FROM nginx:stable-alpine-perl

COPY frontend-service/dist /usr/share/nginx/html

COPY frontend-service/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]