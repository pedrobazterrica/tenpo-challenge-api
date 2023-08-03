package com.tenpo.challenge.controller;

import com.tenpo.challenge.model.Request;
import com.tenpo.challenge.service.RequestLogService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestLogController {
    private final RequestLogService requestLogService;

    public RequestLogController(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @GetMapping
    public Page<Request> getAllRequests(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return requestLogService.getRequestsPaginated(page, pageSize);
    }
}
