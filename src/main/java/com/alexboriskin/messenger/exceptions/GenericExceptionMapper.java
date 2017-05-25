package com.alexboriskin.messenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.alexboriskin.messenger.models.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage(ex.getMessage(), 500, "Read instructions, asshole!"))
                .build();
    }

}
