package fr.asys.demoflux.controller;

import fr.asys.demoflux.dto.MusicDto;
import fr.asys.demoflux.model.Music;
import fr.asys.demoflux.service.MusicService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

import java.util.Comparator;

@RestController
@RequestMapping("/music")
public class MusicAsyncRestController {

    private final MusicService musicService;

    public MusicAsyncRestController(MusicService musicService) {
        this.musicService = musicService;
    }


    @GetMapping("{singer}")
    public Mono<MusicDto> findOneBySingerName(@PathVariable("singer") String singerName) {
        return musicService.findOneBySingerName(singerName);
    }

    @GetMapping("/test/{singer}")
    public Mono<ResponseEntity<MusicDto>> findTestOneBySingerName(@PathVariable("singer") String singerName) {
        Mono<MusicDto> oneBySingerName = musicService.findOneBySingerName(singerName);
        return oneBySingerName.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{title}")
    public Mono<ResponseEntity<Void>> deleteByTitle(@PathVariable("title") String title) {
        return musicService.delete(title).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/mono/{singer}")
    public Mono<ResponseEntity<MusicDto>> findResponseEntityOneBySingerName(@PathVariable("singer") String singerName) {
        return musicService.findOneBySingerName(singerName)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/save-async", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Music> saveMusicStreamed() {
        Flux<Music> save = musicService.saveAll();
        return save;
    }

    @GetMapping(value = "/asynchronous", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MusicDto> getAllAsynchronousMusics() {
        return musicService.getAsynchronousAllMusics();
    }

    @GetMapping(value = "/asynchronous-staged", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ParallelFlux<MusicDto> getAllStagedAsynchronousMusics() {
        return musicService.getStagedAsynchronousAllMusics();
    }

    @GetMapping(value = "/asynchronous-ordered")
    public Flux<MusicDto> getAllOrderedAsynchronousMusics() {
        return musicService.getStagedAsynchronousAllMusics().ordered(Comparator.comparing(MusicDto::title));
    }

    @GetMapping(value = "/asynchronous-ordered-pipe")
    public Flux<MusicDto> getAllOrderedAsynchronousMusicsByPipe() {
        return musicService.getStagedAsynchronousAllMusics().sequential();
    }

}
