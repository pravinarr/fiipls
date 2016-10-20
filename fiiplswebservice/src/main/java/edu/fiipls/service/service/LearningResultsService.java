package edu.fiipls.service.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.fiipls.bean.mapper.ResultMapper;
import edu.fiipls.dbImpl.LearningResultsDB;
import edu.fiipls.entities.LearningResults;
import edu.fiipls.model.LearningResultModel;

@Path("/results")
public class LearningResultsService {

	@GET
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getResults() {

		List<LearningResultModel> list = new ArrayList<LearningResultModel>();
		ResultMapper mapper = new ResultMapper();
		LearningResultsDB db = new LearningResultsDB();
		for (LearningResults result : db.get()) {
			list.add(mapper.resultMapDBToService(result));
		}
		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveResult(LearningResultModel model) {

		ResultMapper mapper = new ResultMapper();
		LearningResultsDB db = new LearningResultsDB();
		db.save(mapper.resultMapServiceToDB(model));
		return Response.status(200).build();
	}
}
