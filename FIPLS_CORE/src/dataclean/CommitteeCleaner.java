package dataclean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CommitteeCleaner {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/committees.csv")));
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/committees.txt"));
		String read;
		System.out.println("committee cleaning process start...");
		//br.readLine();
		bw.append("ID, FECID, NAME, TRESNAME, STREET1, STREET2, CITY, STATE, ZIP, DESIGNATION, TYPE, PARTY, FREQUENCY, INTERESTCAT, CONNECTEDORG, CANDID\n");
		while((read=br.readLine()) != null){
			//System.out.println(read);
			String[] split = read.split("\t");
			int i=0;
			for(String str:split){
				bw.append(str.replace(",", ".").replace("\"", "").trim().replace("\n", "")
						.replace("'", "").replace("%", ""));
				if(i!=15){
					bw.append(",");	
				}
				i++;
			}
					
			bw.append("\n");
		}
		System.out.println("committee cleaning process End...");

	}

}
