-- =========================
-- Users
-- =========================
INSERT INTO users (
    nickname, birth_date, gender, role, region, phone_number, reputation, social_type, social_id, status, profile_image_url, ci_hmac, created_at, updated_at, deleted_at
) VALUES (
    'testuser', '1990-01-01', 'MALE', 'USER', 'SEOUL', '01012345678', 0, 'PHONE', NULL, 'ACTIVE', NULL, 'dummy-hmac', now(), now(), NULL
);

-- =========================
-- Hobbies
-- =========================
INSERT INTO hobbies (name, created_at, updated_at, deleted_at) VALUES
('독서', now(), now(), NULL),
('등산', now(), now(), NULL);

-- =========================
-- UserHobbies
-- =========================
INSERT INTO user_hobbies (user_id, hobby_id, created_at, updated_at, deleted_at) VALUES
(1, 1, now(), now(), NULL),
(1, 2, now(), now(), NULL);

-- =========================
-- 시퀀스 재설정 (중복 키 방지)
-- =========================
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('hobbies_id_seq', (SELECT MAX(id) FROM hobbies));
SELECT setval('user_hobbies_id_seq', (SELECT MAX(id) FROM user_hobbies));
