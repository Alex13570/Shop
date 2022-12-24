package ru.ivmiit.shop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class MainController {

    @GetMapping
    public String HelloWorld(){
        return "<h1>Hello World</h1>";
    }

}
