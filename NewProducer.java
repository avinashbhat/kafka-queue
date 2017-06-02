/* A producer to create random data of Iot and send via kafka
	Author - AB
	Date - 01Jun2017
	*/

import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;

public class NewProducer	{
	public static void main(String args[]) throws Exception	{
		// If wrong usage
		if(args.length == 0)	{
			System.out.println("Error: USAGE - $java {-cp \"...\":.} NewConsumer <topicname>");
			return;
		}
		// Identify the topic name
		String topicName = args[0].toString();
		//Set Properties 
		Properties props = new Properties();
		// To identify the localhost id
		props.put("bootstrap.servers","localhost:9092");
		// If request fails, automatic retry
		props.put("retries",1);
		// Buffer Size of config
		props.put("batch.size",16535);
		// Buffer size of memory
		props.put("buffer.memory",30000000);
		// Can select either StringSerializer or ByteArraySerializer
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// Make a producer with the given attributes
		Producer producer = new KafkaProducer<String, String>(props);
		CreateJson objJSON = new CreateJson();
		for(int i = 0; i < 1000; i++)	{
			// Assuming that the device is creating the details in JSON format
			objJSON.create();
			producer.send(new ProducerRecord<String, String>(topicName, objJSON.toString()));
		}
		producer.close();
	}
}
// to create JSON file in requird format
class CreateJson extends JSONObject	{
	JSONObject obj = new JSONObject();
   	public void create() throws Exception  {
      	Random rg = new Random();
      	JSONObject properties = new JSONObject();
      	// Open a log file
      	//FileWriter fw = new FileWriter("log");
         // Random Temperature
    	properties.put("temperature", new Integer(rg.nextInt(200)));
         // Assume max a device can be up is 100 days
        properties.put("up_time", new Integer(rg.nextInt(100)));
         //Device ID
        obj.put("device_id", new Integer(rg.nextInt(10000)));
         //Device Type
        int devType = 65 + ((int)Math.random() * 27);
        String s = Character.toString((char)devType);
        obj.put("device_type", s);
        obj.put("properties", properties);
         // Convert JSON to string to store it into a file
        //fw.write(obj.toJSONString());
        //fw.write("\n");
       	//fw.close();
   }
   // overriding the toString method to return JSONString
   public String toString()	{
   		return obj.toJSONString();
   }
}