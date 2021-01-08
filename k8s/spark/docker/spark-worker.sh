
DIR=$(dirname $0)

echo "Dir name $DIR"

set -e

APP_IMAGE="spark-worker-k8s:3.1.0"

docker build -f spark-worker.docker -t mozafaq/${APP_IMAGE} .

if [ $# == 1  ]
then
    if [ $1 == "--push" ]
    then
        echo "Pushing images...."
        docker push mozafaq/${APP_IMAGE}
    fi
fi

echo "Done...."