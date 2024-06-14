import requests
import json


def register(service_id):
    requests.put('http://erp-consul:8500/v1/agent/service/register', data=json.dumps({
        "ID": service_id,
        "Name": "purchase-scanner",
        "Address": "purchase-scanner",
        "Port": 80,
        "Check": {
            "HTTP": "http://purchase-scanner:80/healthcheck",
            "Interval": "5s",
            "Timeout": "1s",
            "DeregisterCriticalServiceAfter": "3m"
        },
        "Weights": {
            "Passing": 10,
            "Warning": 3
        }
    }))


def deregister(service_id):
    requests.put('http://erp-consul:8500/v1/agent/service/deregister/' + service_id)
