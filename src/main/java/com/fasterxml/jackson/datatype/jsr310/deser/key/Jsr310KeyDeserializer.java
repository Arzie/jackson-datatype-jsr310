package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.DateTimeException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.key.Jsr310NullKeySerializer;

abstract class Jsr310KeyDeserializer extends KeyDeserializer {

    @Override
    public final Object deserializeKey(String key, DeserializationContext ctxt)
        throws IOException
    {
        if (Jsr310NullKeySerializer.NULL_KEY.equals(key)) {
            // potential null key in HashMap
            return null;
        }
        return deserialize(key, ctxt);
    }

    protected abstract Object deserialize(String key, DeserializationContext ctxt)
        throws IOException;
 
    protected <T> T _rethrowDateTimeException(DeserializationContext ctxt,
            Class<?> type, DateTimeException e) throws IOException
    {
        throw JsonMappingException.from(ctxt,
                String.format("Failed to deserialize %s: (%s) %s",
                        type.getName(), e.getClass().getName(), e.getMessage()), e);
    }
}