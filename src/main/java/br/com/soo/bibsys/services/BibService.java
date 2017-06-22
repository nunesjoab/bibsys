package br.com.soo.bibsys.services;

import br.com.soo.bibsys.utils.Formatter;
import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;

@Path("/bib")
public class BibService {

	@POST
	@Path("/format")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response format(@DefaultValue("true") @FormDataParam("enabled") boolean enabled,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {

		final String file = new Formatter().formatFile(IOUtils.toString(uploadedInputStream, Charset.defaultCharset()));

		ContentDisposition contentDisposition = ContentDisposition.type("attachment").fileName("file.bib").creationDate(new Date()).build();

		return Response.ok(new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputStream.write(file.getBytes());
			}
		}).header("Content-Disposition", contentDisposition).build();
	}
}
