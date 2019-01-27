package de.elinvar.stock.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Amsterdam Luís
 */
@Configuration
public class ObjectMapperFactory
{
    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper ()
    {
        return new ObjectMapper();
    }
}
