package com.sdn.controller;

import com.sdn.dto.ApiResponseDTO;
import com.sdn.exceptions.CustomException;
import com.sdn.service.PipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/api/v1")
@RestController
public class PipelineController {
    @Autowired
    PipelineService pipelineService;

    @GetMapping("/pushDataToSdn")
    public final ResponseEntity<Object> pushDataToSdn() {
        try {
            pipelineService.pushDataToSdn();
            return new ResponseEntity<>(ApiResponseDTO.builder()
                    .message("Data Successfully Pushed To SDN")
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.OK.value())
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
