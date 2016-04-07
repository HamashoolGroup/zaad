git pull
mvn -Dmaven.test.skip=true -Des.cluaster.name=local -Des.host=localhost -Des.port=9300 -Dserver.port=8081 -Dtomcat.protocol=http clean package spring-boot:run
#mvn -Dmaven.test.skip=true -Des.cluaster.name=local -Des.host=localhost -Des.port=9300 -Dserver.port=8009 -Dtomcat.protocol=ajp clean package spring-boot:run