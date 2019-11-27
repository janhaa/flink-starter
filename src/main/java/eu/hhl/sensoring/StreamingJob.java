package eu.hhl.sensoring;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class StreamingJob {
	private static final Logger LOG = LoggerFactory.getLogger(StreamingJob.class);

	public static void main(String[] args) throws Exception {
		// setup Flink environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		// env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		// intialize source
		FlinkKafkaConsumer consumer = new FlinkKafkaConsumer<>("flows-raw", new MyKafkaDeserializationSchema(),
				properties);
		DataStream<In> stream = env.addSource(consumer).name("RawFlows");

		// build data stream
		DataStream<Out> flows = stream.map(new MapFunction<In, Out>() {
			@Override
			public Out map(In record) throws Exception {
				
			}
		});

		FlinkKafkaProducer<String> kafkaProducer = new FlinkKafkaProducer<Out>("localhost:9092", "flows-verified",
				new SimpleStringSchema());
		flowsAsJson.addSink(kafkaProducer).name("VerifiedFlows");

		env.execute("Test");
	}

}