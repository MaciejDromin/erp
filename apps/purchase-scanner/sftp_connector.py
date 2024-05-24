import pysftp

def archive_receipts(filename):
    cnopts = pysftp.CnOpts()
    cnopts.hostkeys = None
    with pysftp.Connection('localhost', username='erp', password='admin', port=2222, cnopts=cnopts) as sftp:
        with sftp.cd('/data'):
            sftp.put(filename)
