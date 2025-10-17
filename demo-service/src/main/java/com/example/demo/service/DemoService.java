package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public String getServiceMessage() {
        return "¡Servicio de demostración funcionando correctamente!";
    }
}
