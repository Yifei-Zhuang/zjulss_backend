package com.example.zjulss.service.search;
import java.util.List;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.dao.GoodForSaleRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GoodSearchServiceWithRepo {
    private GoodForSaleRepository productRepository;

	@Autowired
	public GoodSearchServiceWithRepo(final GoodForSaleRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	public void createProductIndexBulk(final List<GoodForSale> products) {
		productRepository.saveAll(products);
	}

	public GoodForSale createProductIndex(final GoodForSale product) {
		return productRepository.save(product);
	}


	public List<GoodForSale> findByProductName(final String productName) {
		return productRepository.findByName(productName);
	}

	public List<GoodForSale> findByProductMatchingNames(final String productName) {
		return productRepository.findByNameContaining(productName);	
	}
}
