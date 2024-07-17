NPROCS = $(shell grep -c 'processor' /proc/cpuinfo)
MAKEFLAGS += -j$(NPROCS)

self-register-quarkus:
	cd libs/self-register-quarkus; ./gradlew build; ./gradlew publishToMavenLocal

self-register-spring:
	cd libs/self-register-spring; ./gradlew build; ./gradlew publishToMavenLocal

widget-commons:
	cd libs/widget-commons; ./gradlew build; ./gradlew publishToMavenLocal

widget-startup: widget-commons
	cd libs/widget-startup; mvn install -DskipTests

analytics: self-register-spring
	cd apps/analytics; ./gradlew build -x test; \
		podman build -f src/main/docker/Dockerfile -t erp/analytics:latest .

finances: self-register-spring
	cd apps/finances; ./gradlew build -x test; \
		podman build -f src/main/docker/Dockerfile -t erp/finances:latest .

erp-fe:
	cd apps/erp-fe; podman build -f Dockerfile -t erp/fe:latest .

inventory: self-register-quarkus
	cd apps/inventory; ./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/inventory:latest .

planner: self-register-quarkus
	cd apps/planner; ./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/planner:latest .

purchase-scanner:
	cd apps/purchase-scanner; podman build -f docker/Dockerfile -t erp/purchase-scanner:latest .

dashboard: self-register-quarkus widget-commons
	cd apps/dashboard; ./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/dashboard:latest .

widgets-finances: self-register-quarkus widget-startup
	cd apps/widgets-finances; \
		./gradlew build \
		-Dquarkus.native.enabled=true \
		-Dquarkus.native.container-build=true \
		-Dquarkus.package.jar.enabled=false \
		-Dquarkus.profile=docker; \
		podman build -f src/main/docker/Dockerfile.native -t erp/widgets-finances:latest .

all: analytics finances erp-fe inventory planner purchase-scanner dashboard widgets-finances

clean:
	cd libs/self-register-quarkus; ./gradlew clean
	cd libs/self-register-spring; ./gradlew clean
	cd libs/widget-commons; ./gradlew clean
	cd libs/widget-startup; mvn clean
	cd apps/analytics; ./gradlew clean
	cd apps/finances; ./gradlew clean
	cd apps/inventory; ./gradlew clean
	cd apps/planner; ./gradlew clean
	cd apps/dashboard; ./gradlew clean
	cd apps/widgets-finances; ./gradlew clean
