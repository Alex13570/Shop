package ru.ivmiit.shop.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ShopException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ShopException(String title, HttpStatus status) {
        super(title);
        httpStatus = status;
    }
}
