CREATE TABLE IF NOT EXISTS rates (
  id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
  days                   VARCHAR      NOT NULL,
  times                  VARCHAR      NOT NULL,
  tz                     VARCHAR      NOT NULL,
  price                  INT          NOT NULL
);