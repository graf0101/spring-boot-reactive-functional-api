package org.springdoc.demo.app4.weather;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

import java.util.function.Consumer;

import org.springdoc.core.fn.builders.operation.Builder;
import org.springdoc.demo.app4.weather.model.Geo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.swagger.v3.oas.annotations.enums.ParameterIn;

@Configuration
public class WeatherRoutingConfiguration {

	@Bean
	public RouterFunction<ServerResponse> monoRouterFunction2(WeatherHandler weatherHandler) {
		return route().GET("/api/weather/{locationName}", weatherHandler::getGeoByName, getGeoByNameOpenAPI()).build();
	}

	private Consumer<Builder> getGeoByNameOpenAPI() {
		return ops -> ops.tag("weather")
				.operationId("getGeoByName").summary("Find location by locationName")
				.tags(new String[] { "Geolocation" })
				.parameter(parameterBuilder().in(ParameterIn.PATH).name("locationName").description("Location Name"))
				.response(responseBuilder().responseCode("200").description("successful operation")
						.implementation(Geo.class))
				.response(responseBuilder().responseCode("400").description("Invalid LocationName"))
				.response(responseBuilder().responseCode("404").description("Geo not found"));
	}

}