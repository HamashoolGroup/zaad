git pull
mvn -Dmaven.test.skip=true -Des.cluaster.name=local -Des.host=localhost -Des.port=9300 -Dserver.port=8081 clean package spring-boot:run