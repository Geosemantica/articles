DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS articles;

DELETE
FROM flyway_schema_history
WHERE version = '2024.09.24.01';