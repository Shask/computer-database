docker stop mysql
docker rm mysql
docker create --name mysql -e MYSQL_ROOT_PASSWORD=root  mysql/mysql-server
docker cp 1-SCHEMA.sql mysql:/1-SCHEMA.sql
docker cp 2-PRIVILEGES.sql mysql:/2-PRIVILEGES.sql
docker cp 3-ENTRIES.sql mysql:/3-ENTRIES.sql
docker start mysql
