package hadoop.edu.fiipls.core.learner.hadoop.dataset;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import weka.gui.SysErrLog;

public class CreateNewDataSetReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context output)
			throws IOException, InterruptedException {
		System.out.println("Reducing....");
		output.write(key, null);
	}
}
