package com.soonoleum.education.repository.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.soonoleum.education.domain.Message;

@Data
public class ChatGPTRequest {
	private String model;
	private List<Message> messages;

	public ChatGPTRequest(String model, String prompt) {
		this.model = model;
		this.messages =  new ArrayList<>();
		this.messages.add(new Message("user", prompt));
	}
}
