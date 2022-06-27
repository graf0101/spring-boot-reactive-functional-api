package org.springdoc.demo.reactive.geo;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import java.util.function.Consumer;

import org.springdoc.core.fn.builders.operation.Builder;
import org.springdoc.demo.reactive.shared.model.Geo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.swagger.v3.oas.annotations.enums.ParameterIn;

@Configuration
public class GeoRoutingConfiguration {

	@Bean
	public RouterFunction<ServerResponse> geoRouterFunction(GeoHandler geoHandler) {
		String pattern = "/api/geo/{locationName}";
		return route().GET(pattern, accept(MediaType.APPLICATION_JSON), geoHandler::getGeoByName, getGeoByNameOpenAPI()).build();
	}

	private Consumer<Builder> getGeoByNameOpenAPI() {
		return ops -> ops.tag("geo")
				.operationId("getGeoByName").summary("Find location by locationName")
				.tags(new String[] { "Geolocation" })
				.parameter(parameterBuilder().in(ParameterIn.PATH).name("locationName").description("Location Name"))
				.response(responseBuilder().responseCode("200").description("successful operation")
						.implementation(Geo.class))
				.response(responseBuilder().responseCode("400").description("Invalid LocationName"))
				.response(responseBuilder().responseCode("404").description("Geo not found"));
	}

}