package com.tenpo.challenge.service;

import com.tenpo.challenge.model.Request;
import com.tenpo.challenge.repository.RequestLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RequestLogServiceImpl implements RequestLogService{
    private final RequestLogRepository requestLogRepository;

    public RequestLogServiceImpl(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    @Override
    public Page<Request> getRequestsPaginated(Integer page, Integer size) {
        return requestLogRepository.findAll(PageRequest.of(page,size));
    }
}
