package com.vivid.apiserver.domain.individual_video.dao.repository;

import com.vivid.apiserver.domain.individual_video.domain.TextMemoState;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TextMemoStateRepository extends CrudRepository<TextMemoState, String> {

    Optional<TextMemoState> findById(String id);

    Optional<TextMemoState> findTextMemoStateById(String id);

}
