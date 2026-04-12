/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.Resource;

import com.mycompany.smartcampusapi.Model.Sensor;
import com.mycompany.smartcampusapi.Model.SensorReading;
import com.mycompany.smartcampusapi.Exception.SensorUnavailableException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karandeep Singh Jalf
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private String sensorId;

    private static List<SensorReading> readings = new ArrayList<>();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }


    @GET
    public List<SensorReading> getReadings() {
        return readings;
    }

   
    @POST
    public Response addReading(SensorReading reading) {


        Sensor sensor = new Sensor();
        sensor.setStatus("ACTIVE");


        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is under maintenance");
        }

        readings.add(reading);

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}