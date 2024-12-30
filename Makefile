NPROCS = $(shell grep -c 'processor' /proc/cpuinfo)
MAKEFLAGS += -j$(shell echo $$(( $(NPROCS) / 2 )))

widget-commons:
	cd libs/widget-commons; ./gradlew build; ./gradlew publishToMavenLocal

widget-startup: widget-commons
	cd libs/widget-startup; mvn install -DskipTests

reports-client:
	cd libs/reports-client; ./gradlew build; ./gradlew publishToMavenLocal

soitio-commons:
	cd libs/commons; ./gradlew build; ./gradlew publishToMavenLocal

analytics: 
	cd apps/analytics; ./gradlew build -x test; \
		podman build -f src/main/docker/Dockerfile -t erp/analytics:latest .

finances: soitio-commons
	cd apps/finances;  ./gradlew build -x test -x checkstyleAot -x checkstyleAotTest; \
		podman build -f src/main/docker/Dockerfile -t erp/finances:latest .

erp-fe:
	cd apps/erp-fe; podman build -f Dockerfile -t erp/fe:latest .

inventory: soitio-commons
	cd apps/inventory; ./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/inventory:latest .

purchase-scanner:
	cd apps/purchase-scanner; podman build -f docker/Dockerfile -t erp/purchase-scanner:latest .

dashboard: widget-commons
	cd apps/dashboard; ./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/dashboard:latest .

widgets-finances: widget-startup soitio-commons
	cd apps/widgets-finances; \
		./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/widgets-finances:latest .

reports-generator: reports-client
	cd apps/reports; ./gradlew build \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.jvm -t erp/reports-generator:latest .

reports-service: reports-client soitio-commons
	cd apps/reports-service; ./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/reports-service:latest .

all: analytics finances erp-fe inventory purchase-scanner \
	dashboard widgets-finances reports-generator \
	reports-service

clean:
	cd libs/widget-commons; ./gradlew clean
	cd libs/widget-startup; mvn clean
	cd libs/reports-client; ./gradlew clean
	cd libs/commons; ./gradlew clean
	cd apps/analytics; ./gradlew clean
	cd apps/finances; ./gradlew clean
	cd apps/inventory; ./gradlew clean
	cd apps/dashboard; ./gradlew clean
	cd apps/widgets-finances; ./gradlew clean
	cd apps/reports; ./gradlew clean
	cd apps/reports-service; ./gradlew clean
