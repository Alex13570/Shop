package ru.ivmiit.shop.exceptions.user;

import org.springframework.http.HttpStatus;
import ru.ivmiit.shop.exceptions.ShopException;

public class UserEmailAlreadyTakenException extends ShopException {
    public UserEmailAlreadyTakenException() {
        super("Email already taken!", HttpStatus.BAD_REQUEST);
    }
}
