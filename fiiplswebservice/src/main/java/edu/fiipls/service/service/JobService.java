package edu.fiipls.service.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.fiipls.bean.mapper.JobMapper;
import edu.fiipls.dbImpl.JobDbImpl;
import edu.fiipls.entities.JobEntity;
import edu.fiipls.model.JobBean;

@Path("/jobs")
public class JobService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResults() {

		List<JobBean> list = new ArrayList<JobBean>();
		JobMapper mapper = new JobMapper();
		JobDbImpl db = new JobDbImpl();
		for (JobEntity result : db.get()) {
			list.add(mapper.resultMapDBToService(result));
		}
		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveResult(JobBean model) {

		JobMapper mapper = new JobMapper();
		JobDbImpl db = new JobDbImpl();
		db.save(mapper.resultMapServiceToDB(model));
		return Response.status(200).build();
	}
}
