package org.Daemons.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.Daemons.messenger.models.Comment;
import org.Daemons.messenger.service.CommentService;


@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	private long messageId;
	
	private CommentService commentService = new CommentService();
	
	public CommentResource(long messageId) {
		this.messageId = messageId;
	}

	@GET
	public List<Comment> getAllComments() {
		return commentService.getAllComments(messageId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("commentId") long commentId) {
		return commentService.getComment(messageId,commentId);
	}
	
	@POST
	public Comment addComment(Comment comment) {
		return commentService.addComment(messageId,comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("commentId") long commentId, Comment comment) {
		comment.setId(commentId);
		return commentService.updateComment(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public Comment removeComment(@PathParam("commentId") long commentId) {
		return commentService.removeComment(messageId, commentId);
	}
}
