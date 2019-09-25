package com.chengsij.jasypt.processor;

import com.chengsij.jasypt.config.StreamContext;
import com.chengsij.jasypt.encryptor.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamContext.class)
public class EventProcessor implements Processor<String>{
    private static final Logger logger = LoggerFactory.getLogger(EventProcessor.class);

    @Autowired
    private StreamContext context;

    private long timeout = 2000l;

    @Override
    @StreamListener(StreamContext.EVENT_TOPIC)
    public void process (String message){
        String encryptedMessage = StringEncryptor.getEncryptor().encrypt(message);
        Message<String> outboundMessage = MessageBuilder.withPayload(encryptedMessage).build();
        context.filterTopic().send(outboundMessage, timeout);
    }
}
