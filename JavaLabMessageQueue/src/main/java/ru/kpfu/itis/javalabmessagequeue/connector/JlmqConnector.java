package ru.kpfu.itis.javalabmessagequeue.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.javalabmessagequeue.consumer.JlmqConsumer;
import ru.kpfu.itis.javalabmessagequeue.producer.JlmqProducer;

import java.util.concurrent.ExecutionException;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JlmqConnector {
    private String port;
    @Autowired
    private JlmqConsumer jlmqConsumer;
    @Autowired
    private JlmqProducer jlmqProducer;

    public JlmqConnector port(String port) {
        this.port = port;
        return this;
    }

    public JlmqConnector connect() throws ExecutionException, InterruptedException {
        jlmqProducer.connect(port);
        jlmqConsumer.connect(port);
        return this;
    }

    public JlmqConsumer consumer() {
        return jlmqConsumer;
    }

    public JlmqProducer producer() {
        return jlmqProducer;
    }

}
