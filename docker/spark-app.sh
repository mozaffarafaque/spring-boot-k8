DIR=$(dirname $0)

echo "Dir name $DIR"

set -e

APP_IMAGE="spark-app-k8s:1.1"

docker build -f spark-app.docker -t mozafaq/${APP_IMAGE} ..

if [ $# == 1  ]
then
    if [ $1 == "--push" ]
    then
        echo "Pushing images...."
        docker push mozafaq/${APP_IMAGE}
    fi
fi

echo "Done...."