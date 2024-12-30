from receipt_service import handle_uploaded_receipts
from fastapi import FastAPI, UploadFile, status, Response
import asyncio


app = FastAPI(lifespan=lifespan)


@app.post("/receipts")
async def upload_file(file: UploadFile):
    asyncio.ensure_future(handle_uploaded_receipts(file.filename, await file.read()))
    return {"filename": file.filename, "status": "PROCESSING"}


@app.get("/healthcheck")
async def healthcheck():
    return Response(status_code=status.HTTP_200_OK)
