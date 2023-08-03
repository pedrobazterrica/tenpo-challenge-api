package com.tenpo.challenge.service;

import com.tenpo.challenge.model.Request;
import com.tenpo.challenge.repository.RequestLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequestLogServiceImpl implements RequestLogService{
    private final RequestLogRepository requestLogRepository;

    public RequestLogServiceImpl(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    @Override
    public Page<Request> getRequestsPaginated(Integer page, Integer size) {
        log.info("Querying request logs database for page: {} and size: {}", page, size);
        return requestLogRepository.findAll(PageRequest.of(page,size));
    }
}
