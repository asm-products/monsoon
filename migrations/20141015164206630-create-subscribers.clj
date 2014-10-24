;; migrations/20141015164206630-create-subscribers.clj

(defn up []
  ["CREATE TABLE subscribers (
    id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp,
    deleted_at timestamp,
    source     text,
    endpoint   text NOT NULL UNIQUE CHECK (endpoint <> ''))"])

(defn down []
  ["DROP TABLE subscribers"])
