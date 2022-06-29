package dev.fgraf.demo.reactive.weather;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import java.util.function.Consumer;

import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import dev.fgraf.demo.reactive.shared.model.Geo;
import dev.fgraf.demo.reactive.shared.model.Weather;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@Configuration
public class WeatherRoutingConfiguration {

	@Bean
	public RouterFunction<ServerResponse> geoWeatherByNameFunction(WeatherHandler weatherHandler) {
		String pattern = "/api/weather/{locationName}";
		return route().GET(pattern, accept(MediaType.APPLICATION_JSON), weatherHandler::getWeatherByName, getWeatherByNameOpenAPI()).build();
	}

	private Consumer<Builder> getWeatherByNameOpenAPI() {
		return ops -> ops.tag("weather")
				.operationId("getWeatherByName").summary("Find location by locationName")
				.tags(new String[] { "Weather" })
				.parameter(parameterBuilder().in(ParameterIn.PATH).name("locationName").description("Location Name").example("London"))
				.response(responseBuilder().responseCode("200").description("successful operation")
						.implementation(Geo.class))
				.response(responseBuilder().responseCode("400").description("Invalid LocationName"))
				.response(responseBuilder().responseCode("404").description("Weather not found"));
	}


	@Bean
	public RouterFunction<ServerResponse> geoWeatherByCoordsFunction(WeatherHandler weatherHandler) {
		String pattern = "/api/weather/lat/{latitude}/lon/{longitud}";
		return route().GET(pattern, accept(MediaType.APPLICATION_JSON), weatherHandler::getWeatherByName, getWeatherByCordsOpenAPI()).build();
	}

	private Consumer<Builder> getWeatherByCordsOpenAPI() {
		return ops -> ops.tag("weather")
				.operationId("geoWeatherByCoordsFunction").summary("Find Weather by Coordinates")
				.tags(new String[] { "Weather" })
				.parameter(parameterBuilder().in(ParameterIn.PATH).name("latitude").description("Location Latitude"))
				.parameter(parameterBuilder().in(ParameterIn.PATH).name("longitud").description("Location Longitud"))
				.response(responseBuilder().responseCode("200").description("Successful operation")
						.implementation(Weather.class))
				.response(responseBuilder().responseCode("400").description("Invalid Cordinates"))
				.response(responseBuilder().responseCode("404").description("Weather not found"));
	}
}