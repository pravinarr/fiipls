package hadoop.edu.fiipls.core.learner.hadoop;

import java.util.Enumeration;

import weka.core.Option;
import weka.distributed.hadoop.WekaClassifierHadoopJob;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String s="H0CO04122|'GARDNER, CORY'|REP|2014|CO|H|4|I|C|C00492454|'9227 E. LINCOLN AVE., #200-234'|?|'LONE TREE'|CO|80124|C00492454|'GARDNER FOR CONGRESS 2012'|'RAY MARTINEZ'|'9227 E. LINCOLN AVE., #200-235'|?|'LONE TREE'|CO|801245504|P|H|REP|Q|?|?|N|Q3|P|11952582097|24K|CCM|'GARDNER FOR CONGRESS'|LOVELAND|CO|80539|?|?|8162011|12144|C00461749|BB445DEC685C142C8A10|748188|?|?|4102120111144056300|A|30G|G|14021361410|15|IND|'HARRIS, BOBBYE F'|CALHOUN|GA|307012055|RETIRED|RETIRED|10302014|250|?|SA0202151410772|992066|?|?|1042020150017361020";
		String ss = "a|b|c|d";
		StringTokenizer st= new StringTokenizer("|");
		String[] sl = ss.split("\\|");
		System.out.println(sl.length);*/
		
		WekaClassifierHadoopJob job = new WekaClassifierHadoopJob();
		System.out.println(job.listOptions());
		int i = 1;
		Enumeration<Option> options = job.listOptions(); 
		while(options.hasMoreElements()){
			Option o = options.nextElement();
			System.out.println(i+":  "+o.description()+"\n");
			i++;
		}

	}

}
