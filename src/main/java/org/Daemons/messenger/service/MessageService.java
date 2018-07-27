package org.Daemons.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.Daemons.messenger.database.DatabaseClass;
import org.Daemons.messenger.exception.DataNotFoundException;
import org.Daemons.messenger.models.Message;

public class MessageService {

	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1L,"Hello World","Manoj"));
		messages.put(2L, new Message(2L,"Hello Android","Deepak"));
	}
	
	public List<Message> getAllMessageForYear(int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR)==year)
				messagesForYear.add(message);
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagePaginated(int start,int size) {
		List<Message> list = new ArrayList<Message>(messages.values());
		if(start+size>=list.size()) return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
	
	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if(message == null)
			throw new DataNotFoundException("Message Not Found id: " + id);
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size()+1);
		if(message.getCreated()==null)
			message.setCreated(new Date());
		
		messages.put(message.getId(), message);
		return message;
	}
	public Message updateMessage(Message message) {
		if(message.getId() <= 0) {
			return null;
		}
		if(message.getCreated()==null)
			message.setCreated(new Date());
		messages.put(message.getId(), message);
		return message;
	}
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
}
