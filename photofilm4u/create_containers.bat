cd ../autumn-2025-course
docker build -t afloresca/course:latest .
docker push afloresca/course:latest

cd ../autumn-2025-notification
docker build -t afloresca/notification:latest .
docker push afloresca/notification:latest

cd ../autumn-2025-productcatalog
docker build -t afloresca/productcatalog:latest .
docker push afloresca/productcatalog:latest

cd ../autumn-2025-user
docker build -t afloresca/user:latest .
docker push afloresca/user:latest


cd ../photofilm4u
echo All containers have been built and pushed successfully. 
echo executing docker-compose up -d

docker-compose up -d
