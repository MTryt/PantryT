package com.example.pantry.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NotEmpty(message = "Product name cannot be empty.")
    @Size(max = 50, message = "Product name cannot exceed 50 characters.")
    @Column(name = "product_name")
    private String productName;

    @Min(value = 0, message = "Quantity should not be less than 0.")
    @Column(name = "quantity")
    private Integer quantityOfProduct;

    @NotEmpty(message = "Measurement unit cannot be empty.")
    @Column(name = "measurement_unit")
    private String measurementUnit;

    @Future(message = "Expiry date should be in the future.")
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

//    @NotEmpty(message = "Status cannot be empty.")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus statusOfProduct;


//    @ManyToOne //do shelves
@ManyToOne
@JoinColumn(name = "shelf_id") // Nazwa kolumny reprezentującej klucz obcy
private ShelfModel shelf;

}
