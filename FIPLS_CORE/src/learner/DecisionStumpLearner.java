package learner;

import java.io.File;

import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class DecisionStumpLearner {

	public static void main(String[] args) throws Exception {
		/*BufferedReader reader = new BufferedReader(new FileReader(
				CSV2Arff.getArffFile("C:/Users/Ashok/Desktop/test.csv", 1)));
		BufferedReader readerTest = new BufferedReader(new FileReader(
				CSV2Arff.getArffFile("C:/Users/Ashok/Desktop/test.csv", 2)));*/
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/CandidateCleaned2.csv"));
		Instances data = loader.getDataSet();
		Instances testdata = data;
		//reader.close();
		// setting class attribute
		
		
		int i =0;
		for (i=0;i<data.numAttributes();i++) {
			if(data.attribute(i).name().trim().equalsIgnoreCase("ICO")){
				data.setClass(data.attribute(i));
				testdata.setClass(data.attribute(i));
			}
		}
		
		System.out.println("class index"+data.toSummaryString());
		NumericToNominal converter = new NumericToNominal();
		converter.setInputFormat(data);
		Instances newData = Filter.useFilter(data, converter);
		
		
		/*NaiveBayes dt = new NaiveBayes();
		dt.buildClassifier(data);
	    System.out.println("The NBC result is****");
	    System.out.println(dt.toString());
		*//*for(i=0; i<14; i=i+1)
	        {
	            System.out.println("Nominal? "+newData.attribute(i).name());
	        }*/
		
		
		/*String[] options = new String[1];
		options[0] = "-C 1.0 –M 5"; // confidenceFactor = 1.0, minNumObject = 5
		J48 tree = new J48(); // new instance of tree
		tree.setOptions(options); // set the options
		tree.buildClassifier(data);
		*/
		DecisionStump tree = new DecisionStump();
		//tree.setOptions(options)
		
	//	tree.buildClassifier(data);
		//System.out.println(tree.toString());
		
		/*Evaluation evaluation = new Evaluation(data);
		evaluation.evaluateModel(tree,testdata);
		*///System.out.println(evaluation.toSummaryString());
		J48 dt = new J48();
		dt.buildClassifier(data);
	    System.out.println("The J48 result is****");
	    System.out.println(dt.toString());
	    //System.out.println(dt.toString());
	    /*List<Double> list = new ArrayList<Double>();
	    for(int iVal=1;iVal<data.numInstances();iVal++){
	    	if(!list.contains(dt.classifyInstance(data.instance(iVal)))){
	    		System.out.println(dt.classifyInstance(data.instance(iVal)));
	    		list.add(dt.classifyInstance(data.instance(iVal)));
	    	}*/

	   // }
	}

}
