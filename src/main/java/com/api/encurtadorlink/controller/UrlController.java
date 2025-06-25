package com.api.encurtadorlink.controller;



import com.api.encurtadorlink.dto.EncurtarUrlRequest;
import com.api.encurtadorlink.dto.EncurtarUrlResponse;
import com.api.encurtadorlink.dto.UrlInfoResponse;
import com.api.encurtadorlink.model.Url;
import com.api.encurtadorlink.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List; // NOVO IMPORT
import java.util.stream.Collectors; // NOVO IMPORT

@RestController
@RequestMapping
public class UrlController {

    private final UrlService urlService;
    private final String BASE_URL = "http://localhost:8080/";

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/encurtar")
    public ResponseEntity<EncurtarUrlResponse> encurtar(@RequestBody EncurtarUrlRequest request) {
        Url urlEncurtada = urlService.encurtarUrl(request.getUrl());
        String urlCurtaCompleta = BASE_URL + "r/" + urlEncurtada.getCodigoCurto();
        EncurtarUrlResponse response = new EncurtarUrlResponse(urlEncurtada.getUrlOriginal(), urlCurtaCompleta);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/r/{codigoCurto}")
    public void redirecionar(@PathVariable String codigoCurto, HttpServletResponse httpServletResponse) throws IOException {
        Url url = urlService.obterUrlOriginal(codigoCurto);
        httpServletResponse.sendRedirect(url.getUrlOriginal());
    }


    @GetMapping("/api/urls")
    public ResponseEntity<List<UrlInfoResponse>> listarUrls() {
        List<Url> urls = urlService.listarTodas();
        List<UrlInfoResponse> responseList = urls.stream()
                .map(url -> new UrlInfoResponse(
                        url.getId(),
                        url.getUrlOriginal(),
                        BASE_URL + "r/" + url.getCodigoCurto(),
                        url.getDataCriacao(),
                        url.getDataExpiracao()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
}
