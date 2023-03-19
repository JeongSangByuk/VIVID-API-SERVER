package com.vivid.apiserver.domain.user.domain;

import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import com.vivid.apiserver.global.common.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "user")
@Getter
@DynamicUpdate
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE user_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoSpaceParticipant> videoSpaceParticipants = new ArrayList<>();

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Embedded
    private Institution institution = new Institution();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "last_access_individual_video_id")
    private UUID lastAccessIndividualVideoId;

    @Builder
    public User(String email, String name, String picture, Role role) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.picture = picture;
    }

    public void changeLastAccessIndividualVideoId(UUID lastAccessIndividualVideoId) {
        this.lastAccessIndividualVideoId = lastAccessIndividualVideoId;
    }

    // null 체크를 위한 getter 따로 생성
    public Institution getInstitution() {
        return this.institution == null ? new Institution() : this.institution;
    }

    public void changeInstitution(Institution institution) {
        this.institution = institution;
    }
}
