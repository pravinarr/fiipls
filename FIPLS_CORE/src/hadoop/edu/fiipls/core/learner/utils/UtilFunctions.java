package hadoop.edu.fiipls.core.learner.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import edu.fiipls.model.Newrecords;
import hadoop.edu.fiipls.core.learner.beans.DataSetBean;

public class UtilFunctions {

	public static void main(String[] args) {
		DataSetBean bean = new DataSetBean();
		bean.setColumnInfo("a,b,c,d,e");
		Newrecords record = new Newrecords();
		record.setColumn("a,b,c");
		record.setValues("1,2,3");

		System.out.println(UtilFunctions.formNewRecord(bean, record));
	}

	public static String formNewRecord(DataSetBean bean, Newrecords record) {

		String val = "";
		String column = bean.getColumnInfo();
		String actualCol = record.getColumn();

		String[] cols = column.split(",");
		String[] aCols = actualCol.split(",");
		String[] values = record.getValues().split(",");

		int i = 0, j = 0;

		for (; i < cols.length; i++) {
			if (!(j >= aCols.length)) {
				if (aCols[j].equalsIgnoreCase(cols[i])) {
					if (val.equalsIgnoreCase("")) {
						val = val + values[j];
					} else {
						val += "," + values[j];
					}

					j++;
				} else {
					val = val + ",";
				}
			} else {
				val = val + ",";
			}
		}
		return val;
	}

	public static void appendDataToFile(DataSetBean bean, List<Newrecords> records) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String data = "";
			for(Newrecords rec : records){
				data += formNewRecord(bean,rec) +"\n";
			}

			File file = new File(bean.getLocalTestFilePath());
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String binaryFromNumber(long decimalNumber) {
		String binaryNumber = "";
		if (decimalNumber <= 0) {
			binaryNumber = "0";
		} else {

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
