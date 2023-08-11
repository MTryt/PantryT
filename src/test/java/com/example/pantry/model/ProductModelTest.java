package com.example.pantry.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductModelTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Test checks if product details are entered correctly")
    void setValidProduct(){ //czy robi się taki ogólny test czy każdego elementu pojedynczo?
        //given
        ProductModel product = new ProductModel();
        product.setProductName("Apple");
        product.setQuantityOfProduct(10);
        product.setMeasurementUnit("pieces");
        product.setExpiryDate(LocalDate.now().plusDays(7));
        product.setStatusOfProduct(ProductStatus.OKAY);

        // when
        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);

        // then
        assertThat(violations).isEmpty();

    }
    @Test
    @DisplayName("Test checks what if product name is empty")
    void testEmptyProductName() {
        // given
        ProductModel product = new ProductModel();
        product.setProductName("");

        // when
        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product); //czy istnieją naruszenia walidacji (czyli zbiór naruszeń nie jest pusty) oraz czy wśród tych naruszeń jest przynajmniej jedno z komunikatem "Product name cannot be empty.".

        // then
        assertFalse(violations.isEmpty()); //oczekuje że argument będzie równy false
        assertTrue(violations.stream() //oczekuje, że argument będzie równy true
                .anyMatch(violation -> "Product name cannot be empty.".equals(violation.getMessage()))
        );
    }

    @Test
    @DisplayName("Test checks what if the value of quantity is negative")
    void testNegativeQuantity() {
        // given
        ProductModel product = new ProductModel();
        product.setQuantityOfProduct(-1);

        // when
        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);

        // then
        assertFalse(violations.isEmpty()); //oczekujemy, że zbiór naruszeń violations nie jest pusty. Jeśli jest pusty, to znaczy, że walidator nie wykrył żadnych naruszeń, co jest niewłaściwe w tym przypadku, ponieważ pole quantityOfProduct powinno być większe lub równe zeru.
        assertTrue(violations.stream()
                .anyMatch(violation -> "Quantity should not be less than 0.".equals(violation.getMessage())) //sprawdza, czy wśród naruszeń istnieje przynajmniej jedno naruszenie, które ma komunikat "Quantity should not be less than 0.". Wykorzystujemy tu strumień (stream()) naruszeń, a następnie używamy metody anyMatch() do sprawdzenia, czy przynajmniej jeden element spełnia warunek podany w lambdzie. W tym przypadku warunek to sprawdzenie, czy komunikat naruszenia jest zgodny z oczekiwanym komunikatem dla ograniczenia @Min.
        );
    }

    @Test
    @DisplayName("Test checks if expiry date is valid")
    void testPastExpiryDate() {
        // given
        ProductModel product = new ProductModel();
        LocalDate pastDate = LocalDate.now().minusDays(1); // data z przeszłości
        product.setExpiryDate(pastDate);

        // when
        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);

        // then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(violation -> "Expiry date should be in the future.".equals(violation.getMessage()))
        );
    }

//    @Test
//    void testZeroQuantity() {
//        // given
//        ProductModel product = new ProductModel();
//        product.setQuantityOfProduct(0);
//
//        // when
//        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);
//
//        // then
//        assertThat(violations).isEmpty();
//    }
//
//    @Test
//    void testPositiveQuantity() {
//        // given
//        ProductModel product = new ProductModel();
//        product.setQuantityOfProduct(10);
//
//        // when
//        Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);
//
//        // then
//        assertTrue(violations.isEmpty());
//    }
//
//@Test
//void testFutureExpiryDate() {
//    // given
//    ProductModel product = new ProductModel();
//    LocalDate futureDate = LocalDate.now().plusDays(1); // data z przyszłości
//    product.setExpiryDate(futureDate);
//
//    // when
//    Set<ConstraintViolation<ProductModel>> violations = validator.validate(product);
//
//    // then
//    assertTrue(violations.isEmpty());
//}


//
//    @Test
//    void setStatusOfProduct() {
//    }
}