from fastapi import UploadFile
import tarfile
import aiofiles
import os
import shutil

TMP_DIRECTORY = './tmp/'

async def save_locally(filename, file_content):
    local_name = TMP_DIRECTORY + filename
    async with aiofiles.open(local_name, 'wb') as out_file:
        await out_file.write(file_content)
    return local_name

def unpack_archive(filename):
    tar_file = tarfile.open(filename)
    tar_file.extractall(TMP_DIRECTORY)
    tar_file.close()
    extracted_dir = filename[:-7]
    return [extracted_dir + "/" + f for f in os.listdir(extracted_dir)]

def clean_directories():
    for filename in os.listdir(TMP_DIRECTORY):
        to_remove = os.path.join(TMP_DIRECTORY, filename)
        if os.path.isfile(to_remove):
            os.remove(to_remove)
        else:
            shutil.rmtree(to_remove)
    
