package learner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;
import arffCreator.CSV2Arff;
 
public class WekaTest {
	public static BufferedReader readDataFile(String filename) throws Exception {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(
					CSV2Arff.getArffFile("C:/Users/Ashok/Desktop/test.csv", 1)));
			
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static Evaluation classify(Classifier model,
			Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
	
		
		model.buildClassifier(trainingSet);
		System.out.println("*****"+model.classifyInstance(trainingSet.instance(1)));
		//System.out.println(model.toString());
		//System.out.println(trainingSet.toSummaryString());
		evaluation.evaluateModel(model, testingSet);
 
		return evaluation;
	}
 
	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
 
		for (int i = 0; i < predictions.size(); i++) {
			//System.out.println("predicted result is"+predictions.elementAt(i).toString());
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			//System.out.println("predicted : "+np. +"and actual  "+np.actual()); 
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
 
		return 100 * correct / predictions.size();
	}
 
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
 
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
 
		return split;
	}
 
	public static void main(String[] args) throws Exception {
		BufferedReader datafile = readDataFile("C:/Users/Ashok/Desktop/test.csv");
 
		Instances data = new Instances(datafile);
		data.setClassIndex(5);
		data.setClassIndex(5);
		int i =0;
		for (i=0;i<data.numAttributes();i++) {
			if(data.attribute(i).name().trim().equalsIgnoreCase("ICO")){
				data.setClass(data.attribute(i));
				System.out.println("***********"+data.attribute(i).value(0));
				System.out.println("***********"+data.attribute(i).value(1));
				System.out.println("***********"+data.attribute(i).value(2));
			}
		}
		/*
		data.attribute(i).value(0);
		
//		data.setClassIndex(data.numAttributes() - 1);
 
		// Do 10-split cross validation
		Instances[][] split = crossValidationSplit(data, 10);
 
		// Separate split into training and testing arrays
		Instances[] trainingSplits = split[0];
		Instances[] testingSplits = split[1];
 
		// Use a set of classifiers
		Classifier[] models = { 
				new DecisionStump(), //one-level decision tree
				new J48()
		};
 
		Classifier[] models = { 
				new J48(), // a decision tree
				new PART(), 
				new DecisionTable(),//decision table majority classifier
				new DecisionStump() //one-level decision tree
		};
		// Run for each model
		for (int j = 0; j < models.length; j++) {
 
			// Collect every group of predictions for current model in a FastVector
			FastVector predictions = new FastVector();
 
			// For each training-testing split pair, train and test the classifier
			for (i = 0; i < trainingSplits.length; i++) {
				Evaluation validation = classify(models[j], trainingSplits[i], testingSplits[i]);
 
				predictions.appendElements(validation.predictions());
 
				// Uncomment to see the summary for each training-testing pair.
				//System.out.println(models[j].toString());
			}
 
			// Calculate overall accuracy of current classifier on all splits
			double accuracy = calculateAccuracy(predictions);
 
			// Print current classifier's name and accuracy in a complicated,
			// but nice-looking way.
			System.out.println("Accuracy of " + models[j].getClass().getSimpleName() + ": "
					+ String.format("%.2f%%", accuracy)
					+ "\n---------------------------------");
		}
*/ 
	}
}