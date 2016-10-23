package hadoop.edu.fiipls.core.learner.beans;

public class DataSetBean extends BaseJobBean {

	String nextCombination;
	
	public String getNextCombination() {
		return nextCombination;
	}

	public void setNextCombination(String nextCombination) {
		this.nextCombination = nextCombination;
	}

	public DataSetBean(BaseJobBean bean) {
		super(bean);
	}

}
