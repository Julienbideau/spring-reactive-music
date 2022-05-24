package fr.asys.demoflux.service;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class PresentationOperatorTest {

    // DO MAP TEST i * 10
    @Test
    void mapTest() {
        Flux.range(1, 10);
    }

    // DO FLATMAP FLUX.RANGE
    @Test
    void map2Test() {
        Flux.range(1, 10);
    }

    @Test
    void concatTest() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5).delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5).delayElements(Duration.ofMillis(400));

        Flux.concat(oneToFive, sixToTen);

        Thread.sleep(4000);

    }

    @Test
    void mergeTest() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5).delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5).delayElements(Duration.ofMillis(400));
        Flux.merge(oneToFive, sixToTen);

        Thread.sleep(4000);

    }
}
