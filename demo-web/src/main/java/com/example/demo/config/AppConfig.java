package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Clase de configuración para cargar ficheros de propiedades externos.
 */
@Configuration
@PropertySource("classpath:demo.properties")
public class AppConfig {

}