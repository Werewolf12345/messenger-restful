package com.alexboriskin.messenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.alexboriskin.messenger.models.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

    @Override
    public Response toResponse(DataNotFoundException ex) {
        return Response.status(Status.NOT_FOUND)
                .entity(new ErrorMessage(ex.getMessage(), 404, "Read instructions, asshole!"))
                .build();
    }

}
