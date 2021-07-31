package com.feng.common.ulits;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 用于long类型返回前端展示精度丢失的问题  自定义的序列化器，把long类型转化为字符串
 */
public class MySerialze extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

       if(value != null){
           jsonGenerator.writeString(value.toString());
       }
    }
}
