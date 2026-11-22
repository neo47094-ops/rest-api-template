# Calculator Service

Template d’API REST **Spring Boot**, conçue selon une approche spec-first avec OpenAPI 3.

## Fonctionnalités

- opérations d’addition et de multiplication ;
- authentification par clé API ;
- persistance PostgreSQL avec Spring JDBC ;
- migrations automatiques avec Flyway ;
- réponses JSON homogènes ;
- spécification OpenAPI et interface Redoc optionnelle ;
- arrêt gracieux et configuration par variables d’environnement.

## Prérequis

- Java 21+
- Maven 3.6.3+
- PostgreSQL 16+
- Node.js uniquement pour régénérer le bundle OpenAPI

Le projet utilise Spring Boot 4.0.9.

## Structure

```text
src/main/java/com/example/calculator/
  config/       configuration Spring et filtre HTTP
  controller/   endpoints REST et gestion des erreurs
  model/        objets du domaine
  repository/   accès PostgreSQL avec JdbcClient
  security/     validation de la clé API
  service/      logique métier
src/main/resources/
  application.yml
  db/migration/ migrations Flyway
resources/openapi/       source OpenAPI YAML
resources/gen/openapi/   bundle JSON servi à l’exécution
```

## Démarrage rapide

Créez la configuration locale :

```bash
make env
```

Chargez les variables puis démarrez l’application :

```bash
set -a
source .env
set +a
make run
```

Le serveur écoute par défaut sur `http://localhost:8080`. Flyway applique les migrations avant que l’application accepte des requêtes.

## Endpoints

| Méthode | Chemin | Authentification |
|---|---|---|
| `GET` | `/healthcheck` | aucune |
| `POST` | `/calculator/v1/add` | en-tête `api-key` |
| `POST` | `/calculator/v1/multiply` | en-tête `api-key` |

La clé de développement par défaut est `a-dummy-but-valid-api-key`.

```bash
curl http://localhost:8080/healthcheck

curl -X POST http://localhost:8080/calculator/v1/add \
  -H 'Content-Type: application/json' \
  -H 'api-key: a-dummy-but-valid-api-key' \
  -d '{"value1":25.5,"value2":10}'
```

Réponse :

```json
{
  "data": {
    "transaction-uuid": "550e8400-e29b-41d4-a716-446655440000",
    "value1": 25.5,
    "value2": 10,
    "operation": "ADD",
    "result": 35.5
  }
}
```

Les deux opérandes doivent être comprises entre `-1000` et `1000`.

## OpenAPI

La source de vérité est `resources/openapi/openapi.yml`. Après une modification :

```bash
make openapi
```

Avec `OPENAPI_UI_ENABLE=true`, l’application expose :

- `GET /openapi.json`
- `GET /redoc`, ou le chemin défini par `OPENAPI_UI_PATH`

## Tests et packaging

```bash
make test
make package
java -jar target/calculator-service-0.0.1-SNAPSHOT.jar
```
