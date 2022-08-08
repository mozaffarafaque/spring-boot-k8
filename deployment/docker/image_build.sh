DIR=$(dirname $0)

echo "Dir name $DIR"

set -e

APP_IMAGE1="fluent-test-app:1.1"

docker build -f ${DIR}/app-gateway.docker -t mozafaq/${APP_IMAGE1} .


if [ $# == 1  ]
then
    if [ $1 == "--push" ]
    then
        echo "Pushing images...."
        docker push mozafaq/${APP_IMAGE1}
    fi
fi

echo "Done.... ${APP_IMAGE1}"
