package dataclean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ContributorCleaner {

	/**
	 * @param args
	 * @throws IOException 
	 * Committee id,name,street,city,state,zipcode,occupation
	 */
	public static void clean() throws IOException {
		
		System.out.println("Contributors cleaning process Start ");
		String[] filenames = {"contributors1","contributors2","contributors3","contributors4"};
		
		for(String filename:filenames){
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/"+filename+".csv")));
			BufferedReader br = new BufferedReader(new FileReader("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/"+filename+".txt"));
			String read;
			System.out.println("Contributors cleaning process start for the file ..."+filename);
			bw.append("id,name,street,city,state,zipcode,occupation\n");
			while((read=br.readLine()) != null){
				String[] split = read.split("\t");
				int i=0;
				for(String str:split){
					bw.append(str.replace(",", ".").replace("\"", ""));
					if(i!=5){
						bw.append(",");	
					}
					i++;
				}
						
				bw.append("\n");
			}	
			System.out.println("Contributors cleaning process end for the file ..."+filename);
		}
		
		System.out.println("Contributors cleaning process End ");

	}

}
