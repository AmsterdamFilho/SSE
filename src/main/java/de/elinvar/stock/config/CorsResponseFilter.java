package de.elinvar.stock.config;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * @author Amsterdam Luís
 */
@Provider
public class CorsResponseFilter implements ContainerResponseFilter
{
    @Override
    public void filter (final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
    {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS, DELETE");
        headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept, Accept-Language");
    }
}
