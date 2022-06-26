package org.springdoc.demo.app4.weather;

import org.springdoc.demo.app4.weather.client.OpenWeatherMapClient;
import org.springdoc.demo.app4.weather.model.Geo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

	@Autowired
	private OpenWeatherMapClient openWeatherMapClient;

	@Override
	public Mono<Geo> getGeoByName(String locationName) {
		return openWeatherMapClient.getGeolocationByName(locationName);
	}

}