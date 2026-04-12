/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.filter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
/**
 *
 * @author karandeep Singh Jalf
 */
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("Incoming Request");
        System.out.println("Method: " + requestContext.getMethod());
        System.out.println("Path: " + requestContext.getUriInfo().getPath());
        System.out.println("Headers: " + requestContext.getHeaders());
    }
   

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        System.out.println("utgoing Response");
        System.out.println("Status: " + responseContext.getStatus());
        System.out.println("Headers: " + responseContext.getHeaders());
    }
}

