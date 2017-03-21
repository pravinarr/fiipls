package hadoop.edu.fiipls.core.learner.beans;

public class ResultBean {
	
	boolean result;
	
	int totalCase;
	
	int consistentCases;
	
	int nonConsistentCases;
	
	int classifier1Correct;
	
	int classifier2Correct;
	
	long timeTakenToComplete;
	
	String instanceSummary;
	
	

	
	public String getInstanceSummary() {
		return instanceSummary;
	}

	public void setInstanceSummary(String instanceSummary) {
		this.instanceSummary = instanceSummary;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
	public long getTimeTakenToComplete() {
		return timeTakenToComplete;
	}

	public void setTimeTakenToComplete(long timeTakenToComplete) {
		this.timeTakenToComplete = timeTakenToComplete;
	}

	public int getTotalCase() {
		return totalCase;
	}

	public void setTotalCase(int totalCase) {
		this.totalCase = totalCase;
	}

	public int getConsistentCases() {
		return consistentCases;
	}

	public void setConsistentCases(int consistentCases) {
		this.consistentCases = consistentCases;
	}

	public int getNonConsistentCases() {
		return nonConsistentCases;
	}

	public void setNonConsistentCases(int nonConsistentCases) {
		this.nonConsistentCases = nonConsistentCases;
	}

	public int getClassifier1Correct() {
		return classifier1Correct;
	}

	public void setClassifier1Correct(int classifier1Correct) {
		this.classifier1Correct = classifier1Correct;
	}

	public int getClassifier2Correct() {
		return classifier2Correct;
	}

	public void setClassifier2Correct(int classifier2Correct) {
		this.classifier2Correct = classifier2Correct;
	}

	
}
