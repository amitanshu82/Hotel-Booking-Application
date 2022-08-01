import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer {

    public static void main(String[] args) {
        /** Create Kafka Consumer and start consuming messages from the topic "message"
        * change the bootstrap.server properties to connect to your Kafka cluster */

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.1.7:9092");
        properties.put("group.id", "booking");
        properties.put("enable.auto.commit","true");
        properties.put("auto.commit.interval.ms","1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        consumer.subscribe(Arrays.asList("message"));

        try{
            while(true){
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String,String> record :records){
                    System.out.printf("offset = %d, key = %s , value = %s%n", record.offset(),record.key(),record.value());
                }
            }
        }finally {
            consumer.close();
        }

    }
}
