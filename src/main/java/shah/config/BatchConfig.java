package shah.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import shah.entity.Product;
import shah.repository.ProductRepository;

@Configuration
@EnableBatchProcessing
@Slf4j
@AllArgsConstructor
public class BatchConfig {

    private ProductRepository productRepository;
    private JobBuilderFactory job;
    private StepBuilderFactory step;

    @Bean
    public ItemReader<Product> reader() {

        log.info("inside reader");
        // obj for locating file
       return new FlatFileItemReaderBuilder<Product>()
               .name("csvReader")
               .resource(new ClassPathResource("products.csv"))
               .linesToSkip(1)
               .lineMapper(lineMapper())
               .build();
    }

    private LineMapper<Product> lineMapper() {

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("id", "name", "description", "price");
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);

        // take the parse values and set it into the beans, obj to target class type
        BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Product.class);

        // obj to map each line into product obj
        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();

        // take the value read & class type and add here
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    //	input type, output type
    @Bean
    public ItemProcessor<Product, Product> processor() {
        log.info("inside processor");
        return new ProductProcessor();
    }

    @Bean
    public ItemWriter<Product> writer() {
        log.info("inside writer");
        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
        writer.setRepository(productRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Job job() {
        return job
                .get("job")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return step
                .get("step")
                .<Product, Product>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
}
















