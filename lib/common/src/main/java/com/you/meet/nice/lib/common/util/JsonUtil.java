package com.you.meet.nice.lib.common.util;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.you.meet.nice.lib.common.exception.CommonErrorCode;
import com.you.meet.nice.lib.common.exception.MeetException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.TimeZone;

/**
 * @author: zhoujl
 */
@Slf4j
public class JsonUtil {

    private static final PrettyPrinter PRETTY_PRINTER = new DefaultPrettyPrinter();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 忽略json字符串中不识别的属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象 “No serializer found for class com.xxx.xxx”
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // NULL不参与序列化
        // OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 指定时区，默认 UTC，而不是 jvm 默认时区
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static <T> T parseStr2Obj(String jsonStr, Class<T> cls) {
        Assert.hasText(jsonStr, "json不能为空");
        Assert.notNull(cls, "cls不能为null");

        try {
            return cls.equals(String.class) ? (T) jsonStr : OBJECT_MAPPER.readValue(jsonStr, cls);
        } catch (IOException e) {
            log.error("json解析出错", e);
            throw MeetException.meet(CommonErrorCode.ERR_5000, e);
        }
    }

    public static <T> T parseStr2Obj(String jsonStr, TypeReference typeReference) {
        Assert.hasText(jsonStr, "json不能为空");
        Assert.notNull(typeReference, "cls不能为null");

        try {
            return (T) OBJECT_MAPPER.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            log.error("json解析出错", e);
            throw MeetException.meet(CommonErrorCode.ERR_5000, e);
        }
    }

    public static <T> String parseObj2Str(T obj) {
        Assert.notNull(obj, "obj不能为null");

        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            log.error("json解析出错", e);
            throw MeetException.meet(CommonErrorCode.ERR_5000, e);
        }
    }

    public static <T> String parseObj2PrettyStr(T obj) {
        Assert.notNull(obj, "obj不能为null");

        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writer(PRETTY_PRINTER).writeValueAsString(obj);
        } catch (IOException e) {
            log.error("json解析出错", e);
            throw MeetException.meet(CommonErrorCode.ERR_5000, e);
        }
    }

    public static <T> T parseStr2Collection(String jsonStr, TypeReference<T> typeReference) {
        Assert.hasText(jsonStr, "json不能为空");
        Assert.notNull(typeReference, "typeReference不能为null");

        try {
            return (T) (typeReference.getType().equals(String.class) ? jsonStr : OBJECT_MAPPER.readValue(jsonStr, typeReference));
        } catch (IOException e) {
            log.error("json解析出错", e);
            throw MeetException.meet(CommonErrorCode.ERR_5000, e);
        }
    }

    public static <T> T parseStr2Collection(byte[] jsonBytes, TypeReference<T> typeReference) {
        if (ArrayUtils.isEmpty(jsonBytes)) {
            throw new IllegalArgumentException("jsonBytes不能为空");
        }
        Assert.notNull(typeReference, "typeReference不能为null");

        try {
            return OBJECT_MAPPER.readValue(jsonBytes, typeReference);
        } catch (IOException e) {
            log.error("jsonBytes解析出错", e);
            throw MeetException.meet(CommonErrorCode.ERR_5000, e);
        }
    }

    public static <T> T parseStr2Collection(String jsonStr, Class<?> collectionCls, Class<?> elementCls) {
        Assert.hasText(jsonStr, "json不能为空");
        Assert.notNull(collectionCls, "collectionCls不能为null");
        Assert.notNull(elementCls, "elementCls不能为null");

        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionCls, elementCls);
        return parseStr2Obj(jsonStr, javaType);
    }

    public static <T> T parseStr2Obj(String jsonStr, JavaType javaType) {
        Assert.hasText(jsonStr, "json不能为空");
        Assert.notNull(javaType, "javaType不能为null");

        try {
            return OBJECT_MAPPER.readValue(jsonStr, javaType);
        } catch (IOException e) {
            log.error("json解析出错", e);
            throw MeetException.meet(CommonErrorCode.ERR_5000, e);
        }
    }
}