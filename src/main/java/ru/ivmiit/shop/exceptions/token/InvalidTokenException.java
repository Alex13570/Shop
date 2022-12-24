package ru.ivmiit.shop.exceptions.token;

import org.springframework.http.HttpStatus;
import ru.ivmiit.shop.exceptions.ShopException;

public class InvalidTokenException extends ShopException {
    public InvalidTokenException(String title) {
        super(title, HttpStatus.UNAUTHORIZED);
    }

    public InvalidTokenException() {
        this("Invalid token wtf");
    }
}
