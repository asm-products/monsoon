;; migrations/20141015171732280-add-product-to-subscribers.clj

(defn up []
  ["ALTER TABLE subscribers ADD COLUMN product text NOT NULL DEFAULT ''"])

(defn down []
  ["ALTER TABLE subscribers DROP COLUMN product"])
