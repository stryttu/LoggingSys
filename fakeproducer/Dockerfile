FROM python:3-slim
WORKDIR /root
ADD start.sh /root
ADD kafka_producer_interactive.py /root
#ENTRYPOINT ["/bin/sh", "-c"]
RUN pip3 install kafka-python
#CMD python3 start.sh