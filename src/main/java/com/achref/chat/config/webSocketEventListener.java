package com.achref.chat.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.achref.chat.controller.chatMessage;
import com.achref.chat.controller.messagetype;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class webSocketEventListener {

	private final SimpMessageSendingOperations messagetemplate ; 
	@EventListener
	public void handleWebSocketDisconnectListenner (SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor =StompHeaderAccessor.wrap(event.getMessage()) ;
		String username =(String) headerAccessor.getSessionAttributes().get("username") ;
	if( username != null ) {
	 
        System.out.println("User disconnected: " + username);
        var ChatMessage = chatMessage.builder()
                .type(messagetype.LEAVE)
                .sender(username)
                .build();
        messagetemplate.convertAndSend("topic/public",ChatMessage);
	}
	}
}
