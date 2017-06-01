import java.util.Properties;
import java.util.Random;
import java.io.FileWriter;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;

public class SimpleProducer	{
	public static void main(String args[]) throws Exception	{
		// If wrong usage
		if(args.length == 0)	{
			System.out.println("Invalid Usage: Enter Topic Name");
			return;
		}

		// Identify the topic name
		String topicName = args[0].toString();

		//Set Properties 
		Properties props = new Properties();

		// To identify the localhost id
		props.put("bootstrap.servers","localhost:9092");
		//props.put("acks", "all");

		// If request fails, automatic retry
		props.put("retries",1);

		// Buffer Size of config
		props.put("batch.size",16384);

		//props.put("linger.ms",1);

		// Buffer size of memory
		props.put("buffer.memory",33554432);

		// Can select either StringSerializer or ByteArraySerializer
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		// Make a producer with the given attributes
		Producer<String, String> producer = new KafkaProducer<String, String>(props);


		for(int i = 0; i < 10; i++)
			producer.send(new ProducerRecord<String, String>(topicName, Integer.toString(i), Integer.toString(i)));
		System.out.println("Message sent successfully");
		producer.close();
	}
}

// A function to create JSON file in requird format
class CreateJson {
   public create() throws Exception  {
      Random rg = new Random();
      JSONObject obj = new JSONObject();
      JSONObject properties = new JSONObject();

      // Open a log file
      FileWriter fw = new FileWriter("log");

      for(int i = 0; i < 1000; i++) {
         // Random Temperature
         properties.put("temperature", new Integer(rg.nextInt(200)));
         // There are 1440 minutes in a day, assume this as max
         properties.put("up_time", new Integer(rg.nextInt(1440)));

         //Device ID
         obj.put("device_id", new Integer(10000));

         //Device Type
         int devType = rg.nextInt(90);
         if (devType > 90) 
            devType = devType/90 + 65;
         else if (devType < 65)  
            devType = devType/61 + 65;
         obj.put("device_type", new Character((char)devType));
         
         obj.put("properties", properties);
         // Convert JSON to string to store it into a file
         fw.write(obj.toJSONString());
         fw.write("\n");
      }
      fw.close();
   }
}