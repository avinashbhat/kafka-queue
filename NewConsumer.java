
/* A consumer to parse the received IoT data and raise flag if
   certain conditions are satisfied
   Author - AB
   Date - 02Jun2017
   */
import java.util.*;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NewConsumer {
   public static void main(String[] args) throws Exception {
      // If Wrong usage
      if(args.length == 0){
         System.out.println("Error: USAGE - $java {-cp \"...\":.} NewConsumer <topicname>");
         return;
      }
      String topicName = args[0].toString();
      Properties props = new Properties();
      // To identify the localhost id
      props.put("bootstrap.servers", "localhost:9092");
      // To identify the group ID
      props.put("group.id", "test");
      // To indicate how many milliseconds Kafka will wait for the ZooKeeper
      props.put("session.timeout.ms", "30000");
      // Deserialization
      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
      // Create a consumer with above properties
      KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
      // To subscrive to a specific topic
      consumer.subscribe(Arrays.asList(topicName));
      System.out.println("Subscribed to topic " +topicName);

      while (true) {
         // The ConsumerRecored reads in <K, V> form
         // where both of them are strings
         // To parse the string in V to JSON format
         // we use JSONParser
         JSONParser jParser = new JSONParser();
         ConsumerRecords<String, String> received = consumer.poll(100);
         for (ConsumerRecord<String, String> record : received) {
            // for each record
            JSONObject jObj = (JSONObject)jParser.parse(record.value());
            // read device id from JSONObject
            long id = (long)jObj.get("device_id");
            //read properties
            JSONObject devProp = (JSONObject)jObj.get("properties");
            long temp = (long)devProp.get("temperature");
            long up = (long)devProp.get("up_time");
            // if temperature or up time is higher than acceptable 
            if(temp > 45 || up > 60)  {
               if(temp > 45) 
                  System.out.println("Alert: Temperature: "+temp+" Device ID: "+id);
               else 
                  System.out.println("Alert: Up Time: "+up+" Device ID: "+id);
            }
         }
      }
   }
}