package org.springdoc.demo.reactive.weather;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springdoc.demo.reactive.shared.model.Weather;
import org.springdoc.demo.reactive.weather.repository.WeatherRepository;
import org.springdoc.demo.reactive.weather.repository.WeatherRepositoryImpl;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class WeatherHandler {

    private final WeatherRepository weatherRepository;

    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    Mono<ServerResponse> badRequest = ServerResponse.badRequest().build();
    Mono<ServerResponse> ok = ServerResponse.ok().build();

    public WeatherHandler(WeatherRepositoryImpl repository) {
        this.weatherRepository = repository;
    }

    public Mono<ServerResponse> getWeatherByName(ServerRequest request) {
        String locationName = (request.pathVariable("locationName"));
        Mono<Weather> weather = weatherRepository.getWeatherByName(locationName);
        return weather
                .flatMap(w -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(w)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getWeatherByCoodinates(String locationLat, String locationLon) {
        Mono<Weather> weather = weatherRepository.getWeatherByCoodinates(locationLat, locationLon);
        return weather
                .flatMap(w -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(w)))
                .switchIfEmpty(notFound);
    }

}
