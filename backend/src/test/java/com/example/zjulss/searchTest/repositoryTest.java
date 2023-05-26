package com.example.zjulss.searchTest;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.example.zjulss.dao.GoodForSaleRepository;
import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.service.search.GoodSearchServiceWithRepo;
import lombok.extern.slf4j.Slf4j;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class repositoryTest {
    @Mock
    private GoodForSaleRepository employeeRepository;
// JUnit test for saveEmployee
@Test
@Order(1)
@Rollback(value = false)
public void saveEmployeeTest(){

    GoodForSale employee = GoodForSale.builder()
            .name("Ramesh")
            .image("Fadatare")
            .remark("ramesh@gmail.com")
            .build();

    employeeRepository.save(employee);

    System.out.println(employee.getId());
}
@Test
@Order(2)
public void getListOfEmployeesTest(){

    List<GoodForSale> employees = (List<GoodForSale>) employeeRepository.findAll();
    System.out.println(employees.size());
    

}


    
}
