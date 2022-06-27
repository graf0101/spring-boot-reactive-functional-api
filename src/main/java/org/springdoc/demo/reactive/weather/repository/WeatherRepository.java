package org.springdoc.demo.reactive.weather.repository;


import org.springdoc.demo.reactive.shared.model.Weather;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Mono;

public interface WeatherRepository {

    Mono<Weather> getWeatherByCoodinates(
            @Parameter(in = ParameterIn.PATH, description = "Location Latitude") String locationLat,
            @Parameter(in = ParameterIn.PATH, description = "Location Longitud") String locationLon);

    Mono<Weather> getWeatherByName(@Parameter(in = ParameterIn.PATH, description = "Location Name") String locationName);

}
