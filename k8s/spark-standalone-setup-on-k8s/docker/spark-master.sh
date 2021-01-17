
DIR=$(dirname $0)

echo "Dir name $DIR"

set -e

APP_IMAGE="spark-master-k8s:3.1.1"

echo "Building spark server jar ...."
cd ../../../spark-server/ && ./gradlew clean build && cd -


echo "Building spark app jar ...."

cd ../../../spark-app/ && ./gradlew clean build && cd -

mkdir -p tmp
cp ../../../spark-server/build/libs/spark-server-1.0.jar ./tmp/spark-server.jar
cp ../../../spark-app/build/libs/spark-app-1.0.jar ./tmp/spark-app.jar

docker build -f spark-master.docker -t mozafaq/${APP_IMAGE} .

rm ./tmp/spark-server.jar
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
