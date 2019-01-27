package de.elinvar.stock.config;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author Amsterdam Luís
 */
@Provider
@PreMatching
public class CorsRequestFilter implements ContainerRequestFilter
{
    @Override
    public void filter (ContainerRequestContext request)
    {
        if (request.getMethod().equals("OPTIONS"))
        {
            request.abortWith(Response.ok().build());
        }
    }
}
