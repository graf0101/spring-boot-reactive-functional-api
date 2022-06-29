package dev.fgraf.demo.reactive.geo.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dev.fgraf.demo.reactive.shared.client.OpenWeatherMapClient;
import dev.fgraf.demo.reactive.shared.model.Geo;
import reactor.core.publisher.Mono;

@Repository
public class GeoRepositoryImpl implements GeoRepository {

	@Autowired
	private OpenWeatherMapClient openWeatherMapClient;

	@Override
	public Mono<Geo> getGeoByName(String locationName) {
		return openWeatherMapClient.getGeolocationByName(locationName);
	}

}