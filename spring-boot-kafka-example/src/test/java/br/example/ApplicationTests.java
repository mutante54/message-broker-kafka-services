package br.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.example.kafka.receiver.Receiver;
import br.example.kafka.sender.Sender;
import br.example.kafka.vo.MessageMailVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private Sender sender;

	@Autowired
	private Receiver receiver;

	@Test
	public void testReceiverCustomMessage() throws Exception {

		int count = 100;

		for (int i = 1; i <= count; i++) {
			MessageMailVO mvo = new MessageMailVO("batchMessageTemplate","Custom-Message #" + i + " >> This is a CUSTOM message!");
			this.sender.sendMessage("teste", "custom-messages", mvo);
		}

		this.receiver.getLatchTopicCustom().await(10000, TimeUnit.MILLISECONDS);

		assertThat(this.receiver.getLatchTopicCustom().getCount()).isEqualTo(0);
	}

	@Test
	public void testReceiverBatchMessage() throws Exception {

		int count = 10000;

		for (int i = 1; i <= count; i++) {
			MessageMailVO mvo = new MessageMailVO("batchMessageTemplate","Batch-Message #" + i + " >> This is a BATCH message!");
			this.sender.sendMessage("teste", "batch-messages", mvo);
		}

		this.receiver.getLatchTopicBatch().await(30000, TimeUnit.MILLISECONDS);

		assertThat(this.receiver.getLatchTopicBatch().getCount()).isEqualTo(0);
	}
}
