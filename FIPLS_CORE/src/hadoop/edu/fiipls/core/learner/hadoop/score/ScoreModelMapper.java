package hadoop.edu.fiipls.core.learner.hadoop.score;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;

public class ScoreModelMapper extends Mapper<Object, Text, Text, IntWritable> {

	AbstractClassifier classifier1;

	AbstractClassifier classifier2;

	Instances instances;

	Integer attributeToPredict;

	String splitter;

	@Override
	public void setup(Context context) {
		Configuration conf = context.getConfiguration();
		classifier1 = new J48();
		classifier2 = new NaiveBayes();
		// attrs.addAll(conf.get("classifier1Path").split(splitter));
		try {
			splitter = conf.get("valueSplitter");
			classifier1.setOptions(weka.core.Utils.splitOptions("-l "+conf.get("classifier1Path")));
			classifier2.setOptions(weka.core.Utils.splitOptions("-l "+conf.get("classifier2Path")));
			BufferedReader reader = new BufferedReader(new FileReader(conf.get("headerFile")));
			instances = new Instances(reader);
			reader.close();
			Instance ins = instances.get(0);
			instances.delete();
			instances.add(ins);
			// setting class attribute
			attributeToPredict = Integer.parseInt(conf.get("attributeToFind"));
			instances.setClassIndex(attributeToPredict);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void map(Object key, Text value, Context output) throws IOException, InterruptedException {
		Instance ins = instances.get(0);
		String[] str = value.toString().split(splitter);
		for (int i = 0; i < instances.numAttributes(); i++) {
			if (str[i].length() > 1) {
				ins.setValue(i, str[i]);
			} else {
				ins.setMissing(i);
			}
		}
		String correctValue = ins.stringValue(attributeToPredict);
		String classifier1Value = null;
		String classifier2Value = null;
		try {
			classifier1Value = ins.attribute(attributeToPredict).value((int) classifier1.classifyInstance(ins));
			classifier2Value = ins.attribute(attributeToPredict).value((int) classifier2.classifyInstance(ins));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		output.getCounter("totalCases", "totalCases").increment(1L);
		if (classifier1Value.equalsIgnoreCase(classifier2Value)) {
			output.getCounter("consistentCounter", "consistentCounter").increment(1L);
		} else {
			output.getCounter("notConsistentCounter", "notConsistentCounter").increment(1L);
		}
		
		if(classifier1Value.equalsIgnoreCase(correctValue)){
			output.getCounter("classifier1Correct", "classifier1Correct").increment(1L);
		}
		
		if(classifier2Value.equalsIgnoreCase(correctValue)){
			output.getCounter("classifier2Correct", "classifier2Correct").increment(1L);
		}
		

		output.write(null, null);
	}
}
