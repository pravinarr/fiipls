package edu.fiipls.service.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.fiipls.dbImpl.NewRecordsDBImpl;
import edu.fiipls.entities.NewRecords;
import edu.fiipls.model.Newrecords;
import edu.fiipls.model.PredictionResult;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.distributed.CSVToARFFHeaderMapTask;

@Path("/predict")
public class PredictionService {

	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResults(@PathParam("param") String values) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader("/home/hduser/classifiers/sample.arff"));
			Instances instances = new Instances(reader);
			reader.close();

			Classifier classifier1;

			Classifier classifier2;
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream("/home/hduser/classifiers/Classifier1.model"));
			classifier1 = (Classifier) ois.readObject();
			ois.close();

			ObjectInputStream ois1 = new ObjectInputStream(
					new FileInputStream("/home/hduser/classifiers/Classifier2.model"));
			classifier2 = (Classifier) ois1.readObject();
			ois1.close();
			CSVToARFFHeaderMapTask helper = new CSVToARFFHeaderMapTask();
			String[] str = values.toString().split(",");

			if (instances.numAttributes() != str.length) {
				return Response.status(401).entity("Incalid arguments").build();
			}

			Instance ins = null;
			ins = helper.makeInstance(instances, true, str);
			int indexToFind = 0;
			String correctValue = ins.stringValue(indexToFind);
			String classifier1Value = null;
			String classifier2Value = null;
			classifier1Value = ins.attribute(indexToFind).value((int) classifier1.classifyInstance(ins));
			classifier2Value = ins.attribute(indexToFind).value((int) classifier2.classifyInstance(ins));
			PredictionResult result = new PredictionResult();
			result.setClassifier1Result(classifier1Value);
			result.setClassifier2Result(classifier2Value);
			if (classifier1Value.equalsIgnoreCase(classifier2Value)) {
				result.setIsInconsistent("No");
			} else {
				NewRecords records = new NewRecords();
				String columns = "";
				for (int i = 1; i <= instances.numAttributes(); i++) {
					if (columns.equalsIgnoreCase("")) {
						columns = instances.attribute(i).name();
					} else {
						columns = columns + "," + instances.attribute(i).name();
					}
				}
				records.setColumn(columns);
				records.setValues(values);
				NewRecordsDBImpl db = new NewRecordsDBImpl();
				db.save(records);
				result.setIsInconsistent("Yes");
			}
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/newrecords/toadd")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResultsForRelearning() {
		NewRecordsDBImpl db = new NewRecordsDBImpl();
		List<Newrecords> result = new ArrayList<Newrecords>();
		for (NewRecords rec : db.get()) {
			Newrecords r = new Newrecords();
			r.setColumn(rec.getColumn());
			r.setValues(rec.getValues());
			result.add(r);
		}
		db.deleteAll();
		return Response.status(200).entity(result).build();
	}
}
