CREATE TABLE attachment
(
    attachment_id VARCHAR(255) NOT NULL,
    data          OID,
    filename      VARCHAR(255),
    uploaded_at   TIMESTAMP WITHOUT TIME ZONE,
    url           VARCHAR(255),
    task_id       VARCHAR(255) NOT NULL,
    CONSTRAINT attachment_pkey PRIMARY KEY (attachment_id)
);

CREATE TABLE board
(
    board_id      VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    description   VARCHAR(255),
    name          VARCHAR(255),
    created_by_id VARCHAR(255) NOT NULL,
    access_code   VARCHAR(255),
    CONSTRAINT board_pkey PRIMARY KEY (board_id)
);

CREATE TABLE board_collaborators
(
    board_id VARCHAR(255) NOT NULL,
    user_id  VARCHAR(255) NOT NULL
);

CREATE TABLE filedb
(
    file_id VARCHAR(255) NOT NULL,
    data    OID,
    name    VARCHAR(255),
    type    VARCHAR(255),
    CONSTRAINT filedb_pkey PRIMARY KEY (file_id)
);

CREATE TABLE lists
(
    list_id  VARCHAR(255) NOT NULL,
    name     VARCHAR(255),
    position INTEGER,
    board_id VARCHAR(255) NOT NULL,
    CONSTRAINT lists_pkey PRIMARY KEY (list_id)
);

CREATE TABLE notification
(
    notification_id VARCHAR(255) NOT NULL,
    message         VARCHAR(255),
    read            BOOLEAN,
    sent_at         TIMESTAMP WITHOUT TIME ZONE,
    recipient_id    VARCHAR(255) NOT NULL,
    CONSTRAINT notification_pkey PRIMARY KEY (notification_id)
);

CREATE TABLE task
(
    task_id     VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    description VARCHAR(255),
    due_date    date,
    status      VARCHAR(255),
    title       VARCHAR(255),
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    assignee_id VARCHAR(255),
    list_id     VARCHAR(255) NOT NULL,
    CONSTRAINT task_pkey PRIMARY KEY (task_id)
);

CREATE TABLE user_entity
(
    user_id    VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    email      VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NOT NULL,
    CONSTRAINT user_entity_pkey PRIMARY KEY (user_id)
);

ALTER TABLE user_entity
    ADD CONSTRAINT uk4xad1enskw4j1t2866f7sodrx UNIQUE (email);

ALTER TABLE board
    ADD CONSTRAINT fk6ccuvoxsm9ai3xtc51wxgug5a FOREIGN KEY (created_by_id) REFERENCES user_entity (user_id) ON DELETE NO ACTION;

ALTER TABLE board_collaborators
    ADD CONSTRAINT fk6jbi4cjb37nwinuejdu42j3so FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE NO ACTION;

ALTER TABLE task
    ADD CONSTRAINT fk6ll0nyymq8atoika8wxcobkcl FOREIGN KEY (list_id) REFERENCES lists (list_id) ON DELETE NO ACTION;

ALTER TABLE task
    ADD CONSTRAINT fkapslua6t2johvvlhb0wfst60e FOREIGN KEY (assignee_id) REFERENCES user_entity (user_id) ON DELETE NO ACTION;

ALTER TABLE lists
    ADD CONSTRAINT fkf9wbemfvw36eoj3lqgeuj6h3w FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE NO ACTION;

ALTER TABLE board_collaborators
    ADD CONSTRAINT fkhmqoqb895i15t9xqgoe6gthwg FOREIGN KEY (user_id) REFERENCES user_entity (user_id) ON DELETE NO ACTION;

ALTER TABLE attachment
    ADD CONSTRAINT fkliwb3s1jmhbcrq2upsyo2cftn FOREIGN KEY (task_id) REFERENCES task (task_id) ON DELETE NO ACTION;

ALTER TABLE notification
    ADD CONSTRAINT fkr3h2eoad41q3818bkl0moyxls FOREIGN KEY (recipient_id) REFERENCES user_entity (user_id) ON DELETE NO ACTION;