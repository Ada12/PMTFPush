package pers.yangchen.PM;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.util.List;
import java.util.Properties;

/**
 * Created by yangchen on 16-4-7.
 */
public class KafkaProducer {

    private String topic;

    public KafkaProducer(String topic){
        super();
        this.topic = topic;
    }

//    public void testRun(String line){
//        Producer producer = createProducer();
//        int i=0;
//        System.out.println("Size======================================="+ee.size());
//        while (i<ee.size()){
//            String inInfo = ee.get(i).getFintInstatid().substring(0, ee.get(i).getFintInstatid().length() - ee.get(i).getFintStatid().length()-1);
//            String stationInfo = "out"+ee.get(i).getFintStatid()+" "+"in"+inInfo+" ";
//            producer.send(new KeyedMessage<Integer, String>(topic, stationInfo));
//            i++;
//        }
//
//    }

    public Producer createProducer() {
        Properties properties = new Properties();
        properties.put("zookeeper.connect", "127.0.0.1:2181");//声明zk
        properties.put("serializer.class", StringEncoder.class.getName());
        properties.put("metadata.broker.list", "127.0.0.1:9092");// 声明kafka broker
        return new Producer<Integer, String>(new ProducerConfig(properties));
    }
}
