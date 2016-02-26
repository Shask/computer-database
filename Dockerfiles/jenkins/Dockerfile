FROM jenkins:latest
 
ENV DEBIAN_FRONTEND noninteractive
 
USER root
 
RUN curl -sSL https://get.docker.com/ | sh \
    && rm -rf /var/lib/apt/lists/*

ENV DOCKER_HOST tcp://dind:2375
