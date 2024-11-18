CREATE TABLE transaction (
  id UUID NOT NULL PRIMARY KEY,
  amount FLOAT NOT NULL,
  account_id uuid NOT NULL,
  type VARCHAR(15) NOT NULL,
  occurred_in DATE NOT NULL,
  establishment_id UUID NOT NULL,
  idempotency_key VARCHAR(50) UNIQUE,
  CONSTRAINT fk_establishment FOREIGN KEY (establishment_id) REFERENCES establishment (id),
  CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account (id)
);
