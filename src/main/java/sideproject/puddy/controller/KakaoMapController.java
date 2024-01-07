package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sideproject.puddy.service.KakaoMapService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kakao")
public class KakaoMapController {
    private final KakaoMapService kakaoMapService;

    @GetMapping("/address")
    public boolean isValidAddress(
            @RequestParam("mainAddress") String mainAddress
    ) {
        return kakaoMapService.isValidMainAddress(mainAddress);
    }

}
