package dev.fgraf.demo.reactive.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RoutingConfiguration {

	@Bean
	public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
		return route().GET("/api/user/{id}", accept(APPLICATION_JSON), userHandler::getUser,
				ops -> ops.beanClass(UserRepository.class).beanMethod("getUserById")).build();
	}

}