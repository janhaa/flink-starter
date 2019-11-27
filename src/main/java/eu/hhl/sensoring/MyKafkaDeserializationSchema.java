package eu.hhl.sensoring;

import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

public class MyKafkaDeserializationSchema implements KafkaDeserializationSchema<JsonConsumerRecord> {

    private static final Logger LOG = LoggerFactory.getLogger(MyKafkaDeserializationSchema.class);
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonConsumerRecord deserialize(ConsumerRecord<byte[],byte[]> record) {
        JsonConsumerRecord result = new JsonConsumerRecord();
        result.source = record;
        try {
            result.value = mapper.readValue(record.value(), ObjectNode.class);
        } catch(IOException exception) {
            result.value = null;
            LOG.error("IOException when parsing ConsumerRecord.value: " + exception.getMessage());
        }
        return result;
    }

    public boolean isEndOfStream(JsonConsumerRecord nextElement) 
    {
        return false;
    }

    public TypeInformation<JsonConsumerRecord> getProducedType() {
        return TypeInformation.of(JsonConsumerRecord.class);
    }
}