package br.com.soo.bibsys.services;

import br.com.soo.bibsys.utils.FileOperator;
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
import java.util.List;
import java.util.Map;

@Path("/bib")
public class BibService {

	@POST
	@Path("/format")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response format(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {

		final String file = new Formatter().formatFile(IOUtils.toString(uploadedInputStream, Charset.defaultCharset()));

		ContentDisposition contentDisposition = ContentDisposition.type("attachment").fileName("file.bib").creationDate(new Date()).build();

		return Response.ok(new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputStream.write(file.getBytes());
			}
		}).header("Content-Disposition", contentDisposition).build();
	}

	@POST
	@Path("/join")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response join(@FormDataParam("file1") InputStream uploadedInputStream1,
			@FormDataParam("file1") FormDataContentDisposition fileDetail1,
			@FormDataParam("file2") InputStream uploadedInputStream2,
			@FormDataParam("file2") FormDataContentDisposition fileDetail2) throws IOException {

		String file1 = new Formatter().formatFile(IOUtils.toString(uploadedInputStream1, Charset.defaultCharset()));
		String file2 = new Formatter().formatFile(IOUtils.toString(uploadedInputStream2, Charset.defaultCharset()));

		final String file = new FileOperator().joinFiles(file1, file2);

		ContentDisposition contentDisposition = ContentDisposition.type("attachment").fileName("file.bib").creationDate(new Date()).build();

		return Response.ok(new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputStream.write(file.getBytes());
			}
		}).header("Content-Disposition", contentDisposition).build();
	}

	@POST
	@Path("/order")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response order(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {

		String file = new Formatter().formatFile(IOUtils.toString(uploadedInputStream, Charset.defaultCharset()));

		FileOperator fileOperator = new FileOperator();

		List<Map<String, String>> maps = fileOperator.fileToMap(file);
		maps = fileOperator.orderFile(maps);

		final String output = fileOperator.mapToFile(maps);

		ContentDisposition contentDisposition = ContentDisposition.type("attachment").fileName("file.bib").creationDate(new Date()).build();

		return Response.ok(new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputStream.write(output.getBytes());
			}
		}).header("Content-Disposition", contentDisposition).build();
	}
}
