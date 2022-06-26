package org.springdoc.demo.app4.weather.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
public class ClientError extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8920649087928416353L;
	@Getter
	private HttpStatus status;
	@Getter
	private String body;

	public ClientError(HttpStatus status, String body) {
		this.status = status;
		this.body = body;
	}
}