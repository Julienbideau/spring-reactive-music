package fr.asys.demoflux.service;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

/**
 * Reactor !
 */
public class OperatorTest {

    @Test
    void mapTest() {
        Flux.range(1, 10).map(i -> i * 10).log().subscribe();
    }

    @Test
    void map2Test() {
        Flux.range(1, 10).flatMap(i -> Flux.range(i * 10, 2)).log().subscribe();
    }

    @Test
    void concatTest() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5).delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5).delayElements(Duration.ofMillis(400));
        Flux.concat(oneToFive, sixToTen).subscribe(System.out::println);

        Thread.sleep(4000);

    }

    @Test
    void mergeTest() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5).delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5).delayElements(Duration.ofMillis(400));
        Flux.merge(oneToFive, sixToTen).subscribe(System.out::println);

        Thread.sleep(4000);

    }
}
