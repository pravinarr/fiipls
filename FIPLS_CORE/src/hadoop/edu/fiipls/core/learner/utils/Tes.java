package hadoop.edu.fiipls.core.learner.utils;

import org.apache.commons.lang.StringUtils;

public class Tes {

	public static String binaryFromNumber(long decimalNumber) {
		String binaryNumber = "";
		if (decimalNumber <= 0){
			binaryNumber = "0";
		}
		else {

			while (decimalNumber != 0) {
				binaryNumber = (decimalNumber % 2) + binaryNumber;
				decimalNumber /= 2;
			}
		}
		return binaryNumber;
	}

	public static long integerfrmbinary(String str) {
		long j = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '1') {
				j = (long) (j + Math.pow(2, str.length() - 1 - i));
			}

		}
		return j;
	}

	public static void main(String[] args) throws Exception {

		System.out.println(StringUtils.leftPad(binaryFromNumber(integerfrmbinary("111111111111101") - 2), Integer.parseInt("15"), '0'));

		/*
		 * String s ="\"";
		 * 
		 * System.out.println(s.replaceAll("\"", ","));
		 * 
		 * String[] option = weka.core.Utils.splitOptions(" -N 1-69");
		 * System.out.println(Utils.getOption('N', option));
		 * 
		 * Range n = new Range(); n.setRanges("1-69"); n.setUpper(69);
		 * System.out.println(n.isInRange(5));
		 * 
		 * 
		 * String s2 =
		 * "H0AZ03321|PARKER, VERNON|REP|2012|AZ|H|09|C|C|C00541508|5635 E  LINCOLN DRIVE|# 18|PARADISE VALLEY|AZ|852534121|C00529867|YOUNG GUNS 2012 ROUND 4|LISA LISKER|228 S WASHINGTON ST STE 115||ALEXANDRIA|VA|22314|J|N||Q||NRCC|N|Q3|G|12954427983|24K|CCM|VERNON PARKER FOR CONGRESS|PARADISE VALLEY|AZ|85253|||09272012|4568|C00474916|SB22.4127|818709|||4110220121169129923|N|30G||12941449233|15|IND|LEFRAK, HARRISON TUCKER|NEW YORK|NY|10019|SELF|BUSINESS EXECUTIVE|10262012|10000||SA11AI.4136|840994|||4122120121176913907"
		 * ; s2 = s2.replaceAll(",", "*"); s2 = s2.replaceAll(",", "*");
		 * 
		 * CSVParser m_parser = new CSVParser(',', "\'".charAt(1), '\\');
		 */

		// fields = m_parser.parseLine(row);

		/*
		 * Runtime rt = Runtime.getRuntime(); Process proc = rt.
		 * exec("/usr/local/hadoop/bin/hadoop fs -getmerge /output1/part-m-* /usr/local/hadoop/inputToTrain.csv"
		 * ); proc.waitFor();
		 */

		/*
		 * String s2 =
		 * "H0AZ03321|PARKER, VERNON|REP|2012|AZ|H|09|C|C|C00541508|5635 E  LINCOLN DRIVE|# 18|PARADISE VALLEY|AZ|852534121|C00529867|YOUNG GUNS 2012 ROUND 4|LISA LISKER|228 S WASHINGTON ST STE 115||ALEXANDRIA|VA|22314|J|N||Q||NRCC|N|Q3|G|12954427983|24K|CCM|VERNON PARKER FOR CONGRESS|PARADISE VALLEY|AZ|85253|||09272012|4568|C00474916|SB22.4127|818709|||4110220121169129923|N|30G||12941449233|15|IND|LEFRAK, HARRISON TUCKER|NEW YORK|NY|10019|SELF|BUSINESS EXECUTIVE|10262012|10000||SA11AI.4136|840994|||4122120121176913907"
		 * ; String nextCombination =
		 * "111111111111111111111111111111111111111111111111111111111111111111111";
		 * String jvalue = s2.toString().replaceAll(",", "*"); String[] words =
		 * jvalue.toString().split("\\|");
		 * //System.out.println("---->No of split ---->"+words.length); int i
		 * =0; StringBuffer sb = new StringBuffer(); for(String s : words){ if
		 * (nextCombination.charAt(i) == '1') {
		 * if(sb.toString().equalsIgnoreCase("")){ sb.append(s); }else{
		 * sb.append(","); sb.append(s); } } i++; }
		 * System.out.println(sb.toString());
		 */
		/*
		 * String[] ss = s.split("\\|");
		 * 
		 * System.out.println(ss.length+"\n"); for(String sd: ss){
		 * System.out.print("1"); }
		 */
		/*
		 * BufferedReader reader = new BufferedReader(new FileReader(new
		 * File("/home/hduser/Desktop/datasetLatest.csv"))); int no = 0; String
		 * s1 = ""; String jvalue =""; while((jvalue = reader.readLine()) !=
		 * null){ no++; jvalue = jvalue.toString().replaceAll(",", "*");
		 * String[] words = jvalue.toString().split("\\|");
		 * System.out.println(words.length); if(words.length != 69){
		 * System.out.println(no); }
		 * 
		 * }
		 */
		// System.out.println(no);
		// System.out.println(s1);

	}

}
