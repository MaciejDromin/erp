from file_manager import save_locally, unpack_archive, clean_directories
from receipt_scan import scan_receipts, parse_receipts, filter_receipts
from sftp_connector import archive_receipts
from message_publisher import publish
from fastapi import UploadFile
import json

async def handle_uploaded_receipts(filename, file_content):
    saved_file = await save_locally(filename, file_content)
    archive_receipts(saved_file)
    receipts = unpack_archive(saved_file)
    scanned_receipts = scan_receipts(receipts)
    parsed_receipts = parse_receipts(scanned_receipts)
    filtered_receipts = filter_receipts(parsed_receipts)
    await publish("scanned-receipts", filtered_receipts)

    clean_directories()

