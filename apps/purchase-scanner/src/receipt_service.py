from file_manager import save_locally, unpack_archive, clean_directories
from receipt_scan import filter_receipts, group_by_extension
from artifact_client import archive_receipts
from message_publisher import publish
from fastapi import UploadFile
import strategy_provider
import json
import asyncio

provider = strategy_provider.StrategyProvider()

async def handle_uploaded_receipts(filename, request, orgId):
    saved_file = await save_locally(filename, request)
    asyncio.ensure_future(process_file(filename, saved_file, orgId))


async def process_file(filename, saved_file, orgId):
    archive_receipts(filename, saved_file, orgId)
    receipts = unpack_archive(saved_file)
    grouped = group_by_extension(receipts)
    parsed_receipts = []
    for ext in grouped:
        strategy = provider.get_strategy_for_extension(ext)
        parsed_receipts = parsed_receipts + strategy.parse_data(grouped[ext]) 

    filtered_receipts = filter_receipts(parsed_receipts)
    org_wrapper = {
        "orgId": orgId,
        "data": filtered_receipts
    }
    await publish("scanned-receipts", org_wrapper)

    clean_directories()


