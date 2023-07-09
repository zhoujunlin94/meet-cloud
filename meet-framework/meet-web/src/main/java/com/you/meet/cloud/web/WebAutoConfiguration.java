package com.you.meet.cloud.web;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @author zhoujunlin
 * @date 2023年02月20日 10:37
 * @desc
 */
@ComponentScan
public class WebAutoConfiguration {

    @Bean
    public HttpMessageConverter fastJsonHttpMessageConverter() {
        //设置时区
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        FastJsonConfig jsonConfig = new FastJsonConfig();
        //日期格式
        jsonConfig.setDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        //特性设置
        jsonConfig.setFeatures(
                //Feature.OrderedField,
                Feature.IgnoreNotMatch
        );
        jsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteBigDecimalAsPlain,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.DisableCircularReferenceDetect
        );
        //字符编码
        jsonConfig.setCharset(StandardCharsets.UTF_8);
        //序列化设置
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
        jsonConfig.setSerializeConfig(serializeConfig);
        //反序列化设置
        ParserConfig parserConfig = ParserConfig.getGlobalInstance();
        parserConfig.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
        //"autoType is not support"问题,使用setAutoTypeSupport=true的全局设置
        parserConfig.setAutoTypeSupport(true);
        jsonConfig.setParserConfig(parserConfig);

        //处理中文乱码
        ArrayList<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.valueOf("text/json;charset=UTF-8"));

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(jsonConfig);

        return fastConverter;
    }
}
