from receipt_service import handle_uploaded_receipts
from fastapi import FastAPI, UploadFile, status, Response
from contextlib import asynccontextmanager
from registration_client import register, deregister
import asyncio
import uuid


service_id = ''


@asynccontextmanager
async def lifespan(app: FastAPI):
    service_id = str(uuid.uuid4())
    register(service_id)
    yield
    deregister(service_id)


app = FastAPI(lifespan=lifespan)


@app.post("/receipts")
async def upload_file(file: UploadFile):
    asyncio.ensure_future(handle_uploaded_receipts(file.filename, await file.read()))
    return {"filename": file.filename, "status": "PROCESSING"}


@app.get("/healthcheck")
async def healthcheck():
    return Response(status_code=status.HTTP_200_OK)
