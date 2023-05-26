/**
 * 
 */
package com.example.zjulss.searchTest;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.example.zjulss.dao.GoodForSaleRepository;
import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.service.search.GoodSearchServiceWithRepo;
import lombok.extern.slf4j.Slf4j;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
/**
 * @author Pratik Das
 *
 */
@ExtendWith(MockitoExtension.class)
class GoodSearchServiceWithRepoTest {
	@Mock
    private GoodForSaleRepository goodForSaleRepository;
	@InjectMocks
	private GoodSearchServiceWithRepo productSearchServiceWithRepo;
	private GoodForSale goodfForSale;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		goodfForSale=GoodForSale.builder()
				.id(1)
				.name("iPhone 12 Pro Max")
				.price(2300.0)
				.image("apple")
				.remark("6.7-inch Super Retina XDR display\n" + 
						"Ceramic Shield, tougher than any smartphone glass\n" + 
						"A14 Bionic chip, the fastest chip ever in a smartphone\n" + 
						"Pro camera system with 12MP Ultra Wide, Wide and Telephoto cameras; 5x optical zoom range; Night mode, Deep Fusion, Smart HDR 3, Apple ProRAW, 4K Dolby Vision HDR recording")
				.build();
	}
	@Test
  public void findById() {
    when(goodForSaleRepository.findById(1)).thenReturn(Optional.of(goodfForSale));
    
  }
	/**
	 * @throws java.lang.Exception
	 */
	
	@Test
	public void testCreateIndex(){
		// given(goodForSaleRepository.findByName(goodfForSale.getName()))
        //         .willReturn(Optional.empty());

        given(goodForSaleRepository.save(goodfForSale)).willReturn(goodfForSale);
		System.out.println(goodForSaleRepository);
        System.out.println(productSearchServiceWithRepo);
		GoodForSale savedEmployee = productSearchServiceWithRepo.createProductIndex(goodfForSale);

        System.out.println(savedEmployee);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
		// List<GoodForSale> products = productSearchServiceWithRepo.findByProductName(savedEmployee.getName());
		Optional<GoodForSale> product =  goodForSaleRepository.findById(1);

		System.out.println(product);
        
	}
	
}
