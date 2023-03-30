//package com.vivid.apiserver.domain.individual_video;
//
//import com.vivid.apiserver.domain.individual_video.application.IndividualVideoService;
//import com.vivid.apiserver.domain.individual_video.application.command.IndividualVideoCommandService;
//import com.vivid.apiserver.domain.individual_video.application.query.IndividualVideoQueryService;
//import com.vivid.apiserver.domain.user.application.CurrentUserService;
//import com.vivid.apiserver.domain.user.application.command.UserCommandService;
//import com.vivid.apiserver.domain.user.domain.User;
//import com.vivid.apiserver.global.infra.storage.AwsS3Service;
//import com.vivid.apiserver.test.ServiceTest;
//import java.util.UUID;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//public class IndividualVideoServiceTest extends ServiceTest {
//
//    @InjectMocks
//    private IndividualVideoService individualVideoService;
//
//    @Mock
//    private IndividualVideoQueryService individualVideoQueryService;
//
//    @Mock
//    private UserCommandService userCommandService;
//
//    @Mock
//    private IndividualVideoCommandService individualVideoCommandService;
//
//    @Mock
//    private CurrentUserService currentUserService;
//
//    @Mock
//    private AwsS3Service awsS3Service;
//
//    private User user;
//
//    private UUID userId, individualVideoId;
//
//    @BeforeEach
//    void setUp() {
//
////        userId = UUID.randomUUID();
////        user = new User(userId);
////
////        when(currentUserService.getCurrentUser()).thenReturn(user);
//    }
//
//
//    @Test
//    @DisplayName("IndividualVideo 상세 정보 GET 서비스 TEST")
//    public void getDetailsById() {
//
//    }
//
//}
