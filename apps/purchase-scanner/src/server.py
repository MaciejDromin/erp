from receipt_service import handle_uploaded_receipts
from fastapi import FastAPI, UploadFile, status, Response, Request
from urllib.parse import unquote
import asyncio


app = FastAPI()


@app.post("/receipts")
async def upload_file(request: Request):
    filename = request.headers['filename']
    orgId = request.headers['X-OrgId']
    filename = unquote(filename)
    await handle_uploaded_receipts(filename, request, orgId)
    return {"filename": filename, "status": "PROCESSING"}


@app.get("/healthcheck")
async def healthcheck():
    return Response(status_code=status.HTTP_200_OK)
