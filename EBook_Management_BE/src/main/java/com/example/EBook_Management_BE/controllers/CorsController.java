package com.example.EBook_Management_BE.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {
    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public HttpStatus handleOptionsRequest(HttpServletResponse response) {
        return HttpStatus.OK;
    }
}
