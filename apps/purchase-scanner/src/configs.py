import os

ARTIFACT_HOST = os.getenv("ARTIFACT_HOST", "localhost")
ARTIFACT_PORT = os.getenv("ARTIFACT_PORT", 8089)
RABBITMQ_HOST = os.getenv("RABBITMQ_HOST", "localhost")
RABBITMQ_USERNAME = os.getenv("RABBITMQ_USERNAME", "guest")
RABBITMQ_PASSWORD = os.getenv("RABBITMQ_PASSWORD", "guest")
