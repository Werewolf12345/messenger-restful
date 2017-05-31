package com.alexboriskin.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.alexboriskin.messenger.models.Message;
import com.alexboriskin.messenger.resources.beans.MessageFilterBean;
import com.alexboriskin.messenger.services.MessageService;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    MessageService messageService = new MessageService();

    @GET
    public List<Message> getAll(@BeanParam MessageFilterBean filter) {
        if (filter.getYear() > 0) {
            return messageService.getAllMessagesForYear(filter.getYear());
        }

        if (filter.getStart() >= 0 && filter.getSize() > 0) {
            return messageService.getAllMessagesPaginated(filter.getStart(),
                    filter.getSize());
        }

        return messageService.getAllMessages();
    }

    @GET
    @Path("/{messageId}")
    public Message getOne(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
        Message message = messageService.getMessage(id);
        message.addLink(getUriForSelf(uriInfo, message), "self");
        message.addLink(getUriForProfile(uriInfo, message), "profile");
        message.addLink(getUriForComments(uriInfo, message), "comments");
        return message;
    }

    private String getUriForComments(UriInfo uriInfo, Message message) {
        String uri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class, "callCommentResource")
                .resolveTemplate("messageId", message.getId())
                .build()
                .toString();
        return uri;
    }

    private String getUriForProfile(UriInfo uriInfo, Message message) {
        String uri = uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(message.getAuthor())
                .build()
                .toString();
        return uri;
    }

    private String getUriForSelf(UriInfo uriInfo, Message message) {
        String uri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(Long.toString(message.getId()))
                .build()
                .toString();
        return uri;
    }

    @POST
    public Response addNew(Message message, @Context UriInfo uriInfo)
            throws URISyntaxException {
        Message newMessage = messageService.addMessage(message);
        return Response
                .created(new URI(uriInfo.getPath() + "/" + newMessage.getId()))
                .entity(newMessage).build();
    }

    @PUT
    @Path("/{messageId}")
    public Message update(@PathParam("messageId") long id, Message message) {
        message.setId(id);
        return messageService.updateMessage(message);
    }

    @DELETE
    @Path("/{messageId}")
    public void delete(@PathParam("messageId") long id) {
        messageService.deleteMessage(id);
        ;
    }

    @Path("/{messageId}/comments")
    public CommentResource callCommentResource() {
        return new CommentResource();
    }
}
