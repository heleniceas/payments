CREATE TABLE establishment (
  id uuid NOT NULL PRIMARY KEY,
  name varchar(50) NOT NULL,
  type varchar(20) NULL
);

CREATE UNIQUE INDEX establishment_name_index ON establishment(name);

