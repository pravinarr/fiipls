package dataclean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


//Committee id, donor id, amount, year,month,day
public class DonorToCommittee {

public static void clean() throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/Don2Com.csv")));
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Don2Com.txt"));
		String read;
		System.out.println("Don2Com cleaning process start...");
		bw.append("Committee id, donor id, Date,amount\n");
		while((read=br.readLine()) != null){
			String[] split = read.split("\t");
			int i=0;
			for(String str:split){
				bw.append(str.replace(",", ".").replace("\"", ""));
				if(i!=3){
					bw.append(",");	
				}
				i++;
			}
					
			bw.append("\n");
		}
		System.out.println("Don2Com cleaning process End...");
	}
	
}
