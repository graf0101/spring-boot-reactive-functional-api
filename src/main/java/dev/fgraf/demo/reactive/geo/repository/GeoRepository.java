package dev.fgraf.demo.reactive.geo.repository;

import dev.fgraf.demo.reactive.shared.model.Geo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Mono;

public interface GeoRepository {
    public Mono<Geo> getGeoByName(@Parameter(in = ParameterIn.PATH, description = "Location Name") String locationName);

}
