package hadoop.edu.fiipls.core.learner.beans;

public class DataSetBean extends BaseJobBean {

	String nextCombination;
	
	String classifier1Path;
	
	String classifier2Path;
	
	int noOfOnes;
	
	public int getNoOfOnes() {
		return noOfOnes;
	}

	public void setNoOfOnes(int noOfOnes) {
		this.noOfOnes = noOfOnes;
	}

	ResultBean currentResult;
	
	int classIndex;
	
	public int getClassIndex() {
		return classIndex;
	}

	public void setClassIndex(int classIndex) {
		this.classIndex = classIndex;
	}

	public ResultBean getCurrentResult() {
		return currentResult;
	}

	public void setCurrentResult(ResultBean currentResult) {
		this.currentResult = currentResult;
	}

	public String getClassifier1Path() {
		return classifier1Path;
	}

	public void setClassifier1Path(String classifier1Path) {
		this.classifier1Path = classifier1Path;
	}

	public String getClassifier2Path() {
		return classifier2Path;
	}

	public void setClassifier2Path(String classifier2Path) {
		this.classifier2Path = classifier2Path;
	}

	public String getNextCombination() {
		return nextCombination;
	}

	public void setNextCombination(String nextCombination) {
		this.nextCombination = nextCombination;
	}

	public DataSetBean(BaseJobBean bean) {
		super(bean);
	}
	
	public DataSetBean() {
	}

}
