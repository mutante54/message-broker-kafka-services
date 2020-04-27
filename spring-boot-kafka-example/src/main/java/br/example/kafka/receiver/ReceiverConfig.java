package br.example.kafka.receiver;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import br.example.kafka.vo.MessageVO;

@Configuration
@EnableKafka
public class ReceiverConfig {

	@Value("${kafka.bootstrap.servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		// list of host:port pairs used for establishing the initial connections to the Kakfa cluster (Ex.: localhost:9092,another.host:9092)
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		/**
		 * ConsumerConfig.AUTO_OFFSET_RESET_CONFIG specifies what to do when there is no
		 * initial offset in Kafka or if the current offset does not exist any more on
		 * the server (e.g. because that data has been deleted): earliest: automatically
		 * reset the offset to the earliest; offset latest: automatically reset the
		 * offset to the latest offset; none: throw exception to the consumer if no
		 * previous offset is found for the consumer’s group; anything else: throw
		 * exception to the consumer.
		 */
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		return props;
	}

	@Bean
	public ConsumerFactory<String, MessageVO> consumerFactory() {
		JsonDeserializer<MessageVO> jsonDeserializer = new JsonDeserializer<>(MessageVO.class);
		jsonDeserializer.addTrustedPackages("*");
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), jsonDeserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, MessageVO> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, MessageVO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setErrorHandler(new SeekToCurrentErrorHandler());

		return factory;
	}

	@Bean
	public Receiver receiver() {
		return new Receiver();
	}
}
