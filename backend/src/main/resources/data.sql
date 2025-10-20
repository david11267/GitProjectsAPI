-- ----------------------------
-- Users
-- ----------------------------
INSERT INTO users (id, name, surname, username, email, profile_image)
VALUES
('david123', 'David', 'Aslan', 'david11267', 'david.aslan1999@gmail.com', 'https://img.clerk.com/eyJ0eXBlIjoicHJveHkiLCJzcmMiOiJodHRwczovL2ltYWdlcy5jbGVyay5kZXYvb2F1dGhfZ2l0aHViL2ltZ18zNDV5Vm9YbGVGR0VtVEhrdzB6UzVWVUNhTFoifQ'),
('aliceId123', 'Alice', 'Wonderland', 'Alice11267', 'alice.wonderland@gmail.com', 'https://static.wikia.nocookie.net/disney/images/7/75/Profile_-_Alice.jpeg/revision/latest?cb=20250104014515'),
('bobId456', 'Bob', 'Builder', 'bob11267', 'bob.builder@gmail.com', 'https://example.com/bob.png');

-- ----------------------------
-- Skills
-- ----------------------------
INSERT INTO skills (id, name, description, type)
VALUES
(1, 'Java', 'Java programming language',  'Language'),
(2, 'Spring Boot', 'Spring Boot framework', 'Framework'),
(3, 'Communication', 'Verbal and written communication skills',  'Other'),
(4, 'SQL', 'SQL database querying', 'Language');

-- ----------------------------
-- Projects
-- ----------------------------
INSERT INTO project (id, name, user_id, html_url, description, created_at, updated_at, pushed_at, size_in_kb)
VALUES
('proj1', 'Spring API', 'david123', 'https://github.com/david11267/spring-api', 'REST API built with Spring Boot', now(), now(), now(), 512),
('proj2', 'Portfolio Website', 'aliceId123', 'https://github.com/Alice11267/portfolio', 'Personal portfolio site', now(), now(), now(), 256),
('proj3', 'Database App', 'bobId456', 'https://github.com/bob11267/db-app', 'SQL database management app', now(), now(), now(), 1024);

-- ----------------------------
-- Project-Skills Join Table
-- ----------------------------
INSERT INTO project_skills (project_id, skill_id)
VALUES
('proj1', 1), -- Java
('proj1', 2), -- Spring Boot
('proj1', 3), -- Communication

('proj2', 2), -- Spring Boot
('proj2', 3), -- Communication

('proj3', 1), -- Java
('proj3', 4); -- SQL
