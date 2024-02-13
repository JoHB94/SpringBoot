package com.springboot.advanced_jpa.data.repository;

import com.springboot.advanced_jpa.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    //find..By를 이용한 쿼리 메서드
    Optional<Product> findByNumber(Long number);
    List<Product> findAllByName(String name);
    Product queryByNumber(Long number);

    //exist..By를 이용한 메서드
    boolean existsByNumber(Long number);

    //count..By를 이용한 메서드
    long countByName(String name);

    //delete..By와 remove를 이용한 메서드
    void deleteByNumber(Long number);
    long removeByName(String name);
    //삭제한 횟수를 리턴

    //..First<number>..,..Top<number>..를 사용한 메서드
    List<Product> findFirst5ByName(String name);
    List<Product> findTop10ByName(String name);

    /*쿼리 메서드의 조건자 키워즈
    * Is : 값의 일치를 조건으로 사용하는 조건자 키워드. 생략되는 경우가 많으며 Equals와 동일한 기능을 수행.
    * */
    Product findByNumberIs(Long number);
    Product findByNumberEquals(Long number);
    //Product findByNumber(Long); 와도 동일한 기능을 수행

    /*
    * (Is)Not : 값의 불일치를 조건으로 사용하는 조건자 키워드. Is는 생략하고 Not키워드만 사용할 수 도 있음.
    * */
    Product findByNumberIsNot(Long number);
    Product findByNumberNot(Long number);

    /*
    * (Is)Null, (Is)NotNull : 값이 null인지 검사하는 조건자 키워드
    * */
    List<Product> findByUpdatedAtNull();
    List<Product> findByUpdatedAtIsNull();
    List<Product> findByUpdatedAtNotNull();
    List<Product> findByUpdatedAtIsNotNull();

    /*
    * (Is)True, (Is)False : boolean 타입으로 지정된 컬럼값을 확인하는 키워드.
    * */
//    Product findByisActiveTrue();
//    Product findByisActiveIsTrue();
//    Product findByisActiveFalse();
//    Product findByisActiveIsFalse();

    /*
    * And,Or : 여러 조건을 묶을 때 사용.
    * */
    Product findByNumberAndName(Long number, String name);
    Product findByNumberOrName(Long number, String name);

    /*
    * (Is)GreaterThan, (Is)LessThan, (Is)Between : 숫자나 datetime 칼럼을 대상으로 한 비교 연산에 사용.
    * */
    List<Product> findByPriceIsGreaterThan(Long price);
    List<Product> findByPriceGreaterThan(Long price);
    List<Product> findByPriceGreaterThanEqual(Long price);
    List<Product> findByPriceIsLessThan(Long price);
    List<Product> findByPriceLessThan(Long price);
    List<Product> findByPriceLessThanEqual(Long price);
    List<Product> findByPriceIsBetween(Long lowPrice, Long highPrice);
    List<Product> findByPriceBetween(Long lowPrice, Long highPrice);

    /*
    * (Is)StartingWith(=StartsWith), (Is)EndingWith(=EndsWith),(Is)Containing(=Contains),(Is)Like
    *  : 칼럼값에서 일부 일치 여부를 확인하는 조건자 키워드.
    * */
    List<Product> findByNameLike(String name);
    List<Product> findByNameIsLike(String name);

    List<Product> findByNameContains(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameIsContaining(String name);

    List<Product> findByNameStartsWith(String name);
    List<Product> findByNameStartingWith(String name);
    List<Product> findByNameIsStartingWith(String name);

    List<Product> findByNameEndsWith(String name);
    List<Product> findByNameEndingWith(String name);
    List<Product> findByNameIsEndingWith(String name);

    /*
    * 정렬 처리하기
    * Asc & Desc : 오름차순 정렬 & 내림차순 정렬
    * */
    List<Product> findByNameOrderByNumberAsc(String name);
    List<Product> findByNameOrderByNumberDesc(String name);

    /*
    * 여러 정렬 기준 사용 (And를 붙이지 않음)
    * */
    List<Product> findByNameOrderByPriceAscStockDesc(String name);
    //price를 기준으로 오름차순 정렬은 한 뒤 stock을 기준으로 내림차순 정렬

    /*
    * 매개변수를 이용한 쿼리 정렬
    * */
    List<Product> findByName(String name, Sort sort);

    /*
    * 페이지징 처리
    * */
    Page<Product> findByName(String name, Pageable pageable);
    //특정 컬럼만 추출하는 쿼리
    @Query("SELECT p.name, p.price, p.stock FROM Product p WHERE p.name = :name")
    List<Object[]> findByNameParam2(@Param("name") String name);

}
