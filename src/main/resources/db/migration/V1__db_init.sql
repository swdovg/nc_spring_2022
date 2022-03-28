CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE category
(
    id        BIGINT       NOT NULL,
    name      VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE consumer
(
    id                  BIGINT       NOT NULL,
    name                VARCHAR(255) NOT NULL,
    phone_number        VARCHAR(255) NOT NULL,
    email               VARCHAR(255) NOT NULL,
    password            VARCHAR(255) NOT NULL,
    currency            VARCHAR(255),
    default_location_id BIGINT,
    CONSTRAINT pk_consumer PRIMARY KEY (id)
);

CREATE TABLE feedback
(
    consumer_id     BIGINT       NOT NULL,
    subscription_id BIGINT       NOT NULL,
    title           VARCHAR(255) NOT NULL,
    text            VARCHAR(255) NOT NULL,
    rating          INTEGER      NOT NULL,
    CONSTRAINT pk_feedback PRIMARY KEY (consumer_id)
);

CREATE TABLE form_data
(
    form_question_id BIGINT       NOT NULL,
    order_id         BIGINT       NOT NULL,
    answer           VARCHAR(255) NOT NULL,
    CONSTRAINT pk_formdata PRIMARY KEY (form_question_id)
);

CREATE TABLE form_question
(
    id             BIGINT       NOT NULL,
    question       VARCHAR(255) NOT NULL,
    title          VARCHAR(255) NOT NULL,
    description    VARCHAR(255) NOT NULL,
    price          BIGINT       NOT NULL,
    currency       VARCHAR(255),
    average_rating DOUBLE PRECISION,
    supplier_id    BIGINT,
    category_id    BIGINT,
    CONSTRAINT pk_formquestion PRIMARY KEY (id)
);

CREATE TABLE location
(
    id                  BIGINT       NOT NULL,
    location            VARCHAR(255) NOT NULL,
    name                VARCHAR(255) NOT NULL,
    phone_number        VARCHAR(255) NOT NULL,
    email               VARCHAR(255) NOT NULL,
    password            VARCHAR(255) NOT NULL,
    currency            VARCHAR(255),
    default_location_id BIGINT,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE "order"
(
    id                  BIGINT       NOT NULL,
    name                VARCHAR(255) NOT NULL,
    phone_number        VARCHAR(255) NOT NULL,
    email               VARCHAR(255) NOT NULL,
    password            VARCHAR(255) NOT NULL,
    currency            VARCHAR(255),
    default_location_id BIGINT,
    title               VARCHAR(255) NOT NULL,
    description         VARCHAR(255) NOT NULL,
    price               BIGINT       NOT NULL,
    average_rating      DOUBLE PRECISION,
    supplier_id         BIGINT,
    category_id         BIGINT,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE subscription
(
    id             BIGINT       NOT NULL,
    title          VARCHAR(255) NOT NULL,
    description    VARCHAR(255) NOT NULL,
    price          BIGINT       NOT NULL,
    currency       VARCHAR(255),
    average_rating DOUBLE PRECISION,
    supplier_id    BIGINT,
    category_id    BIGINT,
    CONSTRAINT pk_subscription PRIMARY KEY (id)
);

CREATE TABLE supplier
(
    id           BIGINT       NOT NULL,
    name         VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    currency     VARCHAR(255),
    location     VARCHAR(255),
    CONSTRAINT pk_supplier PRIMARY KEY (id)
);

ALTER TABLE consumer
    ADD CONSTRAINT uc_consumer_email UNIQUE (email);

ALTER TABLE consumer
    ADD CONSTRAINT uc_consumer_phonenumber UNIQUE (phone_number);

ALTER TABLE location
    ADD CONSTRAINT uc_location_email UNIQUE (email);

ALTER TABLE location
    ADD CONSTRAINT uc_location_phonenumber UNIQUE (phone_number);

ALTER TABLE "order"
    ADD CONSTRAINT uc_order_email UNIQUE (email);

ALTER TABLE "order"
    ADD CONSTRAINT uc_order_phonenumber UNIQUE (phone_number);

ALTER TABLE supplier
    ADD CONSTRAINT uc_supplier_email UNIQUE (email);

ALTER TABLE supplier
    ADD CONSTRAINT uc_supplier_phonenumber UNIQUE (phone_number);

ALTER TABLE category
    ADD CONSTRAINT FK_CATEGORY_ON_PARENT FOREIGN KEY (parent_id) REFERENCES category (id);

ALTER TABLE consumer
    ADD CONSTRAINT FK_CONSUMER_ON_DEFAULTLOCATION FOREIGN KEY (default_location_id) REFERENCES location (id);

ALTER TABLE form_question
    ADD CONSTRAINT FK_FORMQUESTION_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE form_question
    ADD CONSTRAINT FK_FORMQUESTION_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE location
    ADD CONSTRAINT FK_LOCATION_ON_DEFAULTLOCATION FOREIGN KEY (default_location_id) REFERENCES location (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_DEFAULTLOCATION FOREIGN KEY (default_location_id) REFERENCES location (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE subscription
    ADD CONSTRAINT FK_SUBSCRIPTION_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE subscription
    ADD CONSTRAINT FK_SUBSCRIPTION_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);