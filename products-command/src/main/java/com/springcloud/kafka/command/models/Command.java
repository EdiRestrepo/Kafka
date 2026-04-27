package com.springcloud.kafka.command.models;

public record Command<T>(String type, Long id, T body) {
}
