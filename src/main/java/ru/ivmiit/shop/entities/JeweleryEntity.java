package ru.ivmiit.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JeweleryEntity extends BaseEntity{

    @Column(nullable = false)
    private String name;

    private String shortDescription;

    private String longDescription;

    @Column(nullable = false, columnDefinition = "decimal(10,2)")
    private Double price;

    private Boolean available;

    private String category;

    private String image;

}
