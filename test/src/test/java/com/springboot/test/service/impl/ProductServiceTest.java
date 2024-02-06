package com.springboot.test.service.impl;

import com.springboot.test.data.dto.ProductDto;
import com.springboot.test.data.dto.ProductResponseDto;
import com.springboot.test.data.entity.Product;
import com.springboot.test.data.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

public class ProductServiceTest {

    private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    /*
    * productRepository를 목 객체로 대체함으로 외부 요인을 배제
    * */
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUpTest() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getProductTest() {
        //given
        Product givenProduct = new Product();
        givenProduct.setNumber(123L);
        givenProduct.setName("펜");
        givenProduct.setPrice(1000);
        givenProduct.setStock(1234);

        Mockito.when(productRepository.findById(123L))
                .thenReturn(Optional.of(givenProduct));

        //when
        ProductResponseDto productResponseDto = productService.getProduct(123L);

        //then
        Assertions.assertEquals(productResponseDto.getNumber(),givenProduct.getNumber());
        Assertions.assertEquals(productResponseDto.getName(),givenProduct.getName());
        Assertions.assertEquals(productResponseDto.getPrice(),givenProduct.getPrice());
        Assertions.assertEquals(productResponseDto.getStock(),givenProduct.getStock());

        verify(productRepository).findById(123L);
    }

    @Test
    void saveProductTest() {
        Mockito.when(productRepository.save(any(Product.class))).then(returnsFirstArg());
        /*
        * any() : Mockito의 ArgumentMatchers에서 제공하는 메서드로
        * 목 객체의 동작을 정의 하거나 검증하는 단계에서 조건으로 특정 매개변수의 전달을 설정하지 않고
        * 메서드의 실행만을 확인 하거나 좀 더 큰 범위의 클래스 객체를 매개변수로 전달받는 등의 상황에 사용
        * save의 매개변수로 any(Product.class)로 동작을 설정 했는데,
        * 일반적으로 given()으로 정의 된 Mock객체의 메서드 동작 감지는 매개변수의 비교를 통해 이루어지나
        * 레퍼런스 변수의 비교는 주솟값으로 이뤄지기 때문에 any()를 사용해 클래스만 정의하는 경우도 있다.
        * */
        ProductResponseDto productResponseDto = productService.saveProduct(
                new ProductDto("펜",1000,1234));

        Assertions.assertEquals(productResponseDto.getName(),"펜");
        Assertions.assertEquals(productResponseDto.getPrice(),1000);
        Assertions.assertEquals(productResponseDto.getStock(),1234);

        verify(productRepository).save(any());

    }
}
