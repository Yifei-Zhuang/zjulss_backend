package com.example.zjulss.searchapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.dao.GoodForSaleRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ProductsearchappApplication {
	
	private static final String COMMA_DELIMITER = ",";

	@Autowired
	private ElasticsearchOperations esOps;

	@Autowired
	private GoodForSaleRepository productRepo;

	public static void main(String[] args) {
		SpringApplication.run(ProductsearchappApplication.class, args);
	}
	
	@PreDestroy
	public void deleteIndex() {
		esOps.indexOps(GoodForSale.class).delete();
	}
	
	
	@PostConstruct
	public void buildIndex() {

		esOps.indexOps(GoodForSale.class).refresh();
		productRepo.deleteAll();
		productRepo.saveAll(prepareDataset());
	}

	private Collection<GoodForSale> prepareDataset() {
		Resource resource = new ClassPathResource("fashion-products.csv");
		List<GoodForSale> productList = new ArrayList<GoodForSale>();

		try (
			InputStream input = resource.getInputStream();
			Scanner scanner = new Scanner(resource.getInputStream());) {
			int lineNo = 0;
			while (scanner.hasNextLine()) {
				++lineNo;				
				String line = scanner.nextLine();
				if(lineNo == 1) continue;
				Optional<GoodForSale> product = 
						csvRowToProductMapper(line);
				if(product.isPresent())
				productList.add(product.get());
			}
		} catch (Exception e) {
			log.error("File read error {}",e);;
		}
		return productList;
	}

	private Optional<GoodForSale> csvRowToProductMapper(final String line) {
		try (			
			Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(COMMA_DELIMITER);
			while (rowScanner.hasNext()) {
				String name = rowScanner.next();
				String remark = rowScanner.next();
				String image = rowScanner.next();
				return Optional.of(
					GoodForSale.builder()
						.name(name)
						.remark(remark)
						.image(image)
						.build());

			}
		}
		return Optional.of(null);
	}

}
