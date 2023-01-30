package shah.config;

import org.springframework.batch.item.ItemProcessor;
import shah.entity.Product;

public class ProductProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product p) throws Exception {
        p.setPrice(p.getPrice() - 5);
        System.out.println("Processing: " + p.getId() + ", " + p.getName() + ", " + p.getDescription() + ", " + p.getPrice());
        return p;
    }
}
