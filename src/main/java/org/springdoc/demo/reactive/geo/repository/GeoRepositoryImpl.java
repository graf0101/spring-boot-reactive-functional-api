package org.springdoc.demo.reactive.geo.repository;


import org.springdoc.demo.reactive.shared.client.OpenWeatherMapClient;
import org.springdoc.demo.reactive.shared.model.Geo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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