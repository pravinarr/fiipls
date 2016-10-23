import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		DataSource source = new DataSource("/home/hduser/Downloads/test.csv");
		Instances instances = source.getDataSet();
		Instance ins = instances.get(0);
		instances.delete();
		instances.add(ins);
		System.out.println(instances.toSummaryString());
	}

}
