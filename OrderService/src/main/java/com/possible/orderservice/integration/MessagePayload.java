package com.possible.orderservice.integration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@JsonAutoDetect
public class MessagePayload {
        @JsonProperty
        private String classType;
        @JsonProperty
        private Object payload;
        @JsonProperty
        private Map<String, Object> metadata;

//        public Object getPayload() throws ClassNotFoundException {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(this.payload, this.getClassType());
//        }

        MessagePayload(final String classType, final Object payload, final Map<String, Object> metadata) {
            this.classType = classType;
            this.payload = payload;
            this.metadata = metadata;
        }

        public static MessagePayload.MessagePayloadBuilder builder() {
            return new MessagePayload.MessagePayloadBuilder();
        }

        public String getClassType() {
            return this.classType;
        }

        public Map<String, Object> getMetadata() {
            return this.metadata;
        }

        public void setClassType(final String classType) {
            this.classType = classType;
        }

        public void setPayload(final Object payload) {
            this.payload = payload;
        }

        public void setMetadata(final Map<String, Object> metadata) {
            this.metadata = metadata;
        }

        public static class MessagePayloadBuilder {
            private String classType;
            private Object payload;
            private Map<String, Object> metadata;

            MessagePayloadBuilder() {
            }

            public MessagePayload.MessagePayloadBuilder classType(final String classType) {
                this.classType = classType;
                return this;
            }

            public MessagePayload.MessagePayloadBuilder payload(final Object payload) {
                this.payload = payload;
                return this;
            }

            public MessagePayload.MessagePayloadBuilder metadata(final Map<String, Object> metadata) {
                this.metadata = metadata;
                return this;
            }

            public MessagePayload build() {
                return new MessagePayload(this.classType, this.payload, this.metadata);
            }

            public String toString() {
                return "MessagePayload.MessagePayloadBuilder(classType=" + this.classType + ", payload=" + this.payload + ", metadata=" + this.metadata + ")";
            }
        }

}
