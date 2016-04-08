package pers.yangchen.PM;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

/**
 * Created by yangchen on 16-4-7.
 */
public class GetPMTF {

    public String getMethod(){
        try {
            String getURL = "http://www.pm25.in/api/querys/aqi_ranking.json?token=5j1znBVAsnSf5xQyNQyq";
            URL getUrl = new URL(getURL);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/json)");
            connection.setRequestMethod("GET");
            connection.connect();
            if(connection.getResponseCode()==200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                // StringBuilder result = new StringBuilder();
                String result = "";
                while ((line = reader.readLine()) != null) {
                    result = result + line;
                    System.out.println(line);
                }
                JSONArray object = new JSONArray(result);
                List<PMTFBean> pmtfBeanList = new ArrayList<PMTFBean>();
                PMTFBean pmtfBean = new PMTFBean();

                String topic = "pm2.5";
                KafkaProducer kafkaProducer = new KafkaProducer(topic);
                Producer producer = createProducer();
                String content = "";
                producer.send(new KeyedMessage<Integer, String>(topic, content));
                reader.close();
                connection.disconnect();
                return result;
            }
            else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Producer createProducer() {
        Properties properties = new Properties();
        properties.put("zookeeper.connect", "127.0.0.1:2181");//声明zk
        properties.put("serializer.class", StringEncoder.class.getName());
        properties.put("metadata.broker.list", "127.0.0.1:9092");// 声明kafka broker
        return new Producer<Integer, String>(new ProducerConfig(properties));
    }

}
