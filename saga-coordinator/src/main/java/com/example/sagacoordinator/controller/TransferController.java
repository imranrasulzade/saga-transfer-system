package com.example.sagacoordinator.controller;

import com.example.common.dto.TransferRequest;
import com.example.sagacoordinator.service.SagaCoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final SagaCoordinatorService sagaService;

    @PostMapping
    public void transfer(@RequestBody TransferRequest request) {
        sagaService.initiateTransfer(request);
    }
}
