package com.springboot.test.controller;

import com.google.gson.Gson;
import com.springboot.test.data.dto.ProductDto;
import com.springboot.test.data.dto.ProductResponseDto;
import com.springboot.test.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.BDDMockito.given;


@WebMvcTest(ProductController.class)
/*
* @WebMvcTest(테스트 대상 클래스.class) : 웹에서 사용되는 요청, 응답에 대한 테스트 수행.
* 대상 클래스만 로드해 테스트를 수행하며 만약 대상 클래스를 추가하지 않으면 @Controller, @ControllerAdvice등의 컨트롤러 관련 빈 객체가 모두 로드됨.
* @SpringBootTest 보다 가볍게 테스트 하기 위해 사용.
* 일반적으로 @WebMvcTest 어노테이션을 사용한 테스트는 슬라이스 테스트라고 부른다. 단위 테스트와 통합 테스트의 중간 개념.
* 단위 테스트를 수행하기 위해서는 모든 외부요인을 차단하고 테스트해야 하는데,
* 컨트롤러는 개념상 외부요인을 차단하고 테스트하면 의미가 없기 때문에 슬라이스 테스트를 적용하는 경우가 많다.
* */
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    /*
    * MockMvc는 컨트롤러의 API를 테스트 하기 위해 사용.
    * 서블릿 컨테이너의 구동없이 가상의 MVC환경에서 모의 HTTP 서블릿을 요청하는 유틸리티 클래스.
    * */

    @MockBean
    /*
     *@MockBean : 실제 빈 객체가 아닌 Mock(가짜)객체를 생성해서 주입하는 역할을 수행.
     * 이 어노테이션이 선언된 객체는 실제 객체가 아니기 때문에 실제 행위를 수행하지 않는다.
     * 그렇기 때문에 해당 객체는 개발자가 Mockito의 given()메서드를 통해 동작을 정의해야 한다.
     *  */
    ProductServiceImpl productService;

    @Test
    /*
    * @Test : 테스트 코드가 포함되어있음을 선언하는 어노테이션
    * */
    @DisplayName("MockMvc를 총한 Product 데이터 가져오기 테스트")
    /*
    * @DisplayName : 테스트 메서드의 이름이 복잡해서 가독성이 떨어질 경우 이 어노테이션을 통해 테스트에 대한 표현을 정의
    * */
    void getProductTest() throws Exception {
        given(productService.getProduct(123L)).willReturn(
                new ProductResponseDto(123L,"pen",5000,2000)
        );

        String productId = "123";

        mockMvc.perform(
                get("/product?number=" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.stock").exists())
                .andDo(print());

        verify(productService).getProduct(123L);
        /*
        * perform() 메서드를 통해 서버로 URL 요청을 보내는 것 처럼 통신 테스트 코드를 작성해 컨트롤러를 테스트 함.
        * MockMvcRequestBuilders는 GET/POST/PUT/DELETE에 매핑되는 메서드를 제공, HTTP요청 정보를 설정.
        * perform() 메서드는 ResultActions 객체를 리턴하는데, andExpect() 메서드를 사용해 결괏값을 검증할 수 있음.
        * */
    }

    @Test
    @DisplayName("Product 데이터 생성 테스트")
    void createProductTest() throws Exception {
        /*
        * Mock 객체에서 특정 메서드가 실행되는 경우 실제 Return을 줄 수 없기 때문에 아래와 같이 가정 사항을 만들어 줌
        * */
        given(productService.saveProduct(new ProductDto("pen",5000,2000)))
                .willReturn(new ProductResponseDto(12315L,"pen",5000,2000));

        ProductDto productDto = ProductDto.builder()
                .name("pen")
                .price(5000)
                .stock(2000)
                .build();

        Gson gson = new Gson();
        String content = gson.toJson(productDto);

        mockMvc.perform(
                post("/product")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.stock").exists())
                .andDo(print());

        verify(productService).saveProduct(new ProductDto("pen",5000,2000));
    }
}
