package com.vivid.apiserver.domain.user.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vivid.apiserver.domain.user.domain.QUser;
import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.domain.QVideoSpaceParticipant;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserDao {

    private final JPAQueryFactory query;

    public User findByEmail(final String email) {

        // fetch join + queryDLS를 통한 get
        Optional<User> user = Optional.ofNullable(query.select(QUser.user)
                .from(QUser.user)
                .leftJoin(QUser.user.videoSpaceParticipants, QVideoSpaceParticipant.videoSpaceParticipant)
                .fetchJoin()
                .where(QUser.user.email.eq(email))
                .distinct().fetchOne());

        // not found exception
        user.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return user.get();
    }


}
