DIR=$(dirname $0)

echo "Dir name $DIR"

set -e

APP_IMAGE="sample-server-spark-k8:1.5"

docker build -f app-sample-spark-k8s.docker -t mozafaq/${APP_IMAGE} ..



if [ $# == 1  ]
then
    if [ $1 == "--push" ]
    then
        echo "Pushing images...."
        docker push mozafaq/${APP_IMAGE}
    fi
fi
  
echo "Done...."

