FROM jenkins:latest
 
ENV DEBIAN_FRONTEND noninteractive
 
USER root
 
RUN curl -sSLk https://get.docker.com/ | sh \
    && rm -rf /var/lib/apt/lists/*

COPY plugins.txt /usr/share/jenkins/plugins.txt
COPY config.xml /var/jenkins_home/jobs/computerdb/config.xml
COPY glazer-deploy.sh /var/glazer-deploy.sh

RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

ENV DOCKER_HOST tcp://dind:2375
