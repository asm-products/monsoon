CREATE TABLE subscribers (
  id         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp,
  deleted_at timestamp,
  source     text,
  endpoint   text NOT NULL UNIQUE CHECK (endpoint <> '')
);
