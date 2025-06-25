package com.api.encurtadorlink.repository;

import com.api.encurtadorlink.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByCodigoCurto(String codigoCurto);
}