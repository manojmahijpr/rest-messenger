package org.Daemons.messenger.resources;

import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

@Path("/checks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = { MediaType.APPLICATION_JSON , MediaType.TEXT_XML})
public class CheckResource {

	@GET
	public void get(@Context Response response) throws MalformedURLException {
		URL url = new URL("");
		JsonObjectBuilder json = Json.createObjectBuilder().build();
	}
}
