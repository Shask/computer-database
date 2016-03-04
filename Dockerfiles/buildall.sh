#Building Jenkins docker ...
cd jenkins
docker build -t shask/jenkinsdocker .
docker push shask/jenkinsdocker
cd ..

#Building db docker ...
cd db
docker build -t shask/mysql .
docker push shask/mysql
cd ..