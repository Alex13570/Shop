package ru.ivmiit.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.time.Instant;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseEntity{

    @Column(name = "expired_time", nullable = false)
    private Instant expiredTime;

    @OneToOne(mappedBy = "refreshToken", fetch = FetchType.LAZY)
    private UserEntity userAccount;

}
