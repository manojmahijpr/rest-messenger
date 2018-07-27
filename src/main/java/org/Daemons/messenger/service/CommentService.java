package org.Daemons.messenger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.Daemons.messenger.database.DatabaseClass;
import org.Daemons.messenger.models.Comment;
import org.Daemons.messenger.models.ErrorMessage;
import org.Daemons.messenger.models.Message;

public class CommentService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		
		ErrorMessage errorMessage = new ErrorMessage("Comment Not Found", 500, "https://www.diaryapp.com/exception/Documentation");
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.build();
		
		Message message = messages.get(messageId);
		if(message == null)
			throw new WebApplicationException(response);
		
		Comment comment = message.getComments().get(commentId);
		if(comment == null)
			throw new NotFoundException(response);
		
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size()+1);
		if(comment.getCreated()==null)
			comment.setCreated(new Date());
		
		comments.put(comment.getId(), comment);
		return comment;
	}
	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() <= 0) {
			return null;
		}
		if(comment.getCreated()==null)
			comment.setCreated(new Date());
		comments.put(comment.getId(), comment);
		return comment;
	}
	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
