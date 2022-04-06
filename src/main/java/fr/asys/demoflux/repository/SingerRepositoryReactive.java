package fr.asys.demoflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import fr.asys.demoflux.model.Singer;

@Repository
public interface SingerRepositoryReactive extends ReactiveCrudRepository<Singer, String> {

}
