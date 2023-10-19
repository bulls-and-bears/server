package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.external.ExternalApiUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ExternalController {

    private final ExternalApiUseCase externalApiUseCase;

    @GetMapping("/sinhan")
    public String get() {
        return externalApiUseCase.get();
    }

    @GetMapping("/parse")
    public ResponseEntity<String> getData() {
        String responseBody = externalApiUseCase.get();
        return ResponseEntity.ok(responseBody);
    }
}
