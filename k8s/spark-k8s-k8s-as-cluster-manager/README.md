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