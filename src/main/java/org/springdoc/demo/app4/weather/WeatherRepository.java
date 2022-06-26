package org.springdoc.demo.app4.weather;

import org.springdoc.demo.app4.weather.model.Geo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Mono;

public interface WeatherRepository {
    public Mono<Geo> getGeoByName(@Parameter(in = ParameterIn.PATH, description = "Location Name") String locationName);

}
