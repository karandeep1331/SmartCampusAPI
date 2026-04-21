/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.mycompany.smartcampus.model.ErrorMessage;

/**
 *
 * @author karandeep Singh Jalf
 */
@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException ex) {

        ErrorMessage error = new ErrorMessage(
                ex.getMessage(), 
                409,
                "docs/error/room-not-empty"
        );

             return Response.status(Response.Status.CONFLICT)
                .entity(error)
                .type("application/json")
                .build();
    }
}