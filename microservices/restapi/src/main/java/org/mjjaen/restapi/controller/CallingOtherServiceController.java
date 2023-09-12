package org.mjjaen.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjjaen.restapi.model.Location;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/v1/resttamplate")
@RequiredArgsConstructor
@Slf4j
public class CallingOtherServiceController {
    private final Executor controllerTaskExecutor;

    @Operation(summary = "Get location details for an IP")
    @GetMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CompletableFuture<ResponseEntity<Location>> getLocationDetailsFromIpBlocking(@RequestParam(defaultValue = "8.8.8.8") String ip) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Execution getLocationDetailsFromIpBlocking controller method with IP = " + ip);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Location> apiResponse = restTemplate.exchange("http://ipapi.co/".concat(ip).concat("/json/"), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
            if(apiResponse.getStatusCode().is2xxSuccessful())
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse.getBody());
            else
                return apiResponse;
        }, controllerTaskExecutor);
    }
}