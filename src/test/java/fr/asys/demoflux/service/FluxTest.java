package fr.asys.demoflux.service;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class FluxTest {

    @Test
    void testFlux() {
        Flux.just("A", "B", "C").log().subscribe();
    }

    @Test
    void testFluxList() {
        Flux.just(List.of("A", "B", "C")).log().subscribe();
    }

    @Test
    void testRange() {
        Flux.range(10, 5).log().subscribe();
    }

    @Test
    void testDuration() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1L)).log().take(2L).subscribe();
        Thread.sleep(5000L);
    }

    @Test
    void backPressure() {
        Flux.range(1, 5).log().subscribe(null, null, null, s -> s.request(3));
    }

    @Test
    void limitTest() {
        Flux.range(1, 5).log().limitRate(3).subscribe();
    }
}
