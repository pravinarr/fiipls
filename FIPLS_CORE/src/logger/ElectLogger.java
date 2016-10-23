package logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ElectLogger {

	static Logger logger = Logger.getLogger(ElectLogger.class);

	public static void main(String[] args) throws IOException {
		/*String log4jConfigFile = System.getProperty("user.dir")
				+ File.separator + "log4j.xml";
		System.out.println(log4jConfigFile);
		DOMConfigurator.configure(log4jConfigFile);
		logger.debug("this is a debug log message");
		logger.error("haiadsada");
		logger.info("this is a information log message");
		logger.warn("this is a warning log message");*/
		//Map<String,boolean>
		System.out.println(Integer.toBinaryString(20));
		System.out.println("");
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/CandidateCleaned2.csv"));
	
		
		BufferedReader br1 = new BufferedReader(new FileReader("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/CommitteCleaned.csv"));
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/CandidateCleaned3.csv")));
	//	bw.append("ID, FECID, NAME, TRESNAME, STREET1, STREET2, CITY, STATE, ZIP, DESIGNATION, TYPE, PARTY, FREQUENCY, INTERESTCAT, CONNECTEDORG, CANDID\n");
		
		String test,test1;
		String[] split;
		//br.readLine();
		int i=1,j=0;
		
		List<String> list1 = new ArrayList<String>();
		bw.append(br.readLine()+"\n");
		bw.flush();
		
		while((test=br1.readLine()) != null){
			split = test.split(",");
			list1.add(split[15]);
			System.out.println("Row:"+i);
			i++;
		}
		i=0;
		while((test=br.readLine()) != null){
			i++;
			split = test.split(",");
			if(list1.contains(split[0])){
				
				bw.append(test);
				bw.append("\n");
				bw.flush();
				j++;
			}
			System.out.println("For i="+i+"and j ="+j);
			
		}
		
		
		
		
		/*while((test=br1.readLine()) != null){
			split = test.split(",");
			bw.append(test.replace(split[0]+",", ""));
			bw.append("\n");
			bw.flush();
		}*/
		bw.close();
		//List<String> list2 = new ArrayList<String>();
		//br1.readLine();
		/*while((test=br.readLine()) != null){
			split = test.split(",");
			list1.add(split[12]);
		}*/
		/*while((test1=br1.readLine()) != null){
			split = test1.split(",");
			list2.add(split[1]);
		}*/
		/*br = new BufferedReader(new FileReader("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/CandidateCleaned.csv"));
		br1.readLine();
		
		while((test=br1.readLine()) != null){
			i++;
			split = test.split(",");
			if(list1.contains(split[1])){
				
				bw.append(test.replace(","+split[15],"" ));
				bw.append("\n");
				bw.flush();
				j++;
			}
			System.out.println("For i="+i+"and j ="+j);
			
		}
		*/
		/*while((test=br.readLine()) != null){
			i++;
			split = test.split(",");
			if(list2.contains(split[12])){
				bw.append(test);
				bw.append("\n");
				bw.flush();
				j++;
			}
			System.out.println("For i="+i+"and j ="+j);
			
		}*/
		bw.close();
		
		/*while((test=br.readLine()) != null){
			System.out.println("For i="+i+"and j ="+j);
			i++;
			split = test.split(",");
			br1 = new BufferedReader(new FileReader("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/committees.csv"));
			br1.readLine();
			while((test1=br1.readLine()) != null){
				
				if(test1.split(",")[1].equalsIgnoreCase(split[12])){
					j++;
					break;
				}
				
			}
			
			
			
		}
*/		System.out.println("Total result is i="+i+"  and j is "+j);
	}

}