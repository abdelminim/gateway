package com.app.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.app.services.filters.ErrorFilter;
import com.app.services.filters.PostFilter;
import com.app.services.filters.PreFilter;
import com.app.services.filters.RouteFilter;
import feign.Logger;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication(scanBasePackages = {"com.app.services"})
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients
public class GatewayServerZuulApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerZuulApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GatewayServerZuulApplication.class);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }

    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    @Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
