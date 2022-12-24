package ru.ivmiit.shop.exceptions.user;

import org.springframework.http.HttpStatus;
import ru.ivmiit.shop.exceptions.ShopException;

public class UserNotFoundException extends ShopException {

    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
