FROM python:3.9

WORKDIR /code

COPY ./requirements.txt /code/requirements.txt

RUN apt-get update
RUN apt-get install build-essential -y
RUN apt-get install libpoppler-cpp-dev -y
RUN apt-get install pkg-config -y
RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt
RUN mkdir /code/tmp

COPY ./src /code/app

CMD ["fastapi", "run", "app/server.py", "--port", "80"]
