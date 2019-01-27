package de.elinvar.stock.config;

import de.elinvar.stock.model.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author Amsterdam Luís
 */
@Configuration
public class ObjectWriterFactory
{
    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    @Bean(name = "stockSerializer")
    public ObjectWriter stockSerializer ()
    {
        return objectMapper.writerFor(Stock.class);
    }
}
