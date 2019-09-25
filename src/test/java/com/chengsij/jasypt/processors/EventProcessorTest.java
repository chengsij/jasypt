package com.chengsij.jasypt.processors;

import com.chengsij.jasypt.config.StreamContext;
import com.chengsij.jasypt.encryptor.StringEncryptor;
import com.chengsij.jasypt.processor.EventProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EventProcessorTest {

  @Autowired public EventProcessor processor;

  @Autowired private StreamContext context;

  @Autowired private MessageCollector messageCollector;

  @Test
  public void simpleEncrytionTest() throws InterruptedException {
    String payload = "secrete message";
    processor.process(payload);
    Message<?> receivedMessage = blockingListener(context.filterTopic());
    String result = receivedMessage.getPayload().toString();
    assertNotEquals(payload, result);

    String decryptedResult = StringEncryptor.getEncryptor().decrypt(result);
    assertEquals(payload, decryptedResult);
  }

  private Message<?> blockingListener(MessageChannel channel) throws InterruptedException {
    BlockingQueue<Message<?>> clientTest = messageCollector.forChannel(channel);
    Message<?> receivedPayload = clientTest.poll(2, TimeUnit.SECONDS);
    return receivedPayload;
  }
}
