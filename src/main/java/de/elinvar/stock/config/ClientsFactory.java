package de.elinvar.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.sse.SseEventSink;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Amsterdam Luís
 */
@Configuration
public class ClientsFactory
{
    /**
     * The map holding all the clients wanting to be notified about a stock price.
     *
     * @return the clients map.
     */
    @Bean(name = "clients")
    public Map<String, List<SseEventSink>> clients ()
    {
        return new ConcurrentHashMap<>();
    }
}
