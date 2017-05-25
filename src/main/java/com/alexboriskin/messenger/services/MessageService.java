package com.alexboriskin.messenger.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.alexboriskin.messenger.database.Database;
import com.alexboriskin.messenger.exceptions.DataNotFoundException;
import com.alexboriskin.messenger.models.Message;

public class MessageService {
    private Map<Long, Message> messages = Database.getMessages();


    public MessageService() {
        Message m1 = new Message(1L, "Message 1", "Author 1");
        Message m2 = new Message(2L, "Message 2", "Author 2");
        
        messages.put(m1.getId(), m1);
        messages.put(m2.getId(), m2);
    }


    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }
    
    public List<Message> getAllMessagesForYear(int year) {
        List<Message> messagesForYear = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        
        for (Message message : messages.values()) {
            cal.setTime(message.getDateCreated());
            if (cal.get(Calendar.YEAR) == year) {
                messagesForYear.add(message);
            }
        }
        return messagesForYear;
    }
    
    public List<Message> getAllMessagesPaginated(int start, int size) {
        ArrayList<Message> list = new ArrayList<Message>(messages.values());
        if (start + size > list.size()) return new ArrayList<Message>();
        return list.subList(start, start + size);  
        
    }


    public Message getMessage(Long id) {
        Message message = messages.get(id);
        if (message == null) {
            throw new DataNotFoundException("Message ID: " + id + " not found in database");
        }
        return message;
    }

    public Message addMessage(Message message) {
        Long newId = messages.size() + 1L;

        message.setId(newId);
        messages.put(newId, message);

        return message;
    }

    public Message updateMessage(Message message) {
        messages.put(message.getId(), message);
        return messages.get(message.getId());
    }
    
    public void deleteMessage(long id) {
        messages.remove(id);
    }
}
