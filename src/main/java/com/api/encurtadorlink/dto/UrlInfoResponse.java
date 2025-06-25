package com.api.encurtadorlink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UrlInfoResponse {
    private Long id;
    private String urlOriginal;
    private String urlCurta;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataExpiracao;
}
