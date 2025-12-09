package com.ma.GateWay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {

	public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
	}

    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/clients").uri("lb://tp-19-service-client"))
                .route(r -> r.path("/clients/**").uri("lb://tp-19-service-client"))
                .route(r -> r.path("/client/**").uri("lb://tp-19-service-client"))
                .route(r -> r.path("/voitures").uri("lb://tp-19-service-voiture"))
                .route(r -> r.path("/voitures/**").uri("lb://tp-19-service-voiture"))
                .route(r -> r.path("/tp-19-service-client/clients")
                        .filters(f -> f.rewritePath("/tp-19-service-client/(?<segment>.*)", "/${segment}"))
                        .uri("lb://tp-19-service-client"))
                .route(r -> r.path("/tp-19-service-client/client/**")
                        .filters(f -> f.rewritePath("/tp-19-service-client/(?<segment>.*)", "/${segment}"))
                        .uri("lb://tp-19-service-client"))
                .route(r -> r.path("/tp-19-service-voiture/voitures")
                        .filters(f -> f.rewritePath("/tp-19-service-voiture/(?<segment>.*)", "/${segment}"))
                        .uri("lb://tp-19-service-voiture"))
                .route(r -> r.path("/tp-19-service-voiture/voitures/**")
                        .filters(f -> f.rewritePath("/tp-19-service-voiture/(?<segment>.*)", "/${segment}"))
                        .uri("lb://tp-19-service-voiture"))
                .build();
    }

}
