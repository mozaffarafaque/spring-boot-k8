
DIR=$(dirname $0)

echo "Dir name $DIR"

set -e

APP_IMAGE="spark-k8s-as-cm:3.1.2"


echo "Building spark app jar ...."

cd ../../spark-app/ && ./gradlew clean build && cd -

mkdir -p tmp

cp ../../spark-app/build/libs/spark-app-1.0.jar ./tmp/spark-app.jar

docker build -f spark-k8s-img.docker -t mozafaq/${APP_IMAGE} .

rm ./tmp/spark-app.jar


if [ $# == 1  ]
then
    if [ $1 == "--push" ]
    then
        echo "Pushing images...."
        docker push mozafaq/${APP_IMAGE}
    fi
fi

echo "Done...."