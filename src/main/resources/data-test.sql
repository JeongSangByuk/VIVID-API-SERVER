delete
from individual_video;
delete
from video;
delete
from video_space_participant;
delete
from video_space;
delete
from user;


insert into user (user_id, created_at, deleted, update_at, email, webex_access_token,
                  zoom_access_token, last_access_individual_video_id, name, picture, role)
values (0x5F8E0A8EE5AE4F708E6F549E85C93142, '2023-03-06 16:01:49.699314', false,
        '2023-03-06 16:01:49.699314', 'test01@gmail.com', 'test', null, null, '정벽벽',
        'https://lh3.googleusercontent.com/a/AGNmyxZJZZ5F9xVsagdjWQb0ivWET6eB9wGdbuag3g7U=s96-c',
        'USER'),
       (0xED3B4A4D02C44C2CB04F1A9D16846389, '2023-03-07 12:25:39.841594', false,
        '2023-03-07 12:25:39.841594', 'test02@gmail.com', 'test', null, null, '정벽벽',
        'https://lh3.googleusercontent.com/a/AGNmyxZJZZ5F9xVsagdjWQb0ivWET6eB9wGdbuag3g7U=s96-c',
        'USER'),
       (0x227DCB805BE44E8AA3686E5C2DF0F413, '2023-03-07 12:27:18.126975', false,
        '2023-03-07 14:38:25.678589', 'jsb100800@gmail.com', 'test webex access token', null, null,
        '정벽벽',
        'https://lh3.googleusercontent.com/a/AGNmyxZJZZ5F9xVsagdjWQb0ivWET6eB9wGdbuag3g7U=s96-c',
        'USER');

insert into video_space (video_space_id, created_at, deleted, update_at, description,
                         host_email, is_individual_video_space, name)
values (4, '2023-03-06 16:01:49.612384', false, '2023-03-06 16:01:49.612384',
        'test01님의 개인 영상 그룹 입니다.', 'test01@gmail.com', 1, '개인 영상'),
       (5, '2023-03-07 12:24:03.159975', false, '2023-03-07 12:24:03.159975', 'test01',
        'test01@gmail.com', 0, 'string'),
       (6, '2023-03-07 12:25:39.824391', false, '2023-03-07 12:25:39.824391',
        'test02 개인 영상 그룹 입니다.', 'test02@gmail.com', 1, '개인 영상'),
       (7, '2023-03-07 12:27:18.114522', false, '2023-03-07 12:27:18.114522', '정벽벽님의 개인 영상 그룹 입니다.',
        'jsb100800@gmail.com', 1, '개인 영상');

insert into video (video_id, created_at, deleted, update_at, description, is_uploaded,
                   thumbnail_image, title, uploader_id, video_space_id)
values (1, '2023-03-07 12:53:58.617537', false, '2023-03-07 12:53:58.617537', 'test', false,
        'https://service-video-storage.s3.ap-northeast-2.amazonaws.com/no_image_available.png',
        'test', 'jsb100800@gmail.com', 7),
       (2, '2023-03-07 13:15:37.890558', false, '2023-03-07 13:15:37.890558', 'test', false,
        'https://service-video-storage.s3.ap-northeast-2.amazonaws.com/no_image_available.png',
        'test', 'jsb100800@gmail.com', 7),
       (3, '2023-03-07 14:39:34.112328', false, '2023-03-07 14:39:34.112328', 'string', false,
        'https://service-video-storage.s3.ap-northeast-2.amazonaws.com/no_image_available.png',
        'stringww', 'jsb100800@gmail.com', 7);

insert into video_space_participant (video_space_participant_id, created_at, deleted,
                                     update_at, email, user_id, video_space_id)
values (4, '2023-03-06 16:01:49.693145', false, '2023-03-06 16:01:49.693145', 'test01@gmail.com',
        0x5F8E0A8EE5AE4F708E6F549E85C93142, 4),
       (5, '2023-03-07 12:24:03.242493', false, '2023-03-07 12:24:03.242493', 'test01@gmail.com',
        0x5F8E0A8EE5AE4F708E6F549E85C93142, 5),
       (6, '2023-03-07 12:25:39.833943', false, '2023-03-07 12:25:39.833943', 'test02@gmail.com',
        0xED3B4A4D02C44C2CB04F1A9D16846389, 6),
       (7, '2023-03-07 12:27:18.126505', false, '2023-03-07 12:27:18.126505', 'jsb100800@gmail.com',
        0x227DCB805BE44E8AA3686E5C2DF0F413, 7),
       (8, '2023-03-07 12:28:50.929940', false, '2023-03-07 12:28:50.929940', 'test01@gmail.com',
        0x5F8E0A8EE5AE4F708E6F549E85C93142, 7);

insert into individual_video (individual_video_id, created_at, deleted, update_at,
                              last_access_time, progress_rate, video_id,
                              video_space_participant_id)
values (0x25EDC4C928A94733B0D53EBB6425B7AD, '2023-03-07 13:15:37.976456', false,
        '2023-03-07 13:15:37.976456', '2023-03-07 13:15:37.970498', 100, 2, 7),
       (0x4D81D45AD4464E70A5508E8019D146EB, '2023-03-07 14:39:34.281074', false,
        '2023-03-07 14:39:34.281074', '2023-03-07 14:39:34.170126', 100, 3, 7),
       (0x6287F6BDC6A6438FBA8B9B5386261472, '2023-03-07 12:53:58.700918', false,
        '2023-03-07 12:53:58.700918', '2023-03-07 12:53:58.695541', 100, 1, 7);