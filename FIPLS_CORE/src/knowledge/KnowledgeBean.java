package knowledge;

import inconsistency.Inconsistency;

import java.io.Serializable;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;

@SuppressWarnings("rawtypes")
public class KnowledgeBean  implements Serializable{
	
	/*Logger logger ; 

	@SuppressWarnings("unchecked")
	public KnowledgeBean(){
		logger = getLoggerForThis(KnowledgeBean.class);
	}*/
	
	private J48 currentj48 = new J48();

	private NaiveBayes currentNBC = new NaiveBayes();
	
	private String nextCombination;
	
	private Instances currentData;
	
	private Instances oldData;
	
	//private Evaluation j48evaluation ;
	
	//private Evaluation nbcevaluation ;

	private String candidatepath;
	
	private String committeepath;
	
	private String directoryPath;
	
	private String toBePredictAttribute;
	
	private float inconsisPer;
	
	
/*	private int version;
	
	private Date date;*/
	
	public float getInconsisPer() {
		return inconsisPer;
	}

	public void setInconsisPer(float inconsisPer) {
		this.inconsisPer = inconsisPer;
	}

	public String getToBePredictAttribute() {
		return toBePredictAttribute;
	}

	public void setToBePredictAttribute(String toBePredictAttribute) {
		this.toBePredictAttribute = toBePredictAttribute;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}

	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	public String getCandidatepath() {
		return candidatepath;
	}

	public void setCandidatepath(String candidatepath) {
		this.candidatepath = candidatepath;
	}

	public String getCommitteepath() {
		return committeepath;
	}

	public void setCommitteepath(String committeepath) {
		this.committeepath = committeepath;
	}

/*	public Evaluation getJ48evaluation() {
		return j48evaluation;
	}

	public void setJ48evaluation(Evaluation j48evaluation) {
		this.j48evaluation = j48evaluation;
	}

	public Evaluation getNbcevaluation() {
		return nbcevaluation;
	}

	public void setNbcevaluation(Evaluation nbcevaluation) {
		this.nbcevaluation = nbcevaluation;
	}
*/
	public Instances getCurrentData() {
		return currentData;
	}

	public void setCurrentData(Instances currentData) {
		this.currentData = currentData;
	}

	public Instances getOldData() {
		return oldData;
	}

	public void setOldData(Instances oldData) {
		this.oldData = oldData;
	}

	public J48 getCurrentj48() {
		return currentj48;
	}

	public void setCurrentj48(J48 currentj48) {
		this.currentj48 = currentj48;
	}

	public NaiveBayes getCurrentNBC() {
		return currentNBC;
	}

	public void setCurrentNBC(NaiveBayes currentNBC) {
		this.currentNBC = currentNBC;
	}

	public String getNextCombination() {
		return nextCombination;
	}

	public void setNextCombination(String nextCombination) {
		this.nextCombination = nextCombination;
	}
	
	public float printKnowledgeSummary(KnowledgeBean bean) throws InterruptedException{
		
		Inconsistency incon = new Inconsistency();
		float inconistent = 0;
		float tot = 0;
		for(int i=0;i<getCurrentData().numInstances();i++){
			tot ++;
			if(incon.isPredictedDataIsInconsistent(bean, i,false))
			{
				inconistent++;
			}
		}
		System.out.println("Total instances: "+tot+"and inconsistent :"+inconistent);
		float val = (inconistent/tot)*100;
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("Percentage of inconsistency:"+val);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		
		//logger.info("***********************Knowledge Start***************");
		//logger.info(getCurrentj48().toString());
		//logger.info(getCurrentNBC().toString());
		//logger.info(getJ48evaluation().toSummaryString());
		//logger.info(getNbcevaluation().toSummaryString());
		//logger.info("***********************Knowledge End***************");
		
		return val;
	}
	
	
	
}
