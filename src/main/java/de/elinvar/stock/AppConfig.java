package de.elinvar.stock;

import de.elinvar.stock.config.CorsRequestFilter;
import de.elinvar.stock.config.CorsResponseFilter;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.Path;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Amsterdam Luís
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("org.apache.cxf.jaxrs")
@ComponentScan("de.elinvar.stock.config")
@ComponentScan("de.elinvar.stock.service")
public class AppConfig
{
    private final ApplicationContext ctx;

    @Autowired
    public AppConfig (ApplicationContext ctx)
    {
        this.ctx = ctx;
    }

    @Bean(name = "cxf")
    public SpringBus springBus ()
    {
        return new SpringBus();
    }

    @Bean(destroyMethod = "destroy")
    public Server jaxRsServer ()
    {
        LinkedList<ResourceProvider> resourceProviders = new LinkedList<>();
        for (String beanName : ctx.getBeanDefinitionNames())
        {
            if (ctx.findAnnotationOnBean(beanName, Path.class) != null)
            {
                SpringResourceFactory factory = new SpringResourceFactory(beanName);
                factory.setApplicationContext(ctx);
                resourceProviders.add(factory);
            }
        }
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setApplication(new JaxrsApp());
        factory.setResourceProviders(resourceProviders);
        factory.setProvider(new JacksonJsonProvider());
        factory.setProvider(new CorsResponseFilter());
        factory.setProvider(new CorsRequestFilter());
        OpenApiFeature openApiFeature = new OpenApiFeature();
        openApiFeature.setTitle("Elinvar - Real Time Stock Prices Application");
        openApiFeature.setDescription("Web Service for real time stock prices");
        factory.setFeatures(Collections.singletonList(openApiFeature));
        factory.setBus(springBus());
        factory.setAddress("/");
        return factory.create();
    }

    @Bean
    public ServletRegistrationBean cxfServlet ()
    {
        ServletRegistrationBean<?> servletRegistrationBean = new ServletRegistrationBean<>(new CXFServlet(), "/*");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }
}
