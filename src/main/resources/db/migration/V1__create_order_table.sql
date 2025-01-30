-- Create Order table

CREATE TABLE orders (
  id BIGSERIAL PRIMARY KEY,
  order_date TIMESTAMP NOT NULL,
  status VARCHAR(255) NOT NULL
);
