package com.api.encurtadorlink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // Cria um construtor com todos os argumentos
public class EncurtarUrlResponse {
    private String urlOriginal;
    private String urlCurta;
}