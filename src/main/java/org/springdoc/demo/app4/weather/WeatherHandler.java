package org.springdoc.demo.app4.weather;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springdoc.demo.app4.weather.model.Geo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class WeatherHandler {

    private final WeatherRepository weatherRepository;

    public WeatherHandler(WeatherRepository repository) {
        this.weatherRepository = repository;
    }

    public Mono<ServerResponse> getGeoByName(ServerRequest request) {
        String locationName = (request.pathVariable("locationName"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Geo> geoMono = weatherRepository.getGeoByName(locationName);
        return geoMono
                .flatMap(geo -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(geo)))
                .switchIfEmpty(notFound);
    }

}
