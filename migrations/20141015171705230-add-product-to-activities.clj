;; migrations/20141015171705230-add-product-to-activities.clj

(defn up []
  ["ALTER TABLE activities ADD COLUMN product text NOT NULL DEFAULT ''"])

(defn down []
  ["ALTER TABLE activities DROP COLUMN product"])
