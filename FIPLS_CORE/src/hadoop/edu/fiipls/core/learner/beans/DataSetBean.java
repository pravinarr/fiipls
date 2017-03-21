package hadoop.edu.fiipls.core.learner.beans;

public class DataSetBean extends BaseJobBean {

	String nextCombination;
	
	String classifier1Path;
	
	String classifier2Path;
	
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
