package com.tenpo.challenge.controller;

import com.tenpo.challenge.model.Request;
import com.tenpo.challenge.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestLogController {
    @Autowired
    private RequestLogRepository requestLogRepository;
    @GetMapping
    public List<Request> getAllRequests(){
        return requestLogRepository.findAll();
    }
}
