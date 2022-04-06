package fr.asys.demoflux.repository;

import fr.asys.demoflux.model.Music;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MusicRepositoryReactive extends ReactiveCrudRepository<Music, String> {

    Mono<Music> findByTitle(String title);

}
