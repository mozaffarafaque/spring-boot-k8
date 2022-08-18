#!/bin/bash

echo "[`date -u`] Starting Fluentd agent ...... "

 fluentd -c /fluentd/fluent.conf -d /fluentd/fluent.pid -o /fluentd/fluent-app.log &

echo "[`date -u`] [DISABLED] Started Fluentd agent ...... "

echo "[`date -u`] Starting Application ...... "

java -DLOGS=/tmp/ -jar /app.jar 9091

echo "[`date -u`]  Started Application ...... "
