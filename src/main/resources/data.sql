-- =========================
-- Users
-- =========================
INSERT INTO users (
    user_id, nickname, birth_date, gender, role, region, phone_number, reputation, social_type, social_id, status, profile_image_url, ci_hmac, created_at, updated_at, deleted_at
) VALUES (
    1, 'testuser', '1990-01-01', 'MALE', 'USER', 'SEOUL', '01012345678', 0, 'PHONE', NULL, 'ACTIVE', NULL, 'dummy-hmac', now(), now(), NULL
);

-- =========================
-- Hobbies
-- =========================
INSERT INTO hobbies (hobby_id, name, created_at, updated_at, deleted_at) VALUES
(1, '독서', now(), now(), NULL),
(2, '등산', now(), now(), NULL);

-- =========================
-- UserHobbies
-- =========================
INSERT INTO user_hobbies (user_hobby_id, user_id, hobby_id, created_at, updated_at, deleted_at) VALUES
(1, 1, 1, now(), now(), NULL),
(2, 1, 2, now(), now(), NULL);
