# Create cluster
kind create cluster --name kind-1 --config k8s/kind/cluster-config.yaml

# Verify cluster
docker ps
kubectl get nodes

# Create the Deployment in the cluster
kubectl apply -f k8s/kind/deployment-config.yaml

# Create the Service
kubectl apply -f k8s/kind/service-config.yaml

# Create the ingress-controller
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

# verify it works
kubectl -n ingress-nginx get deploy

# Create the Ingress
kubectl apply -f k8s/kind/ingress-config.yaml

# Verify the Ingress
# kubectl get ingress

# # verify that the Pods/containers are running
# kubectl get pods -w
# kubectl get deployment/backend

# # verify that it works as expected
# kubectl get service