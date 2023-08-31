package com.Jobify;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class BlobSerializer extends JsonSerializer<Blob> {

    @Override
    public void serialize(Blob value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            gen.writeBinary(value.getBytes(1, (int) value.length()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void serializeWithType(Blob value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(value, gen, serializers);
    }
}
