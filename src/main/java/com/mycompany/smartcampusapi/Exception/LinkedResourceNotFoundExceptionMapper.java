    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.Exception;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 *
 * @author karandeep Singh Jalf
 */
    @Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {
        return Response.status(422)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .build();
    }
}

