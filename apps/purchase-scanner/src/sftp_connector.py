from fabric import Connection
import configs

def archive_receipts(filename, orgId):
    Connection(host=configs.SFTP_HOST, user=configs.SFTP_USERNAME, port=configs.SFTP_PORT,
               connect_kwargs={
                   "password": configs.SFTP_PASSWORD,
                   "look_for_keys": False,
                   "allow_agent": False
                }).put(filename, remote="/data/{}/receipts".format(orgId))
