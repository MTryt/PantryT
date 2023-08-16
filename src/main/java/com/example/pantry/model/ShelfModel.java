package com.example.pantry.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShelfModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelf_id")
    private Long shelfId;

    @NotBlank(message = "Shelf name cannot be blank")
    @Size(min = 3, max = 30, message = "Shelf name must be between 3 and 30 characters")
    @Column(name = "shelf_name")
    private String shelfName;

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL)
    private List<ProductModel> products;
    @Override
    public String toString() {
        return "ShelfModel{" +
                "shelfId=" + shelfId +
                ", shelfName='" + shelfName + '\'' +
                '}';
    }
}
