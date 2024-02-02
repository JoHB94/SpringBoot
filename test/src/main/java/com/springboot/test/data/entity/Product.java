package com.springboot.test.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "name")
@Table(name = "product")//@Table이 필요한 경우는 클래스의 이름과 테이블의 이름이 다른 경우.
public class Product {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* @GeneratedValue : 주로 @Id와 함께 사용되며 해당 필드값을 어떤방식으로 자동 생성할 지 결정합니다.
    * (strategy = "           ")
    * AUTO : 기본값을 사용하는 데이터베이스에 맞게 자동 생성
    * IDENTITY : 데이터 베이스의 AUTO_INCREASEMENT를 사용
    * SEQUENCE : @SequenceGenerator 어노테이션으로 식별자 생성기를 결정하고 이를 통해 값을 자동 주입
    * TABLE : @TableGenerator 어노테이션으로 테이블 정보를 설정하고 어떤 DBMS에도 동일하게 작동하기 원할 경우 사용
    * */
    private Long number;

    @Column(nullable = false)
    /*
    * @Column : 기본적으로 필드는 컬럼과 매핑이 되기 때문에 별 다른 설정이 없는 경우 생략
    * @Transient : 엔터티에는 선언돼 있는 필드지만 데이터베이스에서는 필요 없을 경우 사용
    * */
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
