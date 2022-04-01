CREATE TABLE subscription
(
    id             BIGINT       NOT NULL,
    average_rating FLOAT8,
    business_key   CHAR(36)     NOT NULL,
    currency       VARCHAR(255),
    description    VARCHAR(255) NOT NULL,
    price          BIGINT       NOT NULL,
    title          VARCHAR(255) NOT NULL,
    category_id    BIGINT,
    supplier_id    BIGINT,
    CONSTRAINT subscription_pkey PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id                  BIGINT       NOT NULL,
    business_key        CHAR(36)     NOT NULL,
    currency            VARCHAR(255),
    email               VARCHAR(255) NOT NULL,
    name                VARCHAR(255) NOT NULL,
    password            VARCHAR(255),
    phone_number        VARCHAR(255),
    provider            VARCHAR(255),
    provider_id         VARCHAR(255),
    role                VARCHAR(255),
    default_location_id BIGINT,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE location
(
    id           BIGINT       NOT NULL,
    business_key CHAR(36)     NOT NULL,
    location     VARCHAR(255) NOT NULL,
    user_id      BIGINT,
    CONSTRAINT location_pkey PRIMARY KEY (id)
);

CREATE TABLE "order"
(
    id              BIGINT   NOT NULL,
    business_key    CHAR(36) NOT NULL,
    subscription_id BIGINT,
    user_id         BIGINT,
    CONSTRAINT order_pkey PRIMARY KEY (id)
);

CREATE TABLE category
(
    id           BIGINT       NOT NULL,
    business_key CHAR(36)     NOT NULL,
    name         VARCHAR(255) NOT NULL,
    parent_id    BIGINT,
    CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE form_data
(
    form_question_id BIGINT       NOT NULL,
    order_id         BIGINT       NOT NULL,
    answer           VARCHAR(255) NOT NULL,
    business_key     CHAR(36)     NOT NULL,
    CONSTRAINT form_data_pkey PRIMARY KEY (form_question_id, order_id)
);

CREATE TABLE form_question
(
    id              BIGINT       NOT NULL,
    business_key    CHAR(36)     NOT NULL,
    question        VARCHAR(255) NOT NULL,
    subscription_id BIGINT,
    CONSTRAINT form_question_pkey PRIMARY KEY (id)
);

CREATE TABLE feedback
(
    consumer_id     BIGINT       NOT NULL,
    subscription_id BIGINT       NOT NULL,
    business_key    CHAR(36)     NOT NULL,
    rating          INTEGER      NOT NULL,
    text            TEXT         NOT NULL,
    title           VARCHAR(255) NOT NULL,
    CONSTRAINT feedback_pkey PRIMARY KEY (consumer_id, subscription_id)
);

ALTER TABLE subscription
    ADD CONSTRAINT uk_jhbap3fquyl7y9ccgtgqsan4k UNIQUE (business_key);

ALTER TABLE "user"
    ADD CONSTRAINT uk_4bgmpi98dylab6qdvf9xyaxu4 UNIQUE (phone_number);

ALTER TABLE "user"
    ADD CONSTRAINT uk_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email);

ALTER TABLE "user"
    ADD CONSTRAINT uk_smvekpngej5uo9n73ani5oxys UNIQUE (business_key);

ALTER TABLE location
    ADD CONSTRAINT uk_ndmw96jtvo3mq70rcdifvhmbi UNIQUE (business_key);

ALTER TABLE "order"
    ADD CONSTRAINT uk_ipxqkjqg9ds94i6sfa0r4n7lq UNIQUE (business_key);

ALTER TABLE category
    ADD CONSTRAINT uk_tfaydch44g7w53336v8nkr2fm UNIQUE (business_key);

ALTER TABLE form_data
    ADD CONSTRAINT uk_j2a8n9ejvyqkfeph2fqsy4obm UNIQUE (business_key);

ALTER TABLE form_question
    ADD CONSTRAINT uk_rfa6249si3skphl4kc2ytc7kj UNIQUE (business_key);

ALTER TABLE feedback
    ADD CONSTRAINT uk_re2gl6avxi69r3jfd3nyf8j4x UNIQUE (business_key);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence AS bigint START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

ALTER TABLE category
    ADD CONSTRAINT fk2y94svpmqttx80mshyny85wqr FOREIGN KEY (parent_id) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "order"
    ADD CONSTRAINT fk5ds52cnxjw9c99ovccne0axk0 FOREIGN KEY (user_id) REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE subscription
    ADD CONSTRAINT fk5sf4dn4fb13lxl3cjtca9rb9a FOREIGN KEY (supplier_id) REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "user"
    ADD CONSTRAINT fk7nulm3frbb27s5qrgg2q4y8x7 FOREIGN KEY (default_location_id) REFERENCES location (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE form_question
    ADD CONSTRAINT fka4a4wmonsudcnftxxb1h8cuxq FOREIGN KEY (subscription_id) REFERENCES subscription (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE location
    ADD CONSTRAINT fkaehfg4ynpsflko37dstpeptas FOREIGN KEY (user_id) REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "order"
    ADD CONSTRAINT fkbyve88iut77mq0frhg1jfpimd FOREIGN KEY (subscription_id) REFERENCES subscription (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE subscription
    ADD CONSTRAINT fknj55m79pro2qanuayd9ckn3st FOREIGN KEY (category_id) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;