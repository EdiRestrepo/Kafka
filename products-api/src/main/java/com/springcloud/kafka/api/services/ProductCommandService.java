package com.springcloud.kafka.api.services;

import com.springcloud.kafka.api.models.dto.ProductDto;
import jakarta.validation.Valid;

public interface ProductCommandService {

    void sendCreate(com.springcloud.kafka.api.models.dto.@Valid ProductDto dto);
}
