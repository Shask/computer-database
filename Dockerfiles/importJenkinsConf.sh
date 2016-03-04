#Import , build and push the new version of Jenkins.
docker cp dockerfiles_jenkins_1:/var/jenkins_home/jobs/computerdb/config.xml jenkins/config.xml
cd jenkins
docker build -t shask/jenkinsdocker .
docker push shask/jenkinsdocker
cd ..