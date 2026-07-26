package com.lanka.broker.dto;

public record BrokerLoginResponse(Long id, String brokerId, String name, String email, String phone, String district,
                                  String city, String status) {
}
