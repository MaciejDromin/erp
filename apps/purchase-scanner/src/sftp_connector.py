import pysftp
import configs

def archive_receipts(filename):
    cnopts = pysftp.CnOpts()
    cnopts.hostkeys = None
    with pysftp.Connection(configs.SFTP_HOST, username=configs.SFTP_USERNAME,
                           password=configs.SFTP_PASSWORD, port=2222, cnopts=cnopts) as sftp:
        with sftp.cd('/data'):
            sftp.put(filename)
