ALTER TABLE users_access_tokens
  ADD is_expired TINYINT(1) NOT NULL DEFAULT '0';