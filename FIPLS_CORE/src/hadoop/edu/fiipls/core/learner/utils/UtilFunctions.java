package hadoop.edu.fiipls.core.learner.utils;

public class UtilFunctions {
	

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
	
}
