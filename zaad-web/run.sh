git pull
#mvn -Dmaven.test.skip=true -Des.cluster.name=local -Des.host=localhost -Des.port=9300 -Dserver.port=8081 -Dtomcat.protocol=http clean package spring-boot:run
nohup mvn -Dmaven.test.skip=true -Des.cluster.name=es_malsami -Des.host=localhost -Des.port=9300 -Dserver.port=8009 -Dtomcat.protocol=ajp clean package spring-boot:run &
