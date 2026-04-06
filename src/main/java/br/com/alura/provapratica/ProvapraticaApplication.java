package br.com.alura.provapratica;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class ProvapraticaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProvapraticaApplication.class, args);
    }



}