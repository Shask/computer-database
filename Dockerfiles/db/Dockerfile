FROM mysql/mysql-server:5.5

COPY 1-SCHEMA.sql /docker-entrypoint-initdb.d/
COPY 2-PRIVILEGES.sql /docker-entrypoint-initdb.d/
COPY 3-ENTRIES.sql /docker-entrypoint-initdb.d/
COPY 1-SCHEMA.sql /
COPY 2-PRIVILEGES.sql /
COPY 3-ENTRIES.sql /



ENV MYSQL_ROOT_PASSWORD "root"
