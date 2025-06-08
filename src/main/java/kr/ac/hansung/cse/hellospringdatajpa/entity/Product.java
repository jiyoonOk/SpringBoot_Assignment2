package kr.ac.hansung.cse.hellospringdatajpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    @NotBlank(message = "상품명은 필수입니다.")
    @Size(min = 2, max = 45, message = "상품명은 2~45자여야 합니다.")
    private String name;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "브랜드는 필수입니다.")
    @Size(min = 2, max = 30, message = "브랜드는 2~30자여야 합니다.")
    private String brand;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "제조국가는 필수입니다.")
    @Size(min = 2, max = 20, message = "제조국가는 2~20자여야 합니다.")
    private String madeIn;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = true, message = "가격은 0 이상이어야 합니다.")
    @DecimalMax(value = "10000000.0", message = "가격이 1000만원 이상이면 안됩니다.")
    private double price;

    public Product(String name, String brand, String madeIn, double price) {
        this.name = name;
        this.brand = brand;
        this.madeIn = madeIn;
        this.price = price;
    }
}