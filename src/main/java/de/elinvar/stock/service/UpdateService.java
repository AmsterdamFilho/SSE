package de.elinvar.stock.service;

import de.elinvar.stock.model.Stock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Amsterdam Luís
 */
@Controller
@Path("stocks")
@Tag(name = "UpdateService")
public class UpdateService
{
    @Resource(name = "clients")
    private Map<String, List<SseEventSink>> clients;

    @Resource(name = "stockSerializer")
    private ObjectWriter writer;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void post (Stock stock, @Context Sse sse) throws JsonProcessingException
    {
        List<SseEventSink> eventSinks = clients.getOrDefault(stock.getName(), new ArrayList<>());
        if (!eventSinks.isEmpty())
        {
            OutboundSseEvent event = sse.newEvent(stock.getName(), writer.writeValueAsString(stock));
            for (SseEventSink sseEventSink : eventSinks)
            {
                if (!sseEventSink.isClosed())
                {
                    sseEventSink.send(event);
                }
            }
        }
    }
}
