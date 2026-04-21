/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.exception;

import com.mycompany.smartcampus.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 *
 * @author karandeep Singh Jalf
 */
@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

       @Override
    public Response toResponse(SensorUnavailableException exception){
        ErrorMessage errorMessage = new ErrorMessage
        (exception.getMessage(),403,
                "docs/error");
        return Response.status(Response.Status.FORBIDDEN)
                .entity(errorMessage).build();
    }
}