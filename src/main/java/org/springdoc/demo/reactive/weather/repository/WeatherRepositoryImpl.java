package org.springdoc.demo.reactive.weather.repository;

import org.springdoc.demo.reactive.shared.client.OpenWeatherMapClient;
import org.springdoc.demo.reactive.shared.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

	@Autowired
	private OpenWeatherMapClient openWeatherMapClient;

	@Override
	public Mono<Weather> getWeatherByCoodinates(String locationLat, String locationLon) {
		return openWeatherMapClient.getWeatherByCoordinates(locationLat, locationLon);

	}

	@Override
	public Mono<Weather> getWeatherByName(String locationName) {
		return openWeatherMapClient.getGeolocationByName(locationName)
				.flatMap(t -> getWeatherByCoodinates(t.getLat().toString(), t.getLon().toString()));

	}

}