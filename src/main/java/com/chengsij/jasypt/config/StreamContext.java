package com.chengsij.jasypt.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamContext {
    String DEADLETTER = "dead-letter";

    String EVENT_TOPIC = "event-topic";

    String FILTER_TOPIC = "filter-topic";

    @Input(EVENT_TOPIC)
    SubscribableChannel eventTopic();

    @Output(FILTER_TOPIC)
    MessageChannel filterTopic();

    @Output(DEADLETTER)
    MessageChannel deadLetter();

}
