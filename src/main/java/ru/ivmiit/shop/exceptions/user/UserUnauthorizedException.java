package ru.ivmiit.shop.exceptions.user;

import org.springframework.http.HttpStatus;
import ru.ivmiit.shop.exceptions.ShopException;

public class UserUnauthorizedException extends ShopException {
    public UserUnauthorizedException(String title) {
        super(title, HttpStatus.UNAUTHORIZED);
    }
}
