package edu.fiipls.rest.client;

import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import edu.fiipls.model.JobBean;
import edu.fiipls.model.LearningResultModel;
import edu.fiipls.model.Newrecords;
import edu.fiipls.rest.client.bean.PostResultsBean;

public class PostResults {

	private String jobPath = "http://192.168.1.14:8080/fiiplswebservice/rest/jobs/";

	private String resultPath = "http://192.168.1.14:8080/fiiplswebservice/rest/results/";

	private String recordsPath = "http://192.168.1.14:8080/fiiplswebservice/rest/predict/newrecords/toadd";

	private String path = "save";

	public static void main(String[] args) {

		// WebTarget webTarget =
		// client.target("http://3991359b.ngrok.io/fiiplswebservice/rest//").path("first");
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
		bean.setUrl("http://192.168.1.14:8080/fiiplswebservice/rest/predict/");
		JobBean jbean = new JobBean();
		jbean.setClassifier1Model("J48");
		jbean.setClassifier2Model("NaiveBayes");
		jbean.setJobId("first");
		jbean.setTotalRows("10000");
		bean.setJob(jbean);
		PostResults r = new PostResults();
		System.out.println(r.get());

	}

	public boolean postNewJob(PostResultsBean bean) {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResourcePost = client.resource(jobPath + "" + path);
		ClientResponse response = webResourcePost.type("application/json").post(ClientResponse.class, bean.getJob());
		return (response.getStatus() == 200) ? true : false;
	}

	public boolean postNewResult(PostResultsBean bean) {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResourcePost = client.resource(resultPath + "" + path);
		ClientResponse response = webResourcePost.type("application/json").post(ClientResponse.class, bean.getResult());
		return (response.getStatus() == 200) ? true : false;
	}

	public ArrayList<Newrecords> get() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResourcePost = client.resource(recordsPath);
		ClientResponse response = webResourcePost.type("application/json").get(ClientResponse.class);
		return (response.getStatus() == 200) ? response.getEntity(ArrayList.class) : null;
	}

}
