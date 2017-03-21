package hadoop.edu.fiipls.core.learner.beans;

public class BaseJobBean {

	String hdfsPath;

	String hdfsPort;

	String yarnPath;

	String yarnPort;

	String inputPath;

	String outputPath;

	String inputSplit;

	String headerFile;

	String attributeToFind;

	String classifier1Config;

	String classifier2Config;

	String testpath;

	String columnInfo;

	double noOfRows;
	
	ResultBean bestResults;
	
	double allowedInconsistency;


	public BaseJobBean() {

	}

	public BaseJobBean(BaseJobBean bean) {
		this.hdfsPath = bean.getHdfsPath();
		this.yarnPath = bean.getYarnPath();
		this.inputPath = bean.getInputPath();
		this.outputPath = bean.getOutputPath();
		this.inputSplit = bean.getInputSplit();
		this.headerFile = bean.getHeaderFile();
		this.attributeToFind = bean.getAttributeToFind();
	}
	
	public double getAllowedInconsistency() {
		return allowedInconsistency;
	}

	public void setAllowedInconsistency(double allowedInconsistency) {
		this.allowedInconsistency = allowedInconsistency;
	}

	public ResultBean getBestResults() {
		return bestResults;
	}

	public void setBestResults(ResultBean bestResults) {
		this.bestResults = bestResults;
	}

	public double getNoOfRows() {
		return noOfRows;
	}

	public void setNoOfRows(double noOfRows) {
		this.noOfRows = noOfRows;
	}

	public String getColumnInfo() {
		return columnInfo;
	}

	public void setColumnInfo(String columnInfo) {
		this.columnInfo = columnInfo;
	}

	public String getTestpath() {
		return testpath;
	}

	public void setTestpath(String testpath) {
		this.testpath = testpath;
	}

	public String getClassifier1Config() {
		return classifier1Config;
	}

	public void setClassifier1Config(String classifier1Config) {
		this.classifier1Config = classifier1Config;
	}

	public String getClassifier2Config() {
		return classifier2Config;
	}

	public void setClassifier2Config(String classifier2Config) {
		this.classifier2Config = classifier2Config;
	}

	public String getHdfsPort() {
		return hdfsPort;
	}

	public void setHdfsPort(String hdfsPort) {
		this.hdfsPort = hdfsPort;
	}

	public String getYarnPort() {
		return yarnPort;
	}

	public void setYarnPort(String yarnPort) {
		this.yarnPort = yarnPort;
	}

	public String getAttributeToFind() {
		return attributeToFind;
	}

	public void setAttributeToFind(String attributeToFind) {
		this.attributeToFind = attributeToFind;
	}

	public String getHeaderFile() {
		return headerFile;
	}

	public void setHeaderFile(String headerFile) {
		this.headerFile = headerFile;
	}

	public String getInputSplit() {
		return inputSplit;
	}

	public void setInputSplit(String inputSplit) {
		this.inputSplit = inputSplit;
	}

	public String getHdfsPath() {
		return hdfsPath;
	}

	public void setHdfsPath(String hdfsPath) {
		this.hdfsPath = hdfsPath;
	}

	public String getYarnPath() {
		return yarnPath;
	}

	public void setYarnPath(String yarnPath) {
		this.yarnPath = yarnPath;
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

}
