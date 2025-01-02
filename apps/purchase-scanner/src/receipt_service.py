from file_manager import save_locally, unpack_archive, clean_directories
from receipt_scan import filter_receipts, group_by_extension
from sftp_connector import archive_receipts
from message_publisher import publish
from fastapi import UploadFile
import strategy_provider
import json
import asyncio

provider = strategy_provider.StrategyProvider()

async def handle_uploaded_receipts(filename, request):
    saved_file = await save_locally(filename, request)
    asyncio.ensure_future(process_file(saved_file))


async def process_file(saved_file):
    archive_receipts(saved_file)
    receipts = unpack_archive(saved_file)
    grouped = group_by_extension(receipts)
    parsed_receipts = []
    for ext in grouped:
        strategy = provider.get_strategy_for_extension(ext)
        parsed_receipts = parsed_receipts + strategy.parse_data(grouped[ext]) 

    filtered_receipts = filter_receipts(parsed_receipts)
    await publish("scanned-receipts", filtered_receipts)

    clean_directories()


