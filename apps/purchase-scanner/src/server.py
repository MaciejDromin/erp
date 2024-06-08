from typing import Union
from receipt_service import handle_uploaded_receipts
from fastapi import FastAPI, File, UploadFile
import asyncio

app = FastAPI()


@app.post("/receipts")
async def upload_file(file: UploadFile):
    asyncio.ensure_future(handle_uploaded_receipts(file.filename, await file.read()))
    return {"filename": file.filename, "status": "PROCESSING"}
