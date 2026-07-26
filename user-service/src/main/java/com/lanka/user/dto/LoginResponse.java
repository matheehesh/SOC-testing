package com.lanka.user.dto;

public record LoginResponse(String token, String role, String name, Long id, String status, String district,
                            String city) {
}
