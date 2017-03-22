package edu.fiipls.rest.client;
import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
/**
 * This example shows how to upload files using POST requests 
 * with encoding type "multipart/form-data".
 * For more details please read the full tutorial
 * on https://javatutorial.net/java-file-upload-rest-service
 * @author javatutorial.net
 */
public class FileUploaderClient {
	
	public static void main(String[] args) {
		
		final Client client = ClientBuilder.newBuilder()
		        .register(MultiPartFeature.class)
		        .build();
		    WebTarget t = client.target("http://localhost:8080/rest/").path("upload");

		    FileDataBodyPart filePart = new FileDataBodyPart("file", 
		                                             new File("E:/Job_ISA/history.txt"));
		    filePart.setContentDisposition(
		            FormDataContentDisposition.name("file")
		                                    .fileName("history.txt").build());


		    MultiPart multipartEntity = new FormDataMultiPart()
		            .bodyPart(filePart);

		    Response response = t.request().post(
		            Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA));
		    System.out.println(response.getStatus());
		    System.out.println(response.readEntity(String.class));

		    response.close();
		}
	
}