package ru.ivmiit.shop.exceptions.order;

import org.springframework.http.HttpStatus;
import ru.ivmiit.shop.exceptions.ShopException;

public class OrderNotFoundException extends ShopException {

    public OrderNotFoundException() {
        super("Order not found", HttpStatus.NOT_FOUND);
    }
}
