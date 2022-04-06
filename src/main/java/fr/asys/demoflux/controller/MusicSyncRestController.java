package fr.asys.demoflux.controller;

import fr.asys.demoflux.dto.MusicDto;
import fr.asys.demoflux.model.Music;
import fr.asys.demoflux.service.MusicService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicSyncRestController {

    private final MusicService musicService;

    public MusicSyncRestController(MusicService musicService) {
        this.musicService = musicService;

    }

    @PostMapping("/save-one")
    public Mono<Music> saveMusic(@RequestBody Music music) {
        Mono<Music> save = musicService.save(music);
        return save;
    }


    @GetMapping("/save")
    public Flux<Music> saveMusics() {
        Flux<Music> save = musicService.saveAll();
        return save;
    }

    @GetMapping(value = "/synchronous")
    public List<MusicDto> getAllMusics() {
        return musicService.getAllMusics();
    }

    @GetMapping(path = "/download")
    public ResponseEntity<Resource> download() {
        Resource resource = new ClassPathResource("the-adults-are-talking.mp3");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file.mp3\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
