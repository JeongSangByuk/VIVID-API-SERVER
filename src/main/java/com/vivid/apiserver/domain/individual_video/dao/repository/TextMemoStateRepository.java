package com.vivid.apiserver.domain.individual_video.dao.repository;

import com.vivid.apiserver.domain.individual_video.domain.TextMemo;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface TextMemoStateRepository extends CrudRepository<TextMemo, String> {

    Optional<TextMemo> findById(String id);

    Optional<TextMemo> findTextMemoStateById(String id);

}
