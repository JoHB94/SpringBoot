package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /*상속받는 JpaRepository의 제너릭 타입은 첫번째는 사용하는 엔터티를 , 두번째는 엔터티의 @Id가 붙은 필드의 타입으로 설정*/
}
