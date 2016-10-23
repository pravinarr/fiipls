package hadoop.edu.fiipls.core.learner.beans;

public class BaseJobBean {
	
	String hdfsPath;
	
	String yarnPath;
	
	String inputPath;
	
	String outputPath;
	
	String inputSplit;
	
	String headerFile;
	
	String attributeToFind;
	
	public BaseJobBean(BaseJobBean bean){
		this.hdfsPath = bean.getHdfsPath();
		this.yarnPath = bean.getYarnPath();
		this.inputPath = bean.getInputPath();
		this.outputPath = bean.getOutputPath();
		this.inputSplit = bean.getInputSplit();
		this.headerFile = bean.getHeaderFile();
		this.attributeToFind = bean.getAttributeToFind();
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
