package fr.asys.demoflux.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class PresentationFluxTest {

    // DO JUST
    @Test
    void testFlux() {
        Flux.just("A", "B", "C").log().subscribe();
    }

    // DO FROMITERABLE
    @Test
    void testListFlux() {
        Flux.fromIterable(List.of("A", "B")).log().subscribe();
    }

    // DO RANGE HERE
    @Test
    void testRange() {
        Flux.range(1, 5).log().subscribe();
    }

    // DO INTERVAL
    @Test
    void testInterval() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1)).log().subscribe();
        Thread.sleep(5000l);
    }

    // DO TAKE with interval
    @Test
    void testTake() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .take(2)
                .log().subscribe();
        Thread.sleep(5000l);
    }

    // DO BACKPRESSURE
    @Test
    void testBackPressure() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .log().subscribe(null, null, null, subscription -> subscription.request(2l));
        Thread.sleep(5000l);
    }

    // DO LIMIT
    @Test
    void testLimit() {
        Flux.range(1, 5).limitRate(3).log().subscribe();
    }
}
