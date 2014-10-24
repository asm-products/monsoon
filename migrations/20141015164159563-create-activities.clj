;; migrations/20141015164159563-create-activities.clj

(defn up []
  ["CREATE TABLE activities (
    id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sent_at    timestamp,
    headers    text,
    source     text,
    payload    text)"])

(defn down []
  ["DROP TABLE activities"])
