CREATE TABLE articles
(
    id         BIGSERIAL PRIMARY KEY,
    text       TEXT      NOT NULL,
    title      TEXT      NOT NULL,
    author_id  UUID      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    is_removed BOOLEAN   NOT NULL
);

CREATE TABLE comments
(
    id         BIGSERIAL PRIMARY KEY,
    text       TEXT                            NOT NULL,
    author_id  UUID                            NOT NULL,
    created_at TIMESTAMP                       NOT NULL,
    is_removed BOOLEAN                         NOT NULL,
    article_id BIGINT REFERENCES articles (id) NOT NULL,
    reply_to   BIGINT REFERENCES comments (id)
);
