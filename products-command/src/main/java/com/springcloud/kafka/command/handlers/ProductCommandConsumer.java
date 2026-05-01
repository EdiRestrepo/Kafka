package com.springcloud.kafka.command.handlers;

import com.springcloud.kafka.command.models.Command;
import com.springcloud.kafka.command.models.Product;
import com.springcloud.kafka.command.models.dto.ProductDto;
import com.springcloud.kafka.command.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductCommandConsumer {
    private static final Logger log = LoggerFactory.getLogger(ProductCommandConsumer.class);
    private final ProductService productService;

    public ProductCommandConsumer(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public Consumer<Command<ProductDto>> handleCommands() {
        return cmd -> {
            log.info("Comando recibido y consumido con exito: type={}, body={}", cmd.type(), cmd.body());
            ProductDto dto = cmd.body();
            if (dto != null) {
                Product product = new Product(dto.name(), dto.price());
                productService.saveProduct(product);
                log.info("Producto guardado en base de datos: {}", product);
            }
        };
    }
}
