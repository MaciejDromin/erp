package com.soitio.commons.ws;

public record WsMessage<T>(String eventType, T content) {

}
