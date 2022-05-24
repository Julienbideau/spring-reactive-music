package fr.asys.demoflux.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.asys.demoflux.dto.SingerDto;
import fr.asys.demoflux.model.Singer;

@Service
public class SingerService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private static final List<Singer> SINGERS = List.of(new Singer("Casablancas", "Julian"),
            new Singer("Kiwanuka", "Michael"), new Singer("Jagger", "Mick"), new Singer("Alt-J", null));

    public SingerService() {
    }

    public SingerDto getSynchronousSingerFromSingerName(String singerName) {
        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("Got singer : {} from music in thread: {}", singerName, Thread.currentThread().getName());
        return SINGERS.stream().filter(singer -> singer.name().equals(singerName)).findFirst()
                .map(singer -> new SingerDto(singer.name(), singer.firstname())).orElseThrow(IllegalAccessError::new);
    }

    public SingerDto getSynchronousSingerThrowAnError(String singerName) {
        throw new RuntimeException();
    }
}
