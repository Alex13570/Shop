package ru.ivmiit.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="jewelery_id")
    private JeweleryEntity jewelery;

    @ManyToOne
    @JoinColumn(name="order_id")
    private OrderEntity order;

    private Integer count;

}
