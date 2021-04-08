FROM openliberty/open-liberty:21.0.0.3-full-java11-openj9-ubi

COPY --chown=1001:0  target/liberty/wlp/usr/servers/defaultServer/server.xml /config/
COPY --chown=1001:0  target/liberty/wlp/usr/servers/defaultServer/apps/liberty-war-example.war /config/apps/