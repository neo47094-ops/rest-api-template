MAVEN = mvn
REDOCLY_VERSION = 2.25.1

.PHONY: env
env: ## Copy .env.example to .env
ifeq ($(OS),Windows_NT)
	copy .env.example .env
else
	cp .env.example .env
endif

.PHONY: run
run: ## Run the Spring Boot application
	$(MAVEN) spring-boot:run

.PHONY: test
test: ## Compile and run all tests
	$(MAVEN) test

.PHONY: package
package: ## Build the executable JAR
	$(MAVEN) clean package

.PHONY: openapi
openapi: ## Bundle the OpenAPI YAML source into the runtime JSON file
	npx --yes @redocly/cli@$(REDOCLY_VERSION) bundle \
	  ./resources/openapi/openapi.yml \
	  --output ./resources/gen/openapi/openapi.json --ext json

.PHONY: help
help: ## Show this help message
	@grep -E '^[a-zA-Z_/-]+:.*?## .*$$' $(MAKEFILE_LIST) \
	  | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-22s\033[0m %s\n", $$1, $$2}'

.DEFAULT_GOAL := help
