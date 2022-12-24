package ru.ivmiit.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivmiit.shop.entities.RefreshToken;
import ru.ivmiit.shop.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByRefreshToken(RefreshToken refreshToken);

}
