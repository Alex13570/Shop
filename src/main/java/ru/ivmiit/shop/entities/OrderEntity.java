package ru.ivmiit.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends BaseEntity{

    @OneToMany
    private List<OrderDetail> orderDetails;

    @ManyToOne
    private UserEntity user;

}
