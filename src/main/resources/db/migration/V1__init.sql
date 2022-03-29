CREATE TABLE consumer
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
    CONSTRAINT consumer_pkey PRIMARY KEY (id)
);

CREATE TABLE location
(
    id           BIGINT       NOT NULL,
    business_key CHAR(36)     NOT NULL,
    location     VARCHAR(255) NOT NULL,
    consumer_id  BIGINT,
    CONSTRAINT location_pkey PRIMARY KEY (id)
);

CREATE TABLE category
(
    id           BIGINT       NOT NULL,
    business_key CHAR(36)     NOT NULL,
    name         VARCHAR(255) NOT NULL,
    parent_id    BIGINT,
    CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE supplier
(
    id           BIGINT       NOT NULL,
    business_key CHAR(36)     NOT NULL,
    currency     VARCHAR(255),
    email        VARCHAR(255) NOT NULL,
    location     VARCHAR(255),
    name         VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    role         VARCHAR(255),
    CONSTRAINT supplier_pkey PRIMARY KEY (id)
);

CREATE TABLE "order"
(
    id              BIGINT   NOT NULL,
    business_key    CHAR(36) NOT NULL,
    consumer_id     BIGINT,
    subscription_id BIGINT,
    CONSTRAINT order_pkey PRIMARY KEY (id)
);

CREATE TABLE form_data
(
    form_question_id BIGINT       NOT NULL,
    order_id         BIGINT       NOT NULL,
    answer           VARCHAR(255) NOT NULL,
    business_key     CHAR(36)     NOT NULL,
    CONSTRAINT form_data_pkey PRIMARY KEY (form_question_id, order_id)
);

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

ALTER TABLE consumer
    ADD CONSTRAINT uk_2036biu464fmfma0ce3gy48t9 UNIQUE (phone_number);

ALTER TABLE consumer
    ADD CONSTRAINT uk_adl08ilt4vgk91v0pv48of2fu UNIQUE (email);

ALTER TABLE consumer
    ADD CONSTRAINT uk_ovwswpgfaub2t1tj8ao2snlxb UNIQUE (business_key);

ALTER TABLE location
    ADD CONSTRAINT uk_6ucv6spiommpw0rr4eueb0dv UNIQUE (business_key);

ALTER TABLE category
    ADD CONSTRAINT uk_d6kaamnrtxf5i61owi8hhka0p UNIQUE (business_key);

ALTER TABLE supplier
    ADD CONSTRAINT uk_g7qiwwu4vpciysmeeyme9gg1d UNIQUE (email);

ALTER TABLE supplier
    ADD CONSTRAINT uk_gd9qro7i3c7jhuaps7wsxe6ir UNIQUE (phone_number);

ALTER TABLE supplier
    ADD CONSTRAINT uk_ppdg9bq2fv24sdkxkso6sx77s UNIQUE (business_key);

ALTER TABLE "order"
    ADD CONSTRAINT uk_ipxqkjqg9ds94i6sfa0r4n7lq UNIQUE (business_key);

ALTER TABLE form_data
    ADD CONSTRAINT uk_j2a8n9ejvyqkfeph2fqsy4obm UNIQUE (business_key);

ALTER TABLE subscription
    ADD CONSTRAINT uk_jhbap3fquyl7y9ccgtgqsan4k UNIQUE (business_key);

ALTER TABLE form_question
    ADD CONSTRAINT uk_kc4qsmjdseceu1pm1c9cnl6od UNIQUE (business_key);

ALTER TABLE feedback
    ADD CONSTRAINT uk_re2gl6avxi69r3jfd3nyf8j4x UNIQUE (business_key);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence AS bigint START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

ALTER TABLE location
    ADD CONSTRAINT fk23uh6c387lmdwc9u7u38dwmmt FOREIGN KEY (consumer_id) REFERENCES consumer (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE category
    ADD CONSTRAINT fk2y94svpmqttx80mshyny85wqr FOREIGN KEY (parent_id) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE form_question
    ADD CONSTRAINT fka4a4wmonsudcnftxxb1h8cuxq FOREIGN KEY (subscription_id) REFERENCES subscription (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "order"
    ADD CONSTRAINT fkbyve88iut77mq0frhg1jfpimd FOREIGN KEY (subscription_id) REFERENCES subscription (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE subscription
    ADD CONSTRAINT fkhjbo34106f7hj9g5afmrga4gf FOREIGN KEY (supplier_id) REFERENCES supplier (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE consumer
    ADD CONSTRAINT fkhjgxgpq2r61a9ycp9brk3e24m FOREIGN KEY (default_location_id) REFERENCES location (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE subscription
    ADD CONSTRAINT fknj55m79pro2qanuayd9ckn3st FOREIGN KEY (category_id) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "order"
    ADD CONSTRAINT fkq7g8onl1mj0bx8m87qj3xcl6d FOREIGN KEY (consumer_id) REFERENCES consumer (id) ON UPDATE NO ACTION ON DELETE NO ACTION;