FROM devopsedu/webapp
RUN rm -rf /var/www/html/*
ADD .  /var/www/html
EXPOSE 80
CMD ["apachectl", "-D", "FOREGROUND"]
