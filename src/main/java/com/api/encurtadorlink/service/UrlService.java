package com.api.encurtadorlink.service;

import com.api.encurtadorlink.model.Url;
import com.api.encurtadorlink.repository.UrlRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url encurtarUrl(String urlOriginal) {
        if (urlOriginal == null || urlOriginal.isEmpty()) {
            throw new IllegalArgumentException("URL não pode ser vazia.");
        }

        Url novaUrl = new Url();
        novaUrl.setUrlOriginal(urlOriginal);
        novaUrl.setCodigoCurto(gerarCodigoCurto());
        novaUrl.setDataCriacao(LocalDateTime.now());
        novaUrl.setDataExpiracao(LocalDateTime.now().plusDays(30)); // Expira em 30 dias

        return urlRepository.save(novaUrl);
    }

    public Url obterUrlOriginal(String codigoCurto) {
        Url url = urlRepository.findByCodigoCurto(codigoCurto)
                .orElseThrow(() -> new RuntimeException("URL não encontrada"));

        if (url.getDataExpiracao().isBefore(LocalDateTime.now())) {
            urlRepository.delete(url);
            throw new RuntimeException("URL expirada");
        }
        return url;
    }


    public List<Url> listarTodas() {
        return urlRepository.findAll();
    }

    private String gerarCodigoCurto() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
