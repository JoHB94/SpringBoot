package com.springboot.advanced_jpa.data.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springboot.advanced_jpa.data.entity.Product;
import com.springboot.advanced_jpa.data.entity.QProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void sortingAndPagingTest() {
        Product product1 = new Product();
        product1.setName("펜");
        product1.setPrice(1000);
        product1.setStock(100);
        product1.setCreatedAt(LocalDateTime.now());
        product1.setUpdatedAt(LocalDateTime.now());

        Product product2 = new Product();
        product2.setName("펜");
        product2.setPrice(5000);
        product2.setStock(100);
        product2.setCreatedAt(LocalDateTime.now());
        product2.setUpdatedAt(LocalDateTime.now());

        Product product3 = new Product();
        product3.setName("펜");
        product3.setPrice(500);
        product3.setStock(50);
        product3.setCreatedAt(LocalDateTime.now());
        product3.setUpdatedAt(LocalDateTime.now());

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
        Product savedProduct3 = productRepository.save(product3);

//        productRepository.findByName("펜", Sort.by(Sort.Order.asc("price")));
//        productRepository.findByName("펜", Sort.by(Sort.Order.asc("price"), Sort.Order.desc("stock")));
//
//        Page<Product> productPage = productRepository.findByName("펜", PageRequest.of(0,2));
        /*
        * .of()메서드의 매개변수
        * of(int page, int size) : 페이지 번호, 페이지당 데이터 갯수 
        * of(int page, int size, Sort) : 페이지 번호, 페이지당 데이터 갯수, 정렬
        * of(int page, int size, Direction, String properties) : 페이지 번호, 페이지당 데이터 갯수, 정렬방향, 속성(칼럼)
        * */
        System.out.println("*******************************************************************");
        List<Object[]> findByCol = productRepository.findByNameParam2("펜");
    }

   /*
   * QueryDSL에 의해 생성된 Q도메인 클래스를 활용하는 코드
   * */
    @Test
    void queryDslTest() {
        //JPAQuery를 활용한 코드
        JPAQuery<Product> query = new JPAQuery(entityManager);
        QProduct qProduct = QProduct.product;

        List<Product> productList = query
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();
        /*
        * List<T> fetch() : 조회 결과를 리스트로 반환합니다.
        * T fetchOne : 단 건의 조회결과를 반환합니다.
        * Long fetchCount() : 조회 결과의 개수를 반환합니다.
        * T fetchFirst() : 여러건의 조회 결과 중 1건을 반환합니다.
        * QueryResult<T> fetchResults() : 조회 결과 리스트와 개수를 포함한 QueryResults를 반환합니다.
        * */

        for(Product product : productList) {
            System.out.println("---------------------");
            System.out.println();
            System.out.println("Product Number : " + product.getNumber());
            System.out.println("Product Name : " + product.getName());
            System.out.println("Product Price : " + product.getPrice());
            System.out.println("Product Stock : " + product.getStock());
            System.out.println();
            System.out.println("---------------------");
        }

    }
    @Test
    void queryDslTest2() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;

        List<Product> productList = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (Product product : productList) {
            System.out.println("---------------------");
            System.out.println();
            System.out.println("Product Number : " + product.getNumber());
            System.out.println("Product Name : " + product.getName());
            System.out.println("Product Price : " + product.getPrice());
            System.out.println("Product Stock : " + product.getStock());
            System.out.println();
            System.out.println("---------------------");
        }
    }

    @Test
    void queryDslTest3() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;

        List<String> productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for(String product : productList) {
            System.out.println("---------------------");
            System.out.println("Product Name : " + product);
            System.out.println("---------------------");
        }
        /*
        * 조회 대상이 여러 개 일 경우 List<String>이 아닌 List<Tuple>타입을 사용합니다.
        * */
        List<Tuple> tupleList = jpaQueryFactory
                .select(qProduct.name, qProduct.price)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (Tuple product : tupleList) {
            System.out.println("---------------------");
            System.out.println("Product Name : " + product.get(qProduct.name));
            System.out.println("Product Price : " + product.get(qProduct.price));
            System.out.println("---------------------");
        }
    }


}
