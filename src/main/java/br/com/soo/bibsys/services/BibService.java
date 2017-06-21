package br.com.soo.bibsys.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/bib")
public class BibService {

	@GET
	@Path("/")
	public Response hello() {
		return Response.ok("MEU OVO 2").build();
	}
}
