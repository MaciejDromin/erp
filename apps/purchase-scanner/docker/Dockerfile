FROM python:3.9

RUN apt-get update
RUN apt-get install build-essential -y
RUN apt-get install libpoppler-cpp-dev -y
RUN apt-get install pkg-config -y
RUN apt-get install tesseract-ocr -y
RUN apt-get install tesseract-ocr-pol -y
RUN apt-get install openjdk-17-jre-headless -y

WORKDIR /code

ENV SFTP_HOST sftp
ENV SFTP_USERNAME erp
ENV SFTP_PASSWORD admin
ENV SFTP_PORT 22

COPY ./requirements.txt /code/requirements.txt

RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt
RUN mkdir /code/tmp

COPY ./src /code/app

CMD ["fastapi", "run", "app/server.py", "--port", "80"]
