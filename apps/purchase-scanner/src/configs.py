import os

SFTP_HOST = os.getenv("SFTP_HOST", "localhost")
SFTP_USERNAME = os.getenv("SFTP_USERNAME", "erp")
SFTP_PASSWORD = os.getenv("SFTP_PASSWORD", "admin")
SFTP_PORT = os.getenv("SFTP_PORT", 2222)
RABBITMQ_HOST = os.getenv("RABBITMQ_HOST", "localhost")
RABBITMQ_USERNAME = os.getenv("RABBITMQ_USERNAME", "guest")
RABBITMQ_PASSWORD = os.getenv("RABBITMQ_PASSWORD", "guest")
