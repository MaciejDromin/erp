import configs
import requests

def archive_receipts(filename, local_file, orgId):
    headers = {'X-OrgId': orgId}
    with open(local_file, 'rb') as f:
        requests.post('http://{}:{}/?filename={}&directory={}'.format(
            configs.ARTIFACT_HOST, configs.ARTIFACT_PORT, filename, 'uploads/receipts'), data=f, headers=headers)
