# INTRODUCTION
Containerization is a lightweight form of virtualization that encapsulates an application along with its dependencies and configuration into a single package. Containers provide isolation, consistency, and portability across different environments.

Benefits of Docker:
- Consistency: eliminate "it works on my machine" problems by ensuring consistent environments from development to production. 
- Efficiency: containers share the host OS kernel, making them more lightweight than traditional VMs. 
- Isolation: containers offer process-level isolation, enhancing security and resource management. 
- Portability: develop locally, deploy anywhere with confidence. 
- Scalability: scale containers up and down to meet demand effortlessly.

# DOCKER CONCEPTS
## Docker Architecture
Docker uses a client-server architecture. The Docker client talks to the Docker daemon, which does the heavy lifting of building, running, and distributing your Docker containers. The Docker client and daemon can run on the same system, or you can connect a Docker client to a remote Docker daemon. The Docker client and daemon communicate using a REST API, over UNIX sockets or a network interface. Another Docker client is Docker Compose, that lets you work with applications consisting of a set of containers.

## Docker Daemon
The Docker daemon (dockerd) listens for Docker API requests and manages Docker objects such as images, containers, networks, and volumes. A daemon can also communicate with other daemons to manage Docker services.

## Docker Client
The Docker client (docker) is the primary way that many Docker users interact with Docker. When you use commands such as docker run, the client sends these commands to dockerd, which carries them out. The docker command uses the Docker API. The Docker client can communicate with more than one daemon.

## Docker Registries
A Docker registry stores Docker images. Docker Hub is a public registry that anyone can use, and Docker is looks for images on Docker Hub by default. You can even run your own private registry.

When you use the docker pull or docker run commands, Docker pulls the required images from your configured registry. When you use the docker push command, Docker pushes your image to your configured registry.

# DOCKER OBJECTS
## Images
An image is a read-only template with instructions for creating a Docker container. Often, an image is_based on another image, with some additional customization. For example, you may build an image which is based on the ubuntu image, but installs the Apache web server and your application, as well as the configuration details needed to make your application run.

You might create your own images or you might only use those created by others and published in a registry. To build your own image, you create a Dockerfile with a simple syntax for defining the steps needed to create the image and run it. Each instruction in a Dockerfile creates a layer in the image. When you change the Dockerfile and rebuild the image, only those layers which have changed are rebuilt. This is part of what makes images so lightweight, small, and fast, when compared to other virtualization technologies.

Docker images are composed of layers. When a Docker image is built, each instruction in the Dockerfile creates a new layer. Docker caches layers to speed up image building.

### Commands within a Dockerfile file
- FROM: Specifies the base image to start building upon.
```shell
FROM ubuntu:latest
```
- LABEL: Adds metadata to the image.
```shell
LABEL maintainer="yourname@example.com"
LABEL version="1.0"
```
- RUN: Executes commands during image build.
```shell
RUN apt-get update && apt-get install -y package-name
```
- WORKDIR: Sets the working directory inside the container.
```shell
WORKDIR /app
```
- COPY: Copies files or directories from the build context to the image. The root of this command is there the Dockerfile is placed, so ir you want to copy a file in the folder ./myFolder, the instruction would be the following:
```shell
COPY ./myFolder/file.txt ./myFolder
```
- ADD: Similar to COPY, but can also fetch remote resources and extract compressed files.
```shell
ADD src destination
```
- ENV: Sets environment variables in the container.
```shell
ENV VAR_NAME=value
```
- EXPOSE: Informs Docker that the container will listen on a specific port.
```shell
EXPOSE 80
```
- CMD: Defines the default command to be executed when the container starts.
```shell
CMD ["executable", "arg1", "arg2"]
```
- ENTRYPOINT: Similar to CMD, but the command is not overridden by command-line
```shell
ENTRYPOINT ["executable", "arg1"]
```
- VOLUME: Creates a mount point for a volume in the container.
```shell
VOLUME /data
```
- USER: Specifies the user to run commands within the container.
```shell
USER myuser
```
- ARG: Defines a build-time argument to pass to the build environment.
```shell
ARG build_var=default_value
```
- HEALTHCHECK: Specifies a command to check the health of the running container.
```shell
HEALTHCHECK CMD curl --fail http://localhost:80 || exit 1
```

### Image example
```shell
FROM ubuntu:latest

LABEL maintainer="my_email@gmail.com"
LABEL version="1.0"

WORKDIR /app

#copy pom
COPY pom.xml .
#copy source
COPY src ./src

#compile the project
RUN mvn clean compile

# build the app and download dependencies only when these are new (thanks to the cache)
RUN --mount=type=cache,target=/root/.m2  mvn clean package -Dmaven.test.skip

COPY /target/*.jar server.jar

EXPOSE 8100

ENTRYPOINT ["java", "-Dspring.profiles.active=pro", "-jar", "server.jar"]
```

## Containers
A container is a runnable instance of an image. You can create, start, stop, move, or delete a container using the Docker API or CLI. You can connect a container to one or more networks, attach storage to it, or even create a new image based on its current state.

By default, a container is relatively well isolated from other containers and its host machine. You can control how isolated a container's network, storage, or other underlying subsystems are from other containers or from the host machine.

A container is defined by its image as well as any configuration options you provide to it when you create or start it. When a container is removed, any changes to its state that aren't stored in persistent storage disappear.

### Container Lifecycle
Containers have a lifecycle:
1. Create.
2. Start.
3. Stop.
4. Delete.

You can use various Docker commands to manage containers throughout their lifecycle.

# DOCKER COMMANDS
Some of the most common commands Docker offers are:
- docker images: list available images. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/images/).
```shell
docker images
```
- docker rmi: remove an image. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/rmi/).
```shell
docker rmi image
```
- docker pull: pull an image from a repository. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/pull/).
```shell
docker pull [options] name[:tag|@digest]
```
- docker push: push an image to a repository. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/push/).
```shell
docker push [options] name[:tag]
```
- docker run: run a command in a new container, pulling the image if needed and starting the container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/run/).
```shell
docker run [options] image [command] [arg...]
```
- docker start: start one or more stopped containers. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/start/).
```shell
docker start [options] container_id/container_name
```
- docker stop: stop one or more running containers. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/stop/).
```shell
docker stop [options] container_id/container_name
```
- docker restart: restart a running container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/restart/).
```shell
docker restart [options] container_id/container_name
```
- docker pause: pause processes within a running container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/pause/).
```shell
docker pause container_id/container_name
```
- docker unpause: unpause processes within a paused container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/unpause/).
```shell
docker unpause container_id/container_name
```
- docker exec: run a command in a running container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/exec/).
```shell
docker exec [options] container_id/container_name command [arg...]
```
- docker attach: attach to a running container's console. It allows you to enter into the container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/attach/).
```shell
docker attach [options] container_id/container_name
```
- docker logs: fetch the logs of a container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/logs/).
```shell
docker logs [options] container_id/container_name
```
- docker ps: list running containers. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/ps/).
```shell
docker ps [options]
```
- docker ps -a: list all containers, including stopped ones. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/ps/).
```shell
docker ps -a [options]
```
- docker inspect: display detailed information about a container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/inspect/).
```shell
docker inspect [options] container_id/container_name
```
- docker top: display the running processes of a container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/top/).
```shell
docker top container_id/container_name [ps options]
```
- docker rm: remove one or more containers. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/rm/).
```shell
docker rm [options] container_id/container_name [container_id/container_name...]
```
- docker rename: rename a container. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/rename/).
```shell
docker rename old_name new_name
```
- docker cp: copy files/folders between a container and the local filesystem. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/cp/).
```shell
docker cp [options] container_id/container_name:src_path dest_path|-
docker cp [options] src_path|- container_id/container_name:dest_path
```
- docker commit: create a new image from a container's changes. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/commit/).
```shell
docker commit [options] container_id/container_name [repository[:tag]]
```
- docker kill: kill a running container (send SIGKILL). More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/kill/).
```shell
docker kill [options] container_id/container_name [container_id/container_name...]
```
- docker wait: block until a container stops, then print its exit code. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/wait/).
```shell
docker wait container_id/container_name [container_id/container_name...]
```
- docker stats: display live resource usage statistics of containers. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/stats/).
```shell
docker stats [options] [container_id/container_name...]
```
- docker events: get real-time events from the server. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/events/).
```shell
docker events [options]
```
- docker network ls: list available networks. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/network/).
```shell
docker network ls
```
- docker network inspect bridge: show details of the brigde networking (e.g. containers attached to the network with their IP addresses).
```shell
docker network inspect bridge
```
- docker network rm: removes a network.
```shell
docker network rm network [network...]
```
- docker volume ls: list volumes. More details about this command can be found [here](https://docs.docker.com/engine/reference/commandline/volume/).
```shell
docker volume ls
```
- docker volume create: create a new volume.
```shell
docker volume create --name volume_name
```

# DOCKER NETWORKING
Networking is about communication among processes, and Docker’s networking is no different. Docker networking is primarily used to establish communication between Docker containers and the outside world via the host machine where the Docker daemon is running. Docker supports different types of networks, each fit for certain use cases.

Docker networking differs from virtual machine (VM) or physical machine networking in a few ways:
- Virtual machines are more flexible in some ways as they can support configurations like NAT and host networking. Docker typically uses a bridge network, and while it can support host networking, that option is only available on Linux.
- When using Docker containers, network isolation is achieved using a network namespace, not an entirely separate networking stack.
- You can run hundreds of containers on a single-node Docker host, so it’s required that the host can support networking at this scale. VMs usually don’t run into these network limits as they typically run fewer processes per VM.

Some of the major benefits of using Docker Networking are:
- They share a single operating system and maintain containers in an isolated environment.
- It requires fewer OS instances to run the workload.
- It helps in the fast delivery of software.
- It helps in application portability.

## Docker Network Drivers
Docker handles communication between containers by creating a default bridge network, so you often don’t have to deal with networking and can instead focus on creating and running containers. This default bridge network works in most cases, but it’s not the only option you have.

Docker allows you to create three different types of network drivers out-of-the-box: bridge, host, and none. However, they may not fit every use case.

### The Bridge Driver
This is the default. Whenever you start Docker, a bridge network gets created and all newly started containers will connect automatically to the default bridge network.

You can use this whenever you want your containers running in isolation to connect and communicate with each other. Since containers run in isolation, the bridge network solves the port conflict problem. Containers running in the same bridge network can communicate with each other, and Docker uses iptables on the host machine to prevent access outside of the bridge.

The downside with the bridge driver is that it’s not recommended for production; the containers communicate via IP address instead of automatic service discovery to resolve an IP address to the container name. Every time you run a container, a different IP address gets assigned to it. It may work well for local development or CI/CD, but it’s definitely not a sustainable approach for applications running in production.

Another reason not to use it in production is that it will allow unrelated containers to communicate with each other, which could be a security risk.

See this example:
```shell
docker run -dit --name busybox1 busybox /bin/sh
docker run -dit --name busybox2 busybox /bin/sh
docker attach busybox1
hostname -i
ping 172.17.0.3 (assuming 172.17.0.3 is the IP of busybox2)
ping busybox2
```

In the example above, the first ping will work but not the second one, as in the bridge driver, containers talk to each other via IP.

### The Host Driver
As the name suggests, host drivers use the networking provided by the host machine. And it removes network isolation between the container and the host machine where Docker is running. For example, If you run a container that binds to port 80 and uses host networking, the container’s application is available on port 80 on the host’s IP address. You can use the host network if you don’t want to rely on Docker’s networking but instead rely on the host machine networking.

One limitation with the host driver is that it doesn't work on Docker desktop: you need a Linux host to use it. The following command will start an Nginx image and listen to port 80 on the host machine:
```shell
docker run --rm -d --network host --name my_nginx nginx
```

The downside with the host network is that you can’t run multiple containers on the same host having the same port. Ports are shared by all containers on the host machine network.

#### Step by step to connect a container to a network
The following command will create a new network of bridge type:
```shell
docker network create -d bridge mynetwork
```
The following command will run a ubuntu container in interactive (-i) mode.
```shell
docker run ubuntu bash
```
The following command will connect the container to the network mynetwork:
```shell
docker network connect mynetwork container_id
```
The following command will show all the details of the network, where the previous container should shoud up:
```shell
docker network inspect mynetwork
```
The following command will disconnect the container from the network mynetwork:
```shell
docker network disconnect mynetwork container_id
```
The following command will remove the new network created:
```shell
docker network rm mynetwork
```

### The None Driver
The none network driver does not attach containers to any network. Containers do not access the external network or communicate with other containers. You can use it when you want to disable the networking on a container.

## Published ports
By default, when you create or run a container using docker create or docker run, the container doesn't expose any of its ports to the outside world. Use the --publish or -p flag to make a port available to services outside of Docker. This creates a firewall rule in the host, mapping a container port to a port on the Docker host to the outside world. Here are some examples:
- `p 8080:80`: map TCP port 80 in the container to port 8080 on the Docker host. Traffic coming to the system 8080 port is routed to the container por 80.
- `p 192.168.1.100:8080:80`: map TCP port 80 in the container to port 8080 on the Docker host for connections to host IP 192.168.1.100.
- `p 8080:80/udp`: map UDP port 80 in the container to port 8080 on the Docker host.
- `p 8080:80/tcp -p 8080:80/udp`: map TCP port 80 in the container to TCP port 8080 on the Docker host, and map UDP port 80 in the container to UDP port 8080 on the Docker host.

Publishing container ports is insecure by default. Meaning, when you publish a container's ports it becomes available not only to the Docker host, but to the outside world as well. If you include the localhost IP address (127.0.0.1) with the publish flag, only the Docker host can access the published container port.
```shell
docker run -p 127.0.0.1:8080:80 nginx
```

## IP Address and hostname
By default, the container gets an IP address for every Docker network it attaches to. A container receives an IP address out of the IP subnet of the network. The Docker daemon performs dynamic subnetting and IP address allocation for containers. Each network also has a default subnet mask and gateway.

When you connect an existing container to a different network using docker network connect, you can use the --ip or --ip6 flags on that command to specify the container's IP address on the additional network.

When a container starts, it can only attach to a single network, using the --network flag. You can connect a running container to multiple networks using the docker network connect command. When you start a container using the --network flag, you can specify the IP address for the container on that network using the --ip or --ip6 flags.

In the same way, a container's hostname defaults to be the container's ID in Docker. You can override the hostname using --hostname. When connecting to an existing network using docker network connect, you can use the --alias flag to specify an additional network alias for the container on that network.

# DOCKER VOLUMES
The purpose of using Docker volumes is to persist data outside the container so it can be backed up or shared.

Docker volumes are dependent on Docker’s file system and are the preferred method of persisting data for Docker containers and services. When a container is started, Docker loads the read-only image layer, adds a read-write layer on top of the image stack, and mounts volumes onto the container filesystem.

## Why docker volumes?
If you are using Docker for development, you must be familiar with the -v or --volume flag that lets you mount your local files into the container. For instance, you can mount your local ./target onto the /usr/share/nginx/html directory container or an nginx container to visualize your html files.
```shell
echo "<h1>Hello from Host</h1>" > ./target/index.html
docker run -it --rm --name nginx -p 8080:80 -v "$(pwd)"/target:/usr/share/nginx/html nginx
```

Navigate to http://localhost:8080/ and you should see “Hello from Host”. This is called a bind mount and is commonly used by developers. But, if you are using Docker Desktop on Windows or MacOS bind, mounts have significant performance issues. As a result, using volumes may be the best alternative for holding state between container runs.

Unlike bind mount, where you can mount any directory from your host, volumes are stored in a single location (most likely /var/lib/docker/volumes/ on unix systems) and greatly facilitates managing data (backup, restore, and migration). Docker volumes can safely be shared between several running containers.

## Creating and managing docker volumes
### Create a Docker Volume Implicitly
The easiest way to create and use a volume is with docker run and the -v or --volume flag. For instance:
```shell
docker run -it --rm --name nginx -p 8080:80 -v demo-earthly:/usr/share/nginx/html nginx
```

### Create a Docker Volume Explicitly
You can use the docker volume create command to explicitly create a data volume.
```shell
docker volume create --name demo-earthly
```

### Declare a Docker Volume from Dockerfile
Volumes can be declared in your Dockerfile using the VOLUME statement. This statement declares that a specific path of the container must be mounted to a Docker volume. When you run the container, Docker will create an anonymous volume (volume with a unique id as the name) and mount it to the specified path.
```shell
FROM nginx:latest

RUN echo "<h1>Hello from Volume</h1>" > /usr/share/nginx/html/index.html
VOLUME /usr/share/nginx/html
```

# DOCKER AND CLOUD SERVICES
- Docker on Cloud Providers.
  - Cloud providers like AWS, Azure, and GCP offer managed Docker services for easy deployment and management of containers.
- Managed Kubernetes Services.
  - Leverage managed Kubernetes services like GKE, AKS, and EKS to simplify Kubernetes cluster setup and management.
- Serverless Computing with Docker.
  - Serverless platforms like AWS Lambda and Azure Functions can run Docker containers as serverless functions.

# DOCKER COMPOSE
Docker Compose is a tool for running multi-container applications on Docker, which are defined using the compose YAML file. You can start your applications with a single command: docker-compose up.

One of the base functions of Docker Compose is to build images from Dockerfiles. However, Docker Compose is capable of orchestrating the containerization and deployment of multiple software packages. You can select which images are used for certain services, set environment-specific variables, configure network connections, and much more.

Below are the three necessary steps that begin most Docker workflows:
- Building a Dockerfile for each image you wish to add.
- Use Docker Compose to assemble the images with the build command.
- Specify the path to individual Dockerfiles using the build command in conjunction with /path/to/dockerfiles.

## Compose file
The docker compose file consists of the following sections:
- version ‘3’: This denotes that we are using version 3 of Docker Compose, and Docker will provide the appropriate features. At the time of writing this article, version 3.7 is latest version of Compose. 
- services: This section defines all the different containers we will create. In our example, we have two services, web and database. 
- web: This is the name of our Flask app service. Docker Compose will create containers with the name we provide. 
- build: This specifies the location of our Dockerfile, and . represents the directory where the docker-compose.yml file is located. 
- ports: This is used to map the container’s ports to the host machine. 
- volumes: This is just like the -v option for mounting disks in Docker. In this example, we attach our code files directory to the containers’ ./code directory. This way, we won’t have to rebuild the images if changes are made. 
- links: This will link one service to another. For the bridge network, we must specify which container should be accessible to which container using links. 
- image: If we don’t have a Dockerfile and want to run a service using a pre-built image, we specify the image location using the image clause. Compose will fork a container from that image. 
- environment: The clause allows us to set up an environment variable in the container. This is the same as the -e argument in Docker when running a container.

## Commands
- docker-compose build: the build command builds or rebuild images in the docker-compose.yml file.
```shell
docker-compose build
```
- docker-compose images: this command will list the images you’ve built using the current docker-compose file.
```shell
docker-compose images
```
- docker-compose stop: this command stops the running containers of specified services.
```shell
docker-compose stop
```
- docker-compose run: this is similar to the docker run command. It will create containers from images built for the services mentioned in the compose file.
```shell
docker-compose run
```
- docker-compose up: This command does the work of the docker-compose build and docker-compose run commands. It builds the images if they are not located locally and starts the containers. If images are already built, it will fork the container directly.
```shell
docker-compose up
```
- docker-compose ps: this command list all the containers in the current docker-compose file. They can then either be running or stopped.
```shell
docker-compose ps
```
- docker-compose down: this command is similar to the docker system prune command. However, in Compose, it stops all the services and cleans up the containers, networks, and images.
```shell
docker-compose down
```

## Networking
By default, Docker Compose creates a single network for each container defined in the compose file. All the containers defined in the compose file connect and communicate through the default network. In the docker compose file, we have crated a network called mynetwork of bridge type (meaning it’s a network on the host machine separated from the rest of the host network stack), and we have connected both services to the same network.

## Volumes
Although there are many ways to create a volume, it’s more convenient to use the docker-compose command to easily share data between multiple containers.

The use of the volume property in compose files is very similar to -v and --volume. That being said, to perform a bind mount (mount a directory from your local machine), you can use a relative path unlike -v with the command docker run that requires an absolute path.
```shell
version: "3.7"
services:
  web:
    image: nginx:latest
    ports:
      - 8080:80
    volumes:
      - ./target:/usr/share/nginx/html
```

The containers and hosts in the above configuration use volumes in the services definition (web) to mount ./target from the host to /usr/share/nginx/html of the container.

With docker-compose, volumes must be declared at the same level as services. Then you can refer to them by their name.
```shell
version: "3.7"
services:
  web:
    image: nginx:latest
    ports:
      - 8080:80
    volumes:
      - html_files:/usr/share/nginx/html
  web1:
    image: nginx:latest
    ports:
      - 8081:80
    volumes:
      - html_files:/usr/share/nginx/html
 
volumes:
  html_files:
```

## Example of docker compose
```shell
version: '3.7'
services:
  db:
    image: mysql:8.0.19
    command: '--default-authentication-plugin=mysql_native_password'
    restart: always
    volumes:
      - db_data:/var/lib/mysql
    restart: always
    networks:
      - mynetwork
    environment:
      - MYSQL_ROOT_PASSWORD=somewordpress
      - MYSQL_DATABASE=wordpress
      - MYSQL_USER=wordpress
      - MYSQL_PASSWORD=wordpress
  wordpress:
    image: wordpress:latest
    ports:
      - 80:80
    restart: always
    networks:
      - mynetwork
    environment:
      - WORDPRESS_DB_HOST=db
      - WORDPRESS_DB_USER=wordpress
      - WORDPRESS_DB_PASSWORD=wordpress
      - WORDPRESS_DB_NAME=wordpress
volumes:
  db_data:
 networks:
  mynetwork:
```