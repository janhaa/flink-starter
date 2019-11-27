package eu.hhl.sensoring;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonConsumerRecord {
    public ConsumerRecord source;
    public ObjectNode value;
}