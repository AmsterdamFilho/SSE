package de.elinvar.stock.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSink;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Amsterdam Luís
 */
@Controller
@Path("stocks")
@Tag(name = "CheckService")
public class CheckService
{
    @Resource(name = "clients")
    private Map<String, List<SseEventSink>> clients;

    @GET
    @Path("{stock}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Response get (@Context SseEventSink eventSink, @PathParam("stock") String stock) throws InterruptedException
    {
        try (SseEventSink mySink = eventSink)
        {
            clients.putIfAbsent(stock, new ArrayList<>());
            clients.get(stock).add(mySink);
            int limit = 12 * 3600; // 12 hours then expire
            int executionTime = 0;
            while (!eventSink.isClosed() && executionTime < limit)
            {
                Thread.sleep(1000);
                executionTime += 1;
            }
        }
        finally
        {
            clients.remove(stock);
        }
        return Response.ok().build();
    }
}
