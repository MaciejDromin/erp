NPROCS = $(shell grep -c 'processor' /proc/cpuinfo)
MAKEFLAGS += -j$(shell echo $$(( $(NPROCS) / 2 )))

self-register-quarkus:
	cd libs/self-register-quarkus; mvn install -DskipTests

self-register-spring:
	cd libs/self-register-spring; ./gradlew build; ./gradlew publishToMavenLocal

widget-commons:
	cd libs/widget-commons; ./gradlew build; ./gradlew publishToMavenLocal

widget-startup: widget-commons
	cd libs/widget-startup; mvn install -DskipTests

reports-client:
	cd libs/reports-client; ./gradlew build; ./gradlew publishToMavenLocal

soitio-commons:
	cd libs/commons; ./gradlew build; ./gradlew publishToMavenLocal

analytics: self-register-spring
	cd apps/analytics; ./gradlew build -x test; \
		podman build -f src/main/docker/Dockerfile -t erp/analytics:latest .

finances: self-register-spring soitio-commons
	cd apps/finances;  ./gradlew build -x test -x checkstyleAot -x checkstyleAotTest; \
		podman build -f src/main/docker/Dockerfile -t erp/finances:latest .

erp-fe:
	cd apps/erp-fe; podman build -f Dockerfile -t erp/fe:latest .

inventory: self-register-quarkus soitio-commons
	cd apps/inventory; ./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/inventory:latest .

purchase-scanner:
	cd apps/purchase-scanner; podman build -f docker/Dockerfile -t erp/purchase-scanner:latest .

dashboard: self-register-quarkus widget-commons
	cd apps/dashboard; ./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/dashboard:latest .

widgets-finances: self-register-quarkus widget-startup soitio-commons
	cd apps/widgets-finances; \
		./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/widgets-finances:latest .

reports-generator: self-register-quarkus reports-client
	cd apps/reports; ./gradlew build \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.jvm -t erp/reports-generator:latest .

reports-service: self-register-quarkus reports-client soitio-commons
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
	cd libs/self-register-quarkus; mvn clean
	cd libs/self-register-spring; ./gradlew clean
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
