package ru.ivmiit.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivmiit.shop.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
