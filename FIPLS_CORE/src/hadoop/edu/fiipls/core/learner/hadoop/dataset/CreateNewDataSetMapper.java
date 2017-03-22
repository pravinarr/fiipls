package hadoop.edu.fiipls.core.learner.hadoop.dataset;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import au.com.bytecode.opencsv.CSVParser;

public class CreateNewDataSetMapper extends Mapper<Object, Text, Text, IntWritable> {

	@Override
	public void map(Object key, Text value, Context output) throws IOException, InterruptedException {
		try{
		Configuration conf = output.getConfiguration();
		String nextCombination = conf.get("nextCombination");
		String fieldSeparator = conf.get("fieldSeperator");
		//System.out.println("---->"+value.toString());
		// If more than one word is present, split using white space.
		String jvalue = value.toString();
		//String jvalue = value.toString().replaceAll(",", "*");
		//jvalue = jvalue.replaceAll("\"", "");
		//jvalue = jvalue.replaceAll("\'", "");
		//jvalue = jvalue.replaceAll("\\?", "");
		String[] words = jvalue.toString().split(fieldSeparator);
		//System.out.println("---->No of split ---->"+words.length);
		int i =0;
		StringBuffer sb = new StringBuffer();
		for(String s : words){
			if (nextCombination.charAt(i) == '1') {
				if(sb.toString().equalsIgnoreCase("")){
					sb.append(s);
				}else{
					sb.append(",");
					if(s==null || s.equalsIgnoreCase("")){
						sb.append('?');
					}else{
						sb.append(s);	
					}
					
				}
			}
			i++;
		}
	/*	CSVParser m_parser = new CSVParser(',', "\\'".charAt(1), '\\');

	    String[] fields = m_parser.parseLine(sb.toString());
		if(fields.length != 69){
			System.out.println("Not same-->"+sb.toString());
		}*/
		
		// Only the first word is the candidate name
		output.write(new Text(sb.toString()), null);
		}catch(Exception e){
			System.err.println(e);
		}
	}
}
