-- 키워드
-- INT VARCHAR() TINYINT() CHAR() DATETIME
-- NOT NULL NULL AUTO_INCREMENT COMMENT UNIQUE
-- PRIMARY KEY (), FOREIGN KEY () REFERENCES tablename()

-- DROP TABLE IF EXISTS tablename CASCADE;
-- CREATE TABLE IF NOT EXISTS tablename COMMENT '' (
-- );


-- ######################################################################
--  USER
-- ######################################################################
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users COMMENT '사용자 테이블' (
    id                          INT                         NOT NULL AUTO_INCREMENT COMMENT '사용자 번호',
    email                       VARCHAR(40)                 UNIQUE NOT NULL COMMENT '로그인 이메일',
    password                    VARCHAR(255)                NOT NULL COMMENT '로그인 비밀번호',
    nickname                    VARCHAR(15)                 UNIQUE NOT NULL COMMENT '닉네임',
    name                        VARCHAR(12)                 NOT NULL COMMENT '이름',
    phone                       VARCHAR(11)                 NOT NULL COMMENT '휴대폰 번호',
    login_type                  VARCHAR(5)                  NOT NULL COMMENT '로그인 방식에 따른 유형 (LOCAL, OAUTH)',
    enabled                     TINYINT(1)                  NOT NULL COMMENT '계정 활성화 여부 값 (1, 0)',
    deleted_yn                  CHAR(1)                     NOT NULL DEFAULT 'N' COMMENT '사용자 탈퇴 및 계정 삭제 여부 (논리적 삭제)',
    created_at                  DATETIME                    NOT NULL DEFAULT NOW() COMMENT '가입 일자',
    updated_at                  DATETIME                    NOT NULL COMMENT '변경 일자',
    deleted_at                  DATETIME                    NULL COMMENT '삭제 일자(논리적 삭제)',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_roles CASCADE;
CREATE TABLE IF NOT EXISTS user_roles COMMENT '회원별 보유 권한 목록 테이블' (
    user_id                     INT                         NOT NULL COMMENT '사용자 테이블',
    role_type                   VARCHAR(20)                 NOT NULL COMMENT '권한 유형 (ROLE_USER, ROLE_MANAGER, ROLE_ADMIN)',
    used_yn                     CHAR(1)                     NOT NULL DEFAULT 'Y' COMMENT '권한의 사용 여부',
    created_at                  DATETIME                    NOT NULL DEFAULT NOW() COMMENT '권한 등록 일자',
    updated_at                  DATETIME                    NOT NULL COMMENT '권한 변경 일자',
    PRIMARY KEY (user_id, role_type),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS user_profiles CASCADE;
CREATE TABLE IF NOT EXISTS user_profiles COMMENT '사용자 프로필 정보 테이블' (
    id                          INT                         NOT NULL AUTO_INCREMENT COMMENT '프로필 번호',
    user_id                     INT                         UNIQUE NOT NULL COMMENT '사용자 번호',
    profile_img_url             VARCHAR(255)                NOT NULL COMMENT '프로필 이미지 저장 경로 (파일명과 확장자명까지 포함)',
    gender                      CHAR(1)                     NOT NULL COMMENT '성별 (M, F, N)',
    bio                         VARCHAR(150)                NOT NULL COMMENT '한 줄 소개',
    univ                        VARCHAR(45)                 NOT NULL COMMENT '재학중이거나 졸업한 대학명',
    location                    VARCHAR(20)                 NOT NULL COMMENT '거주 도시명 (전라남도, 경상남도, 서울특별시 등)',
    created_at                  DATETIME                    NOT NULL DEFAULT NOW() COMMENT '프로필 등록 일자',
    updated_at                  DATETIME                    NOT NULL COMMENT '프로필 변경 일자',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);


-- ######################################################################
--  TEAM
-- ######################################################################
DROP TABLE IF EXISTS teams CASCADE;
CREATE TABLE IF NOT EXISTS teams COMMENT '팀 정보 테이블' (
    id                          INT                         NOT NULL AUTO_INCREMENT COMMENT '팀 번호',
    name                        VARCHAR(100)                NOT NULL COMMENT '팀 명',
    bio                         VARCHAR(150)                NOT NULL COMMENT '팀 간략 소개',
    used_yn                     CHAR(1)                     NOT NULL DEFAULT 'Y' COMMENT '팀 사용 여부',
    deleted_yn                  CHAR(1)                     NOT NULL DEFAULT 'N' COMMENT '팀 삭제 여부 (논리적 삭제)',
    created_by                  INT                         NOT NULL COMMENT '팀 등록자 번호',
    created_at                  DATETIME                    NOT NULL DEFAULT NOW() COMMENT '팀 정보 등록 일자',
    updated_at                  DATETIME                    NOT NULL COMMENT '팀 정보 변경 일자',
    deleted_at                  DATETIME                    NULL COMMENT '팀 정보 삭제 일자 (논리적 삭제)',
    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES users (id)
);

DROP TABLE IF EXISTS team_users CASCADE;
CREATE TABLE IF NOT EXISTS team_users COMMENT '팀원 명단 테이블' (
    team_id                     INT                         NOT NULL COMMENT '팀 번호',
    user_id                     INT                         NOT NULL COMMENT '사용자 번호',
    is_captain                  CHAR(1)                     NOT NULL COMMENT '팀장 여부',
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES teams (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);


-- ######################################################################
--  PROJECT
-- ######################################################################
DROP TABLE IF EXISTS projects CASCADE;
CREATE TABLE IF NOT EXISTS projects COMMENT '프로젝트 정보 테이블' (
    id                          INT                         NOT NULL AUTO_INCREMENT COMMENT '프로젝트 번호',
    team_id                     INT                         NOT NULL COMMENT '프로젝트 진행 팀 번호',
    user_id                     INT                         NOT NULL COMMENT '프로젝트 관리자 (팀장 또는 일반 팀원)',
    summary                     VARCHAR(150)                NOT NULL COMMENT '프로젝트 한 줄 소개',
    started_date                DATETIME                    NOT NULL COMMENT '시작 일자',
    ended_date                  DATETIME                    NOT NULL COMMENT '종료 일자',
    status                      CHAR(1)                     NOT NULL COMMENT '프로젝트 상태 (W, R, D)',
    deleted_yn                  CHAR(1)                     NOT NULL DEFAULT 'N' COMMENT'프로젝트 삭제 여부 (논리적 삭제)',
    created_at                  DATETIME                    NOT NULL DEFAULT NOW() COMMENT '프로젝트 정보 등록 일자',
    updated_at                  DATETIME                    NOT NULL COMMENT '프로젝트 정보 변경 일자',
    deleted_at                  DATETIME                    NULL COMMENT '프로젝트 삭제 일자 (논리적 삭제)',
    PRIMARY KEY (id),
    FOREIGN KEY (team_id) REFERENCES teams (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS project_tasks CASCADE;
CREATE TABLE IF NOT EXISTS project_tasks COMMENT '프로젝트 업무 테이블' (
    id                          INT                         NOT NULL AUTO_INCREMENT COMMENT '프로젝트 업무 번호',
    project_id                  INT                         NOT NULL COMMENT '프로젝트 번호',
    parent_task_id              INT                         NOT NULL COMMENT '상위 업무 번호',
    name                        VARCHAR(150)                NOT NULL COMMENT '업무 명',
    started_date                DATETIME                    NOT NULL COMMENT '업무 시작 일자',
    ended_date                  DATETIME                    NOT NULL COMMENT '업무 종료 일자',
    actual_ended_date           DATETIME                    NULL COMMENT '업무 실제 종료 일자',
    rate                        INT                         NOT NULL DEFAULT 0 COMMENT '업무 진행률',
    sort                        INT                         NOT NULL COMMENT '화면 정렬 번호 (화면 트리 구조 출력을 위한 번호)',
    created_at                  DATETIME                    NOT NULL DEFAULT NOW() COMMENT '업무 등록 일자',
    updated_at                  DATETIME                    NOT NULL COMMENT '업무 변경 일자',
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES projects (id)
);

DROP TABLE IF EXISTS task_assignments CASCADE;
CREATE TABLE IF NOT EXISTS task_assignments COMMENT '프로젝트 업무 할당 테이블' (
    project_task_id             INT                         NOT NULL COMMENT '프로젝트 업무 번호',
    user_id                     INT                         NOT NULL COMMENT '사용자 번호',
    PRIMARY KEY (project_task_id, user_id),
    FOREIGN KEY (project_task_id) REFERENCES project_tasks (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS project_minutes CASCADE;
CREATE TABLE IF NOT EXISTS project_minutes COMMENT '프로젝트 회의록 테이블' (
    id                          INT                         NOT NULL AUTO_INCREMENT COMMENT '프로젝트 회의록 번호',
    project_id                  INT                         NOT NULL COMMENT '프로젝트 번호',
    user_id                     INT                         NOT NULL COMMENT '회의록 작성자 번호',
    title                       VARCHAR(150)                NOT NULL COMMENT '회의록 제목 (입력 하지 않을 경우 회의날짜+회의록)',
    content                     TEXT                        NOT NULL COMMENT '회의 내용',
    meeting_date                DATETIME                    NOT NULL COMMENT '회의 일자',
    created_at                  DATETIME                    NOT NULL DEFAULT NOW() COMMENT '회의록 등록 일자',
    updated_at                  DATETIME                    NOT NULL COMMENT '회의록 변경 일자',
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES projects (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS attendees CASCADE;
CREATE TABLE IF NOT EXISTS attendees COMMENT '회의 참여자 테이블' (
    project_minutes_id          INT                         NOT NULL COMMENT '회의록 번호',
    user_id                     INT                         NOT NULL COMMENT '회의 참여자 번호',
    PRIMARY KEY (project_minutes_id, user_id),
    FOREIGN KEY (project_minutes_id) REFERENCES project_minutes (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);


-- ######################################################################
--  ADMIN
-- ######################################################################
DROP TABLE IF EXISTS task_groups CASCADE;
CREATE TABLE IF NOT EXISTS task_groups COMMENT '상위 업무 그룹 테이블' (
    id                          INT                         NOT NULL AUTO_INCREMENT COMMENT '업무 그룹 번호',
    name                        VARCHAR(45)                 NOT NULL COMMENT '업무 그룹명',
    sort                        INT                         NOT NULL COMMENT '화면 정렬 번호',
    used_yn                     CHAR(1)                     NOT NULL DEFAULT 'Y' COMMENT '사용 여부',
    deleted_yn                  CHAR(1)                     NOT NULL DEFAULT 'N' COMMENT '팀 삭제 여부 (논리적 삭제)',
    created_at                  DATETIME                    NOT NULL DEFAULT NOW() COMMENT '등록 일자',
    updated_at                  DATETIME                    NOT NULL COMMENT '변경 일자',
    deleted_at                  DATETIME                    NULL COMMENT '삭제 일자 (논리적 삭제)',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS login_histories CASCADE;
CREATE TABLE IF NOT EXISTS login_histories COMMENT '로그인 이력 테이블' (
    id                          INT                         NOT NULL AUTO_INCREMENT COMMENT '이력 번호',
    user_id                     INT                         NOT NULL COMMENT '사용자 번호',
    ip_address                  VARCHAR(15)                 NOT NULL COMMENT '로그인 및 로그아웃한 IP 주소',
    user_agent                  VARCHAR(255)                NOT NULL COMMENT '사용자 식별 정보',
    event_type                  CHAR(1)                     NOT NULL COMMENT '접속 유형 (I, O)',
    login_at                    DATETIME                    NOT NULL COMMENT '로그인 일자',
    logout_at                   DATETIME                    NULL COMMENT '로그아웃 일자',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);