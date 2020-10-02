DIR=$(dirname $0)

echo "Dir name $DIR"

APP_IMAGE="test-springboot-app:1.1"

docker build -f Dockerfile -t ${APP_IMAGE} ..


docker container run -it -d -p 8081:8080 --name container-sample-app ${APP_IMAGE}

echo "Started...."

