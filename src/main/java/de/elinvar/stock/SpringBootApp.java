package de.elinvar.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Amsterdam Luís
 */
@SpringBootApplication
public class SpringBootApp
{
    public static void main (String[] args)
    {
        SpringApplication.run(AppConfig.class, args);
    }
}
