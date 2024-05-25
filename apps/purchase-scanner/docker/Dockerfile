FROM python:3.9

WORKDIR /code

COPY ./requirements.txt /code/requirements.txt

RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt
RUN mkdir /code/tmp

COPY ./src /code/app

CMD ["fastapi", "run", "app/server.py", "--port", "80"]
