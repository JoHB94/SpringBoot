package com.springboot.jpa.service.impl;

import com.springboot.jpa.data.dao.ProductDAO;
import com.springboot.jpa.data.dto.ProductDto;
import com.springboot.jpa.data.dto.ProductResponseDto;
import com.springboot.jpa.data.entity.Product;
import com.springboot.jpa.data.repository.ProductRepository;
import com.springboot.jpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public ProductResponseDto getProduct(Long number) {
        Product product = productDAO.selectProduct(number);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumber(product.getNumber());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStock(product.getStock());

        return productResponseDto;
    }

    @Override
    public ProductResponseDto saveProduct(ProductDto productDto) {
        /*
        * 서비스 계층에서 DAO에 대한 의존성을 낮추기 위해 Entity객체를 이용해 DAO에 의존성을 주입한다.
        * 의존성 역전(Dependency Inversion):
        서비스 레이어가 엔티티 객체를 통해 데이터를 전달함으로써, 의존성이 역전되어 데이터 액세스 계층이 비즈니스 로직에 의존하지 않게 됩니다.

        * 유연성과 재사용성:
        비즈니스 로직이 엔티티 객체를 통해 데이터를 전달하면, 해당 비즈니스 로직은 특정 데이터베이스 기술이나 DAO 구현에 의존하지 않으므로 유연성과 재사용성이 향상됩니다.

        * 테스트 용이성:
        엔티티 객체를 통해 데이터를 전달하는 방식은 단위 테스트를 수행할 때 모의 객체(Mock)나 다른 테스트 더블을 쉽게 사용할 수 있게 합니다.

        * 레이어 간 추상화:
        엔티티 객체를 사용하면 서비스 레이어와 데이터 액세스 레이어 간의 경계를 명확히 하고, 레이어 간의 추상화를 촉진할 수 있습니다.
        * */
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productDAO.insertProduct(product);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumber(savedProduct.getNumber());
        productResponseDto.setName(savedProduct.getName());
        productResponseDto.setPrice(savedProduct.getPrice());
        productResponseDto.setStock(savedProduct.getStock());

        return productResponseDto;
    }

    @Override
    public ProductResponseDto changeProductName(Long number, String name) throws Exception {
        Product changedProduct = productDAO.updateProductName(number, name);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumber(changedProduct.getNumber());
        productResponseDto.setName(changedProduct.getName());
        productResponseDto.setPrice(changedProduct.getPrice());
        productResponseDto.setStock(changedProduct.getStock());

        return productResponseDto;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        productDAO.deleteProduct(number);
    }

}
