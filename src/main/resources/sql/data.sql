-- 스크립트 실행시 기본적으로 추가되어 있어야 할 데이터

INSERT INTO task_groups (name, sort, updated_at)
VALUES ('요구사항 분석', 1, NOW()),
       ('시스템 설계', 2, NOW()),
       ('구현', 3, NOW()),
       ('테스트', 4, NOW()),
       ('유지보수', 5, NOW());