package shah.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Product {

	@Id
	private Integer id;
	private String name;
	private String description;
	private Double price;
	

	
}
