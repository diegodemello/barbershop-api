package br.com.dio.barbershop.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record UpdateClientRequest(
        @NotNull
        @JsonProperty("name")
        String name,
        @NotNull
        @Email
        @JsonProperty("email")
        String email,
        @NotNull
        @JsonProperty("phone")
        String phone
) {}