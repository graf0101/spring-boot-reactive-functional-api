package org.springdoc.demo.app4.weather.client;

import java.net.URI;

import org.springdoc.demo.app4.ApiWeatherProperties;
import org.springdoc.demo.app4.weather.model.Geo;
import org.springdoc.demo.app4.weather.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;



import lombok.Setter;
import reactor.core.publisher.Mono;

@Component
public class OpenWeatherMapClient {

	@Autowired
	@Setter
	ApiWeatherProperties apiProps;

	@Autowired
	OpenWeatherMapClientConfig webClientConfig;

	public Mono<Geo> getGeolocationByName(String locationName) {

		return webClientConfig
				.webClient()
				.get()
				.uri(uriBuilder -> buildGeoUri(locationName, apiProps.getLimit(), uriBuilder))
				.accept(MediaType.APPLICATION_JSON)
				.exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Geo.class))
				.next()
				.log("Geo Fetched  : ");
	}

	public Mono<Weather> getWeatherByCoordinates(String locationLat, String locationLon) {
		return webClientConfig
				.webClient()
				.get()
				.uri(uriBuilder -> buildWeatherUri(locationLat, locationLon, apiProps.getUnit(), uriBuilder))
				.accept(MediaType.APPLICATION_JSON)
				.exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Weather.class))
				.next()
				.log("Weather Fetched  : ");
	}

	private URI buildWeatherUri(String locationLat, String locationLon, String units, UriBuilder uriBuilder) {
		return uriBuilder
				.path(apiProps.getPathWeather())
				.queryParam("lat", locationLat)
				.queryParam("lon", locationLon)
				.queryParam("units", units)
				.queryParam("lang", apiProps.getLang())
				.queryParam("appid", apiProps.getKey()).build();
	}

	private URI buildGeoUri(String locationName, String string, UriBuilder uriBuilder) {
		return uriBuilder
				.path(apiProps.getPathGeo())
				.queryParam("q", locationName)
				.queryParam("limit", string)
				.queryParam("appid", apiProps.getKey())
				.build();
	}
}