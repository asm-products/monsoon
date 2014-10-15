CREATE TABLE activities (
  id         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  sent_at    timestamp,
  headers    text,
  source     text,
  payload    text
);
