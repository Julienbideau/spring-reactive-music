package fr.asys.demoflux.service;

import fr.asys.demoflux.dto.MusicDto;
import fr.asys.demoflux.dto.SingerDto;
import fr.asys.demoflux.model.Music;
import fr.asys.demoflux.repository.MusicRepositoryReactive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicService {

    Logger logger = LoggerFactory.getLogger(getClass());
    private final MusicRepositoryReactive musicRepositoryReactive;
    private final SingerService singerService;

    public MusicService(MusicRepositoryReactive musicRepositoryReactive, SingerService singerService) {
        this.musicRepositoryReactive = musicRepositoryReactive;
        this.singerService = singerService;
    }

    public Mono<Music> save(Music music) {
        return musicRepositoryReactive.save(music);
    }

    /**
     * Save all with delay musics
     *
     * @return
     */
    public Flux<Music> saveAll() {

        Flux<Music> musicTitles = Flux.just(new Music("A new abnormal", "Casablancas"),
                new Music("The dream", "Alt-J"),
                new Music("Selfness", "Casablancas"),
                new Music("Cold little heart", "Kiwanuka"),
                new Music("Miss You", "Jagger"));
        Flux<Music> saved = musicTitles
                .log()
                .delayElements(Duration.ofSeconds(1l))
                .flatMap(this::saveMusic);
        return saved;
    }

    private Mono<Music> saveMusic(Music music) {
        logger.info("Saved music : {} in thread: {}", music.title(), Thread.currentThread().getName());
        return musicRepositoryReactive.save(music);
    }

    public List<MusicDto> getAllMusics() {
        List<Music> musics = new ArrayList<>();
        musicRepositoryReactive.findAll().subscribe(musics::add);
        return musics.stream()
                .map(this::getMusicDtoFromMusic)
                .collect(Collectors.toList());
    }

    public Mono<MusicDto> findOneBySingerName(String singerName) {
        return musicRepositoryReactive.findAll()
                .filter(music -> music.singerName().equals(singerName))
                .take(1)
                .next()
                .map(this::getMusicDtoFromMusic);
    }

    public Flux<MusicDto> getAsynchronousAllMusics() {
        return musicRepositoryReactive
                .findAll()
                .flatMap(this::getMonoMusicDtoFromMusic);
    }

    public ParallelFlux<MusicDto> getStagedAsynchronousAllMusics() {
        return musicRepositoryReactive
                .findAll()
                .parallel()
                .runOn(Schedulers.parallel())
                .log()
                .flatMap(this::getMonoMusicDtoLightFromMusic)
                .map(musicDto -> new MusicDto(musicDto.title(), singerService.getSynchronousSingerFromSingerName(musicDto.singer().name())))
                .doOnError(error -> logger.error("Error when streaming async", error));
    }

    private MusicDto getMusicDtoFromMusic(Music music) {
        logger.info("Get the music {} by singer : {} in thread: {}", music.title(), music.singerName(), Thread.currentThread().getName());
        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new MusicDto(music.title(), singerService.getSynchronousSingerFromSingerName(music.singerName()));
    }

    private Mono<MusicDto> getMonoMusicDtoFromMusic(Music music) {
        logger.info("Get the music {} in thread: {}", music.singerName(), Thread.currentThread().getName());
        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Mono.just(new MusicDto(music.title(), singerService.getSynchronousSingerFromSingerName(music.singerName())));
    }

    private Mono<MusicDto> getMonoMusicDtoLightFromMusic(Music music) {
        logger.info("Get the music {} in thread: {}", music.singerName(), Thread.currentThread().getName());
        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Mono.just(new MusicDto(music.title(), new SingerDto(music.singerName(), null)));
    }


    public Mono<Void> delete(String title) {
        return musicRepositoryReactive.findByTitle(title)
                .flatMap(music -> musicRepositoryReactivedelete);
    }
}
