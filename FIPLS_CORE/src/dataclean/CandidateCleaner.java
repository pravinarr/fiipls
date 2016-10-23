package dataclean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CandidateCleaner {
	
	
	public static void main(String[] args) throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/candidates.csv")));
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/candidates.txt"));
		String read;
		System.out.println("Candidate cleaning process start...");
		bw.append("ID, FECID, NAME,PARTY1, PARTY2, ICO, STATUS, STREET1, STREET2, CITY, STATE, ZIP, COMID, ELECYEAR, DISTRICT\n");
		while((read=br.readLine()) != null){
			String[] split = read.split("\t");
			int i=0;
			for(String str:split){
				bw.append(str.replace(",", ".").replace("\"", "").trim().replace("\n", "")
						.replace("'", "").replace("%", ""));
				if(i!=14){
					bw.append(",");	
				}
				i++;
			}
					
			bw.append("\n");
		}
		System.out.println("Candidate cleaning process End...");
	}

}


