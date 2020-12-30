DIR=$(dirname $0)

echo "Dir name $DIR"

set -e

APP_IMAGE1="test-app-gateway:1.4"

docker build -f app-gateway.docker -t mozafaq/${APP_IMAGE1} ..

APP_IMAGE2="test-app-worker-compute:1.4"

docker build -f app-worker-compute.docker -t mozafaq/${APP_IMAGE2} ..

APP_IMAGE3="test-app-worker-controller:1.4"

docker build -f app-worker-controller.docker -t mozafaq/${APP_IMAGE3} ..


if [ $# == 1  ]
then
    if [ $1 == "--push" ]
    then
        echo "Pushing images...."
        docker push mozafaq/${APP_IMAGE1}
        docker push mozafaq/${APP_IMAGE2}
        docker push mozafaq/${APP_IMAGE3}
    fi
fi
  
echo "Done...."

