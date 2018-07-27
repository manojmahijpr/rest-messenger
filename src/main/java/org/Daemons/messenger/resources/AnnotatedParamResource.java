package org.Daemons.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Path("/annotatedParam")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class AnnotatedParamResource {

	@GET
	@Path("accessParam")
	public String differentParams(@MatrixParam("matrixParam") String matrixParam,
											@HeaderParam("headerParam") String headerParam,
											@CookieParam("cookieparam") String cookieParam) {
		
		try {
			return "Matrix Param: " + matrixParam +"\nHeader Param: " + headerParam +"\nCookie Param: " + cookieParam;
		}
		catch(NullPointerException npx) {
			return npx.getMessage().toString();
		}
	}
	
	@GET
	@Path("contextParam")
	public String getContextParam(@Context UriInfo uriInfo,
									@Context HttpHeaders httpHeaders) {
		
		try {
			return "URI: " + uriInfo.getAbsolutePath().toString() +"\nHttpHeader Cookie: " + httpHeaders.getCookies().toString();
		}
		catch(NullPointerException npx) {
			return npx.getMessage().toString();
		}
	}
	
}
