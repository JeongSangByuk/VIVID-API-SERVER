package com.vivid.apiserver.domain.video.dao;

import com.vivid.apiserver.domain.video.domain.OcrText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OcrTextRepository extends JpaRepository<OcrText, UUID> {


}
