package dev.fgraf.demo.reactive.geo;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import dev.fgraf.demo.reactive.geo.repository.GeoRepository;
import dev.fgraf.demo.reactive.shared.model.Geo;
import reactor.core.publisher.Mono;;

@Component
public class GeoHandler {

    private final GeoRepository geoRepository;

    public GeoHandler(GeoRepository repository) {
        this.geoRepository = repository;
    }

    public Mono<ServerResponse> getGeoByName(ServerRequest request) {
        String locationName = (request.pathVariable("locationName"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Geo> geoMono = geoRepository.getGeoByName(locationName);
        return geoMono
                .flatMap(geo -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(geo)))
                .switchIfEmpty(notFound);
    }

}
