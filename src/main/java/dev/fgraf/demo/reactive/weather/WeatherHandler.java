package dev.fgraf.demo.reactive.weather;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import dev.fgraf.demo.reactive.repository.WeatherRepository;
import dev.fgraf.demo.reactive.repository.WeatherRepositoryImpl;
import dev.fgraf.demo.reactive.shared.model.Weather;
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
        return setContent(weather);
    }

    public Mono<ServerResponse> getWeatherByCoodinates(String locationLat, String locationLon) {
        Mono<Weather> weather = weatherRepository.getWeatherByCoodinates(locationLat, locationLon);
        return setContent(weather);
    }

    private Mono<ServerResponse> setContent(Mono<Weather> weather) {
        return weather
                .flatMap(w -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(w)))
                .switchIfEmpty(notFound);
    }

}
