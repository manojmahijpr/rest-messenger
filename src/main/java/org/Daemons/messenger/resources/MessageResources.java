package org.Daemons.messenger.resources;


import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.Daemons.messenger.models.Message;
import org.Daemons.messenger.resources.beans.MessageFilterBean;
import org.Daemons.messenger.service.MessageService;

/*
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
*/

/*
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
*/
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = { MediaType.APPLICATION_JSON , MediaType.TEXT_XML})
public class MessageResources {
	
	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getAllMessages(@QueryParam("year") int year,
										@QueryParam("start") int start,
										@QueryParam("size") int size) {
		
		if(year>0)
			return messageService.getAllMessageForYear(year);
		
		if (start >= 0 && size > 0) {
			return messageService.getAllMessagePaginated(start, size);
		}
		
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/getByBeanParam")
	public List<Message> getMessageFilterBeanParam(@BeanParam MessageFilterBean bean) {
		
		if(bean.getYear() >0)
			return messageService.getAllMessageForYear(bean.getYear());
		if (bean.getStart() >= 0 && bean.getSize() >= 0)
			return messageService.getAllMessagePaginated(bean.getStart(), bean.getSize());
		
		return messageService.getAllMessages();
	}
	
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId,@Context UriInfo uriInfo) {
		Message message = messageService.getMessage(messageId);
		message.addLink(uriInfo.getAbsolutePath().toString(), "self");
		message.addLink(getProfileUri(uriInfo,message.getAuthor()), "profile");
		message.addLink(getCommentsUri(uriInfo,message), "comments");
		return message;
	}
	
	private String getCommentsUri(UriInfo uriInfo, Message message) {
		/*URI uri = uriInfo.getBaseUriBuilder()
				.path(MessageResources.class)
				.path(Long.toString(message.getId()))
				.path("comments")
				.path(CommentResource.class)
				.build();*/
		
		URI uri = uriInfo.getBaseUriBuilder()
				.path(MessageResources.class)
				.path(MessageResources.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build();
		return uri.toString();
	}

	private String getProfileUri(UriInfo uriInfo, String author) {
		URI uri = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(author)
				.build();
		return uri.toString();
	}

	@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public Message removeMessage(@PathParam("messageId") long messageId) {
		return messageService.removeMessage(messageId);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(@PathParam("messageId") long messageId) {
		return new CommentResource(messageId);
	}
}