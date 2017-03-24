package edu.fiipls.rest.client;

import java.io.File;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

/**
 * This example shows how to upload files using POST requests with encoding type
 * "multipart/form-data". For more details please read the full tutorial on
 * https://javatutorial.net/java-file-upload-rest-service
 * 
 * @author javatutorial.net
 */
public class FileUploaderClient {

	public void upload(String path) {

		final ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(MultiPartWriter.class);
		final Client client = Client.create(config);

		final WebResource resource = client.resource("http://192.168.1.14:8080/fiiplswebservice/rest/").path("upload");

		final File fileToUpload = new File(path);

		final FormDataMultiPart multiPart = new FormDataMultiPart();
		if (fileToUpload != null) {
			multiPart.bodyPart(new FileDataBodyPart("file", fileToUpload, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		}

		final ClientResponse clientResp = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class,
				multiPart);
		System.out.println("Response: " + clientResp.getClientResponseStatus());

		client.destroy();
	}

}