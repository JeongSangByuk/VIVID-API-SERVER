package com.vivid.apiserver.domain.user.application.command;

import com.vivid.apiserver.domain.user.dao.UserRepository;
import com.vivid.apiserver.domain.user.domain.Institution;
import com.vivid.apiserver.domain.user.domain.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    public void changeLastAccessIndividualVideoId(User user, UUID lastAccessIndividualVideoId) {
        user.changeLastAccessIndividualVideoId(lastAccessIndividualVideoId);
    }

    public void changeWebexAccessToken(User user, String accessToken) {
        Institution institution = user.getInstitution();
        institution.changeWebexAccessToken(accessToken);
        user.changeInstitution(institution);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
