package hadoop.edu.fiipls.core.learner.hadoop.score;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.distributed.CSVToARFFHeaderMapTask;

@SuppressWarnings("deprecation")
public class ScoreModelMapper extends Mapper<Object, Text, Text, IntWritable> {

	Classifier classifier1;

	Classifier classifier2;

	Instances instances;

	Integer attributeToPredict;

	String splitter;
	
	CSVToARFFHeaderMapTask helper;
	
	@Override
	public void setup(Context context) {
		Configuration conf = context.getConfiguration();
		
		classifier1 = new J48();
		classifier2 = new NaiveBayes();
		// attrs.addAll(conf.get("classifier1Path").split(splitter));
		try {
			helper = new CSVToARFFHeaderMapTask();
			Path[] paths = DistributedCache.getLocalCacheFiles(conf);
			splitter = conf.get("valueSplitter");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(paths[0].toUri().getPath()));
			classifier1 = (Classifier) ois.readObject();
			ois.close();
			
			ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream(paths[1].toUri().getPath()));
			classifier2 = (Classifier) ois1.readObject();
			ois1.close();

			BufferedReader reader = new BufferedReader(new FileReader(paths[2].toUri().getPath()));
			instances = new Instances(reader);
			reader.close();
			/*Instance ins = instances.get(0);
			instances.delete();
			instances.add(ins);*/
			// setting class attribute
			attributeToPredict = Integer.parseInt(conf.get("attributeToFind"));
			instances.setClassIndex(attributeToPredict);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void map(Object key, Text value, Context output) throws IOException, InterruptedException {
		try{
		String[] str = value.toString().split(splitter);
		Instance ins = null;
		try {
			ins = helper.makeInstance(instances, true, str);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*for (int i = 0; i < instances.numAttributes(); i++) {
			if (str[i].length() > 1) {
				ins.setValue(i, str[i]);
			} else {
				ins.setMissing(i);
			}
		}*/
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

		if (classifier1Value.equalsIgnoreCase(correctValue)) {
			output.getCounter("classifier1Correct", "classifier1Correct").increment(1L);
		}

		if (classifier2Value.equalsIgnoreCase(correctValue)) {
			output.getCounter("classifier2Correct", "classifier2Correct").increment(1L);
		}

		output.write(null, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
