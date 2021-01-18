
DIR=$(dirname $0)

echo "Dir name $DIR"

set -e

APP_IMAGE="spark-task-server:1.1"

echo "Building spark app server jar ...."

cd ../../spark-server/ && ./gradlew clean build && cd -

mkdir -p tmp

cp ../../spark-server/build/libs/spark-server-1.0.jar ./tmp/spark-task-server.jar

docker build -f spark-task-server.docker -t mozafaq/${APP_IMAGE} .

rm ./tmp/spark-task-server.jar


if [ $# == 1  ]
then
    if [ $1 == "--push" ]
    then
        echo "Pushing images...."
        docker push mozafaq/${APP_IMAGE}
    fi
fi

echo "Done...."