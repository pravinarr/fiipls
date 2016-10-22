package edu.fiipls.service.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.fiipls.bean.mapper.JobMapper;
import edu.fiipls.dbImpl.JobDbImpl;
import edu.fiipls.dbImpl.LearningResultsDB;
import edu.fiipls.entities.JobEntity;
import edu.fiipls.entities.LearningResults;
import edu.fiipls.model.ResultsForChart;
import edu.fiipls.model.ResultsOutput;

@Path("/uiResults")
public class ResultsService {

	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResults(@PathParam("param") String jobName) {

		ResultsOutput output = new ResultsOutput();

		JobMapper map = new JobMapper();
		LearningResultsDB db = new LearningResultsDB();

		JobDbImpl impl = new JobDbImpl();
		List<JobEntity> results = impl.get(jobName);
		double totalRows = 0;
		if (results.size() > 0) {
			output.setJobDetails(map.resultMapDBToService(results.get(0)));
			totalRows = results.get(0).getTotalRows();
			output.setJobId(jobName);
		}

		for (LearningResults result : db.getBeat(jobName)) {
			ResultsForChart chart = new ResultsForChart();
			chart.setBestConsistency((result.getConsistent() / totalRows) * 100);
			chart.setClassifier1Accuracy((result.getClassifier1Correct() / totalRows) * 100);
			chart.setClassifier2Accuracy((result.getClassifier2Correct() / totalRows) * 100);
			chart.setConsistency((result.getConsistent() / totalRows) * 100);
			chart.setId("" + result.getId());
			chart.setNoOfRows(result.getColumns().split(",").length);
			output.setBestClassifier(chart);
		}
		
		
		ResultsForChart ch = new ResultsForChart();
		ch.setBestConsistency(0);
		ch.setClassifier1Accuracy(0);
		ch.setClassifier2Accuracy(0);
		ch.setConsistency(0);
		ch.setId("0");
		ch.setNoOfRows(0);
		output.getLearningProgress().add(ch);
		double bestConsis = 0;
		for (LearningResults result : db.get(jobName)) {
			ResultsForChart chart = new ResultsForChart();
			double consist = (result.getConsistent() / totalRows) * 100;
			if(bestConsis < consist){
				chart.setBestConsistency(consist);
				bestConsis = consist;
			}else{
				chart.setBestConsistency(bestConsis);
			}
			
			chart.setClassifier1Accuracy((result.getClassifier1Correct() / totalRows) * 100);
			chart.setClassifier2Accuracy((result.getClassifier2Correct() / totalRows) * 100);
			chart.setConsistency((result.getConsistent() / totalRows) * 100);
			chart.setId("" + result.getId());
			chart.setNoOfRows(result.getColumns().split(",").length);
			output.getLearningProgress().add(chart);

		}
		return Response.status(200).entity(output).build();
	}

}
