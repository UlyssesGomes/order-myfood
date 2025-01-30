-- Create Order Item table.

CREATE TABLE order_items (
  id BIGSERIAL PRIMARY KEY,
  description VARCHAR(255) DEFAULT NULL,
  quantity INT NOT NULL,
  order_id BIGINT NOT NULL,
  CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
