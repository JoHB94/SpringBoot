package com.springboot.test.repository;

import com.springboot.test.data.entity.Product;
import com.springboot.test.data.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*Repository 테스트시 고려사항
* 데이터 베이스는 외부 요인에 해당되기 때문에 단위 테스트를 할 때 데이터 베이스르 제외할 수 있다.
* 혹은 테스트용 다른 데이터 베이스를 사용할 수 있다. 테스트 과정에서 데이터 베이스에 테스트 데이터가 적재되기 때문.
* */

@DataJpaTest
/*
* @DataJpaTest : jpa와 관련된 설정만 로드해서 테스트를 진행함
* 내부적으로 @Transactional 어노테이션을 포함하고 있어 테스트 코드가 종료되면 자동으로 데이터 베이스의 롤백이 진행됨
* 기본값으로 임베디드 데이터베이스를 사용(H2데이터 베이스를 내장), 다른 데이터베이스를 사용하려면 별도의 설정 필요.
* */
public class ProductRepositoryTestByH2 {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Environment env;

    @Test
    void testDatabaseConnection() {
        String databaseUrl = env.getProperty("spring.datasource.url");
        System.out.println("Current Database URL: " + databaseUrl);
    }

    @Test
    void saveTest() {
        //given
        Product product = new Product();
        product.setName("펜");
        product.setPrice(1000);
        product.setStock(1000);

        //when
        Product savedProduct = productRepository.save(product);

        //then
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(),savedProduct.getPrice());
        assertEquals(product.getStock(),savedProduct.getStock());
    }

    @Test
    void selectTest() {
        //given
        Product product = new Product();
        product.setName("펜");
        product.setPrice(1000);
        product.setStock(1000);

        Product savedProduct = productRepository.saveAndFlush(product);

        //when
        Product foundProduct = productRepository.findById(savedProduct.getNumber()).get();

        //then
        assertEquals(product.getName(), foundProduct.getName());
        assertEquals(product.getPrice(), foundProduct.getPrice());
        assertEquals(product.getStock(), foundProduct.getStock());
    }
}
