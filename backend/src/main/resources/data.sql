

-------SEED USER DATA----------
INSERT INTO users (id, name, surname, username, email, user_image_href)
VALUES (
  'user_345yViqMz3J7ScZ637mYj0VydRk',
  'David',
  'Aslan',
  'david11267',
  'david.aslan1999@gmail.com',
  'profileImage -> https://img.clerk.com/eyJ0eXBlIjoicHJveHkiLCJzcmMiOiJodHRwczovL2ltYWdlcy5jbGVyay5kZXYvb2F1dGhfZ2l0aHViL2ltZ18zNDV5Vm9YbGVGR0VtVEhrdzB6UzVWVUNhTFoifQ'
)
ON CONFLICT (id) DO NOTHING;
