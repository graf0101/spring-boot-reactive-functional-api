package dev.fgraf.demo.reactive.user;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserRepository customerRepository;

    public UserHandler(UserRepository repository) {
        this.customerRepository = repository;
    }

    /**
     * GET ALL Users
     */
    public Mono<ServerResponse> getAll(ServerRequest request) {
        // fetch all customers from repository
        Flux<User> customers = customerRepository.getAllUsers();

        // build response
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(customers, User.class);

    }

    /**
     * GET a User by ID
     */
    public Mono<ServerResponse> getUser(ServerRequest request) {
        // parse path-variable
        long customerId = Long.valueOf(request.pathVariable("id"));

        // build notFound response
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        // get customer from repository
        Mono<User> customerMono = customerRepository.getUserById(customerId);

        // build response
        return customerMono
                .flatMap(this::getUserContent)
                .switchIfEmpty(notFound);
    }

    private Mono<ServerResponse> getUserContent(User customer) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(customer));
    }

    /**
     * POST a User
     */
    public Mono<ServerResponse> postUser(ServerRequest request) {
        Mono<User> customer = request.bodyToMono(User.class);
        return ServerResponse.ok().build(customerRepository.saveUser(customer));
    }

    /**
     * PUT a User
     */
    public Mono<ServerResponse> putUser(ServerRequest request) {
        // parse id from path-variable
        long customerId = Long.valueOf(request.pathVariable("id"));

        // get customer data from request object
        Mono<User> customer = request.bodyToMono(User.class);

        // get customer from repository
        Mono<User> responseMono = customerRepository.putUser(customerId, customer);

        // build response
        return responseMono
                .flatMap(this::getUserContent);
    }

    /**
     * DELETE a User
     */
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        // parse id from path-variable
        long customerId = Long.valueOf(request.pathVariable("id"));

        // get customer from repository
        Mono<String> responseMono = customerRepository.deleteUser(customerId);

        // build response
        return responseMono
                .flatMap(strMono -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(fromValue(strMono)));

    }
}
