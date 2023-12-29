package com.you.meet.cloud.provider.kafka.interceptor;

import cn.hutool.core.util.StrUtil;
import com.you.meet.nice.common.util.RequestIdUtil;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Headers;
import org.slf4j.MDC;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年06月27日 10:30
 * @desc
 */
public class RequestIdProducerInterceptor implements ProducerInterceptor<String, String> {

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        String requestId = MDC.get(RequestIdUtil.REQUEST_ID);
        requestId = StrUtil.isBlank(requestId) ? RequestIdUtil.getRequestId() : requestId;
        Headers headers = record.headers();
        headers.add(RequestIdUtil.REQUEST_ID, requestId.getBytes(StandardCharsets.UTF_8));

        return new ProducerRecord<>(
                record.topic(),
                record.partition(),
                record.timestamp(),
                record.key(),
                record.value(),
                headers);
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
