package com.lanka.user.dto;

public record UserApprovalResponse(Long id, String name, String email, String mobile, String role, String district,
                                   String city, String status) {
}
