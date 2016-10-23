package arffCreator;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSV2Arff {
	/**
	 * takes 2 arguments: - CSV input file - ARFF output file
	 */
	public static String getArffFile(String csvFile, int option)
			throws Exception {
		String outPutFile = null;
		if (option == 1) {
			outPutFile = clean(csvFile);
		} else {
			outPutFile = cleanAllowOnlyMissing(csvFile);
		}
		System.out.println("outfile will be" + outPutFile);

		// load CSV
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(outPutFile));
		Instances data = loader.getDataSet();
		outPutFile = outPutFile.replace(".csv", ".arff");
		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(outPutFile));
		// saver.setDestination(new File(outPutFile));
		saver.writeBatch();
		return outPutFile;
	}

	public static String clean(String csvFile) throws IOException {
		String outFile = "C:/Users/Ashok/Desktop/Candidate.csv";
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(new File(outFile)));
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String read;
		System.out.println("Com2Cand cleaning process start...");

		int j1 = 0;
		int j2 = 0;

		while ((read = br.readLine()) != null) {
			String[] split = read.split(",");

			int i = 0;

			StringBuffer sb = new StringBuffer("");
			if (!(split.length < 5) && !("".equalsIgnoreCase(split[5].trim()))) {
				j1++;
				for (String str : split) {
					sb.append(str.replace(",", ".").replace("\"", "").trim()
							.replace("\n", ""));
					if (i != 14) {
						sb.append(",");
					}
					i++;
				}

				sb.append("\n");
				bw.append(sb.toString());
				bw.flush();
			//	System.out.println(sb.toString());
			} else {
				j2++;
			}
		}
		br.close();
		bw.flush();
		bw.close();
		System.out.println("Com2Cand cleaning process End..." + j1 + "and "
				+ j2);
		return outFile;
	}

	public static String cleanAllowOnlyMissing(String csvFile)
			throws IOException {
		String outFile = "C:/Users/Ashok/Desktop/Candidate2.csv";
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(new File(outFile)));
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String read;
		System.out.println("Com2Cand cleaning process start...");

		int j1 = 0;
		int j2 = 0;
		bw.append("ID, FECID, NAME,PARTY1, PARTY2, ICO, STATUS, STREET1, STREET2, CITY, STATE, ZIP, COMID, ELECYEAR, DISTRICT\n");
		while ((read = br.readLine()) != null) {
			String[] split = read.split(",");

			int i = 0;

			StringBuffer sb = new StringBuffer("");
			if (!(split.length < 5) && ("".equalsIgnoreCase(split[5].trim()))) {
				j1++;
				for (String str : split) {
					sb.append(str.replace(",", ".").replace("\"", "").trim()
							.replace("\n", ""));
					if (i != 14) {
						sb.append(",");
					}
					i++;
				}

				sb.append("\n");
				bw.append(sb.toString());
				bw.flush();
				//System.out.println(sb.toString());
			} else {
				j2++;
			}
		}
		br.close();
		bw.flush();
		bw.close();
		System.out.println("Com2Cand cleaning process End..." + j1 + "and "
				+ j2);
		return outFile;
	}

}
