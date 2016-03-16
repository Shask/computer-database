if [ -e $(docker ps -aq -f "name=mysql") ]
then 
	echo No mysl docker running ...
else 
	docker rm -f mysql
fi  
if [ -e $(docker ps -aq -f "name=webapp") ]
then 
	echo No mysl docker running ...
else 
	docker rm -f webapp
fi 

docker run --name="mysql" -d shask/mysql:2.0
docker run --name="webapp" --link="mysql:localhost" -p 9999:8080 shask/computerdb-webapp 