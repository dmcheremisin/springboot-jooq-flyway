package org.example.springbootjooq.dto;

public record TaskDto(Integer id, Integer personId, String subject, String status) {
}
