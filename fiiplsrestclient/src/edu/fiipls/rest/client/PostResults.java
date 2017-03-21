package edu.fiipls.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import edu.fiipls.model.LearningResultModel;
import edu.fiipls.rest.client.bean.PostResultsBean;

public class PostResults {
	
	public static  void main(String[] args){
		
		//WebTarget webTarget = client.target("http://3991359b.ngrok.io/fiiplswebservice/rest//").path("first");
		PostResultsBean bean = new PostResultsBean();
		LearningResultModel mo = new LearningResultModel();
		mo.setJobId("first");
		mo.setClassifier1Correct("200");
		mo.setClassifier2Correct("100");
		mo.setColumns("a,b");
		mo.setNonConsistent("200");
		mo.setConsistent("600");
		bean.setResult(mo);
		bean.setPath("save");
		bean.setUrl("http://3991359b.ngrok.io/fiiplswebservice/rest/results/");
		PostResults r = new PostResults();
		System.out.println(r.postNewResult(bean));
		
		
	}
	
	public boolean postNewJob(PostResultsBean bean){
		Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
		WebTarget webTarget = client.target(bean.getUrl()).path(bean.getPath());
		 
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = (Response) invocationBuilder.post(Entity.entity(bean.getJob(), MediaType.APPLICATION_JSON));
		return (response.getStatus()==200)?true:false;
	}
	
	public boolean postNewResult(PostResultsBean bean){
		Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
		WebTarget webTarget = client.target(bean.getUrl()).path(bean.getPath());
		 
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = (Response) invocationBuilder.post(Entity.entity(bean.getResult(), MediaType.APPLICATION_JSON));
		return (response.getStatus()==200)?true:false;
	}

}
