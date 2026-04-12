/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.Resource;

import com.mycompany.smartcampusapi.Model.Sensor;
import com.mycompany.smartcampusapi.Model.Room;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 *
 * @author karandeep Singh Jalf
 */
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {


    private static Map<String, Sensor> sensors = new HashMap<>();

    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {
        if (type == null) {
            return sensors.values();
        }

        List<Sensor> filtered = new ArrayList<>();

        for (Sensor s : sensors.values()) {
            if (s.getType() != null && s.getType().equalsIgnoreCase(type)) {
                filtered.add(s);
            }
        }

        return filtered;
    }

    @POST
    public Response createSensor(Sensor sensor) {

        // ❗ CHECK: room must exist
        Room room = RoomResource.rooms.get(sensor.getRoomId());

        if (room == null) {
            return Response.status(422)
                    .entity("Room does not exist")
                    .build();
        }

        sensors.put(sensor.getId(), sensor);

        // link sensor to room
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") String id) {
        Sensor sensor = sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        return Response.ok(sensor).build();
    }
}