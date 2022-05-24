package fr.asys.demoflux.service;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    void monoTest() {
        Mono.just("Le bal masqué").log().subscribe();
    }
}
