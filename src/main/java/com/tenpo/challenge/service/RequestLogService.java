package com.tenpo.challenge.service;

import com.tenpo.challenge.model.Request;
import org.springframework.data.domain.Page;

public interface RequestLogService {
    Page<Request> getRequestsPaginated(Integer page, Integer size);
}
