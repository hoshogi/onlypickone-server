package com.hoshogi.onlyonepick.domain.game.controller;

import com.hoshogi.onlyonepick.domain.game.dto.request.CreateGameRequest;
import com.hoshogi.onlyonepick.domain.game.service.GameService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(CREATED)
    ApiResponse<?> createGame(@RequestPart(value = "images") List<MultipartFile> multipartFiles,
                              @RequestPart(value = "request") CreateGameRequest request) {
        gameService.createGame(request, multipartFiles);
        return ApiResponse.onSuccess(CREATED);
    }
}