FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=rootroot

COPY ../public/init.sql /docker-entrypoint-initdb.d/

EXPOSE 3307