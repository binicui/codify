-- H2 In-Memory DB Tables...
-- created by subinpark on 2025-07-27



-- ######################################################################
-- # USER
-- ######################################################################
drop table if exists users cascade;

create table if not exists users comment '사용자' (
    id                      int                     not null auto_increment comment '사용자 번호',
    email                   varchar(40)             unique not null comment '가입 이메일',
    password                varchar(255)            not null comment '비밀번호',
    nickname                varchar(15)             unique not null comment '닉네임',
    name                    varchar(15)             not null comment '이름',
    phone                   varchar(11)             not null comment '휴대폰 번호',
    login_type              varchar(5)              not null comment '로그인 유형 (LOCAL / OAUTH)',
    enabled                 tinyint(1)              not null comment '계정 활성화',
    deleted_yn              char(1)                 not null comment '삭제 여부(논리)',
    created_at              datetime                not null default now() comment '가입 일자',
    updated_at              datetime                not null comment '변경 일자',
    deleted_at              datetime                null comment '삭제 일자(논리)',
    primary key (id)
);


drop table if exists user_roles cascade;

create table if not exists user_roles comment '사용자 별 권한 목록' (
    user_id                 int                     not null comment '사용자 번호',
    role_type               varchar(25)             not null comment '권한 유형 (ROLE_USER / ROLE_MANAGER / ROLE_ADMIN)',
    used_yn                 char(1)                 not null comment '사용 여부',
    created_at              datetime                not null default now() comment '등록 일자',
    updated_at              datetime                not null comment '등록 일자',
    primary key (user_id, role_type),
    foreign key (user_id) references users (id)
);


drop table if exists user_profiles cascade;

create table if not exists user_profiles comment '사용자 프로필' (
    id                      int                     not null auto_increment comment '사용자 번호',
    user_id                 int                     not null comment '사용자 번호',
    profile_img_url         varchar(255)            not null comment '프로필 이미지 경로',
    gender                  char(1)                 not null comment '성별 (M / F / N)',
    bio                     varchar(150)            not null comment '한 줄 소개',
    univ                    varchar(45)             not null comment '대학명',
    location                varchar(20)             not null comment '거주 도시명',
    created_at              datetime                not null default now() comment '등록 일자',
    updated_at              datetime                not null comment '변경 일자',
    primary key (id),
    foreign key (user_id) references users (id)
);




-- ######################################################################
-- # ADMIN
-- ######################################################################
drop table if exists login_histories cascade;

create table if not exists login_histories comment '로그인 이력' (
    id                      int                     not null auto_increment comment '이력 번호',
    user_id                 int                     not null comment '사용자 번호',
    ip_address              varchar(15)             not null comment '아이피 주소',
    user_agent              varchar(255)            not null comment '사용자 식별 정보',
    event_type              char(1)                 not null comment '접속 구분 (I/O)',
    login_at                datetime                not null comment '로그인 일자',
    logout_at               datetime                null comment '로그아웃 일자',
    primary key (id),
    foreign key (user_id) references users (id)
);