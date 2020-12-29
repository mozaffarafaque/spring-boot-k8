DIR=$(dirname $0)

echo "Dir name $DIR"

set -e

APP_IMAGE1="test-springboot-app:1.3"

docker build -f app-gateway.docker -t ${APP_IMAGE1} ..

APP_IMAGE2="test-worker-app:1.3"

docker build -f app-worker.docker -t ${APP_IMAGE2} ..

if [ $# == 1  ]
then
    if [ $1 == "--push" ]
    then
        echo "Pushing image...."
        docker push mozafaq/${APP_IMAGE1}
        docker push mozafaq/${APP_IMAGE2}
    fi
fi
  
echo "Done...."

