package com.lanka.user.dto;

public record RegisterRequest(String name, String mobile, String email, String password, String role, String district,
                              String city) {
}

