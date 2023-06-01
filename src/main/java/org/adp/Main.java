package org.adp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("BEGIN SERVICE INITIALIZATION...");
        SpringApplication.run(Main.class, args);
        log.info("SERVICE INITIALIZED SUCCESSFULLY.");
    }
}