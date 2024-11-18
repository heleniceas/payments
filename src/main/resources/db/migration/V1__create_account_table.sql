CREATE TABLE account (
    id uuid NOT NULL PRIMARY KEY,
    number varchar(20) NOT NULL,
    cash_amount FLOAT NOT NULL,
    meal_amount FLOAT NOT NULL,
    food_amount FLOAT NOT NULL,
    created_at timestamp default current_timestamp NOT NULL,
    updated_at timestamp default current_timestamp NOT NULL
);

CREATE UNIQUE INDEX account_number_index ON account (number);
