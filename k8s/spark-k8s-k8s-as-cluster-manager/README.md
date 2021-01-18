# Spark on k8s as k8s being cluster manager


### Preparing the image

```
  # Checkout spark repo
  git clone <repo>
  
  # Switch to branch
  git checkout <branch>
  
  ./bin/docker-image-tool.sh -r mozafaq/spark-k8s-as-cm -t v3.1.0_20210117 build

  # Create tag that is user friendly

  docker tag mozafaq/spark-k8s-as-cm/spark:v3.1.0_20210117 mozafaq/spark-k8s-as-cm:3.1.0_20210117

  # Push
  docker push mozafaq/spark-k8s-as-cm:3.1.0_20210117
```


### Creating role

```
kubectl create serviceaccount spark

kubectl create clusterrolebinding spark-role --clusterrole=edit --serviceaccount=default:spark --namespace=default
```


### Run job

```

./bin/spark-submit \
    --master k8s://<Cluster IP e.g. https://172.31.80.10:6443> \
    --deploy-mode cluster \
    --name spark-pi-name \
    --class org.apache.spark.examples.SparkPi \
    --conf spark.executor.instances=2 \
    --conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
    --conf spark.kubernetes.container.image=mozafaq/spark-k8s-as-cm:3.1.0_20210117 \
    local:///opt/spark/examples/jars/spark-examples_2.12-3.1.1-SNAPSHOT.jar 10000

```

### For aws secret dependencies

```

# Creating the key

kubectl create secret generic aws-secret \
 --from-literal=key='<ACCESS_KEY>' \
 --from-literal=secret='<SECRET_KEY>'

## Run the command

./bin/spark-submit \
    --master k8s://https://172.31.80.10:6443 \
    --deploy-mode cluster \
    --name spark-word-count-2mb-4 \
    --class com.mozafaq.test.sparkapp.JobRunner \
    --conf spark.executor.instances=2 \
    --conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
    --conf spark.kubernetes.container.image.pullPolicy=IfNotPresent \
    --conf spark.kubernetes.container.image=mozafaq/spark-k8s-as-cm:3.1.2 \
    --conf spark.kubernetes.driver.secretKeyRef.AWS_ACCESS_KEY=aws-secret:key \
    --conf spark.kubernetes.driver.secretKeyRef.AWS_SECRET_KEY=aws-secret:secret \
    --conf spark.kubernetes.executor.secretKeyRef.AWS_ACCESS_KEY=aws-secret:key \
    --conf spark.kubernetes.executor.secretKeyRef.AWS_SECRET_KEY=aws-secret:secret \
    --conf spark.driver.extraJavaOptions=-Dspark.job.id=word-count-xyz \
    --conf spark.extraListeners=com.mozafaq.test.sparkapp.infra.JobTracker \
    local:///app/spark-app.jar s3a://bd-expl-data/tmp/two-mb-sample.txt word-count-1 25
```

### Running service invocation

```
curl -H "Content-Type: application/json" -X POST "http://spark-master-server:9013/launch-spark-app" -d '{
  "jobId": "<jobname>",
  "masterEndpoint": "k8s://https://172.31.80.10:6443",
  "mainClass": "com.mozafaq.test.sparkapp.JobRunner",
  "deployMode": "cluster",
  "confs": {
    "spark.executor.instances" : "1",
    "spark.extraListeners": "com.mozafaq.test.sparkapp.infra.JobTracker",
    "spark.kubernetes.authenticate.driver.serviceAccountName": "spark",
    "spark.kubernetes.container.image.pullPolicy": "IfNotPresent",
    "spark.kubernetes.container.image": "mozafaq/spark-k8s-as-cm:3.1.2",
    "spark.kubernetes.driver.secretKeyRef.AWS_ACCESS_KEY": "aws-secret:key",
    "spark.kubernetes.driver.secretKeyRef.AWS_SECRET_KEY": "aws-secret:secret",
    "spark.kubernetes.executor.secretKeyRef.AWS_ACCESS_KEY": "aws-secret:key",
    "spark.kubernetes.executor.secretKeyRef.AWS_SECRET_KEY": "aws-secret:secret",
    "spark.driver.extraJavaOptions": "-Dspark.job.id=<jobname>",
    "spark.kubernetes.driver.limit.cores": "1",
    "spark.kubernetes.executor.limit.cores": "1",
    "spark.driver.memory": "1g",
    "spark.executor.memory": "1g"
  },
  "arguments": ["s3a://<BUCKET-NAME>/apth/<bject-name>", "<jobname>",  "25"]
}'

```