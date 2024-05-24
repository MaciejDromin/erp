import pika
import json

async def publish(topic, message):
    parameters = pika.URLParameters('amqp://guest:guest@localhost:5672')
    connection = pika.BlockingConnection(parameters)
    channel = connection.channel()
    channel.basic_publish('receipt_exchange',
                      'receipt_queue',
                      json.dumps(message),
                      pika.BasicProperties(content_type='application/json',
                                           delivery_mode=pika.DeliveryMode.Transient))
    connection.close()

