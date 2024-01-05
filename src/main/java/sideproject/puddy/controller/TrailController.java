package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.puddy.dto.Trail.TrailDto;
import sideproject.puddy.service.TrailService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TrailController {
    private final TrailService trailService;
    @GetMapping("/trail")
    public List<TrailDto> getNearTrailList(){
        return trailService.getNearTrails();
    }
}
