package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class TestController {

    @Value("${example.property}")
    private String exampleProperty;

    @Autowired
    private DemoService demoService;

    @GetMapping("/test")
    public String test() {
        return "¡Endpoint de pruebas funcionando correctamente! Spring Boot 2.7.18 con Java 8";
    }

    @GetMapping("/ear")
    public String ear() {
        return "¡Este es el endpoint del módulo EAR!";
    }

    @GetMapping("/property")
    public String property() {
        return exampleProperty;
    }

    @GetMapping("/service")
    public String testService() {
        return demoService.getServiceMessage();
    }
}