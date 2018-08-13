# outlier

My assumptions

    Due to time restriction and learning curve of Docker for me I have decided
    to implement not as microservices but in different package the consumer 
    and server parts

    1. In case date is not correct in kafka setting date to current
    
    2. In case your repository is bounded to F5 so it
       won’t bring third party you can create in your .m2
       path new settings.xml file and new repository
       folder(just name them differently) and you can run
       the mvn clean the following way(or in inteliij change
       the maven settings)
       mvn -settings $HOME/.m2/{your settings file name} -
       Dmaven.repo.local=$HOME/.m2/{repository folder name} clean
    3. For median I used apache math package
    
    4. SBT - Didn't found corrrect use for it in java and spring boot.
       Using mvn package - run my unit tests during jar creation
       
    5. EndToEndTest - Created controller which can be invoked via-
        curl -L "http://localhost:8086/endtoend"
        It will return if it passed and outlier list
        
        
Installation on new machine
    
    In order to create new jar of the project need to run mvn package- 
        Prerequisites
            MySql is running- 
                   I have used docker image
                    docker pull mysql
                    docker run -d --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=outlier mysql:5
            Kafka-
                Download kafka. Then run form kafka folder
                  bin/zookeeper-server-start.sh config/zookeeper.properties
                  bin/kafka-server-start.sh config/server.properties
                  bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic data
                  
            After jar creation is done you can stop all the running instances  
                   bin/kafka-server-stop.sh config/server.properties
                   bin/zookeeper-server-stop.sh config/zookeeper.properties
                   docker stop mysql
                   Remove container
                    docker ps -aq --no-trunc -f status=exited | xargs docker rm
                    
                    
    Run docker-compose up for full installation on new machine.
   
    
