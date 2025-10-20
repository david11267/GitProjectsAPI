

-------SEED USER DATA----------
INSERT INTO users (id, name, surname, username, email, user_image_href)
VALUES (
  'user_345yViqMz3J7ScZ637mYj0VydRk',
  'David',
  'Aslan',
  'david11267',
  'david.aslan1999@gmail.com',
  'profileImage -> https://img.clerk.com/eyJ0eXBlIjoicHJveHkiLCJzcmMiOiJodHRwczovL2ltYWdlcy5jbGVyay5kZXYvb2F1dGhfZ2l0aHViL2ltZ18zNDV5Vm9YbGVGR0VtVEhrdzB6UzVWVUNhTFoifQ'
),
(
     'aliceId123',
     'Alice',
     'Wonderland',
     'Alice11267',
     'alice.wonderland@gmail.com',
     'https://static.wikia.nocookie.net/disney/images/7/75/Profile_-_Alice.jpeg/revision/latest?cb=20250104014515'
   );


-------SEED Skills DATA----------

INSERT INTO skills (id, name, description, icon_url, type)
VALUES
(1, 'Java', 'Java programming language', 'https://example.com/java.png', 'Language'),
(2, 'Spring Boot', 'Spring Boot framework', 'https://example.com/spring.png', 'Framework'),
(3, 'Communication', 'Verbal and written communication skills', 'https://example.com/communication.png', 'Other');

-- ----------------------------
-- User-Skills Join Table
-- ----------------------------
INSERT INTO user_skills (user_id, skill_id)
VALUES
('user_345yViqMz3J7ScZ637mYj0VydRk', 1),  -- David knows Java
('user_345yViqMz3J7ScZ637mYj0VydRk', 2),  -- David knows Spring Boot
('user_345yViqMz3J7ScZ637mYj0VydRk', 3),  -- David knows Communication

('aliceId123', 2),  -- Alice knows Spring Boot
('aliceId123', 3);  -- Alice knows Communication
