package dataload;

import inconsistency.InconsistencyPredictor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import knowledge.KnowledgeBean;
import logger.LoggerImp;

import org.apache.log4j.Logger;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class DataLoadFromDB extends LoggerImp {

	Logger logger;

	String myDriver = "org.gjt.mm.mysql.Driver";
	String myUrl = "jdbc:mysql://localhost/machine_learning";

	Connection conn;

	@SuppressWarnings("unchecked")
	public DataLoadFromDB() throws SQLException, ClassNotFoundException {
		logger = getLoggerForThis(InconsistencyPredictor.class);
		Class.forName(myDriver);

	}

	public KnowledgeBean loadDataFromDB(KnowledgeBean bean, int option) {

		CSVLoader loader = new CSVLoader();
		try {

			if (option == 1) {
				// loader.setSource(new File(bean.getCandidatepath()));
				loader.setSource(new File(createNewDataFile(bean, "0",
						bean.getDirectoryPath())));
				Instances originalTrain = loader.getDataSet();
				NumericToNominal convert = new NumericToNominal();
				String[] options = new String[2];
				options[0] = "-R";
				options[1] = "1-14"; // range of variables to make numeric

				try {
					convert.setOptions(options);
					convert.setInputFormat(originalTrain);
					bean.setCurrentData(Filter.useFilter(originalTrain, convert));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (option == 2) {
				bean.setNextCombination(getNextCombination(bean
						.getNextCombination()));
				logger.info("########### combination setted :"
						+ bean.getNextCombination());
				createNewDataFile(bean, bean.getNextCombination(),
						bean.getDirectoryPath());
				/*
				 * loader.setSource(new File(createNewDataFile( bean,
				 * bean.getNextCombination(), bean.getDirectoryPath())));
				 */
			}
			// bean.setOldData(bean.getOldData());
			// bean.setCurrentData(loader.getDataSet());
			setClassAttribute(bean);
			return bean;
		} catch (IOException e) {
			logger.info("Exception in DataLoader\n" + e.getMessage());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;

	}

	/*
	 * Method to get the next possible combination to the re-learner
	 */
	public String getNextCombination(String value) {
		String check;
		for (int i = Integer.parseInt(value) + 1; i <= 32766; i++) {
			check = String.format("%16s", Integer.toBinaryString(i)).replace(
					" ", "0");
			logger.info("######" + check);
			if ((check.charAt(0) == '0') && (check.charAt(15) == '0')) {
				value = "" + i;
				break;
			}
		}
		return value;
	}

	public boolean isAddable(int combination, int position) {
		boolean flag = false;
		String binaryString = String.format("%16s",
				Integer.toBinaryString(combination)).replace(" ", "0");
		if (binaryString.charAt(position) == '1') {
			flag = true;
		}
		return flag;
	}

	public int calculateNoOfAttributes(int combination) {
		int size = 0;
		String binaryString = String.format("%16s",
				Integer.toBinaryString(combination)).replace(" ", "0");
		for (int i = 0; i < binaryString.length(); i++) {
			if (binaryString.charAt(i) == '1') {
				size++;
			}
		}
		return size;
	}

	public void setClassAttribute(KnowledgeBean bean) {

		for (int i = 0; i < bean.getCurrentData().numInstances(); i++) {
			if (bean.getCurrentData().attribute(i).name()
					.equalsIgnoreCase(bean.getToBePredictAttribute())) {
				bean.getCurrentData().setClassIndex(i);
				break;
			}
		}

	}

	public String createNewDataFile(KnowledgeBean bean, String combination,
			String directoryPath) throws IOException, NumberFormatException,
			SQLException {
		conn = DriverManager.getConnection(myUrl, "root", "");
		Statement s = conn.createStatement();
		String filePath;
		String candidate = "FECID,NAME,PARTY1,PARTY2,ICO,STATUS,STREET1,STREET2,CITY,STATE,ZIP,COMID,ELECYEAR,DISTRICT";
		String committe = "COMMID,COMMFECID,COMMNAME,COMMTRESNAME,COMMSTREET1,COMMSTREET2,COMMCITY,COMMSTATE,COMMZIP,COMMDESIGNATION,COMMTYPE,COMMPARTY,COMMFREQUENCY,COMMINTERESTCAT,COMMCONNECTEDORG,COMMCANDID";
		String[] splitCommittee = committe.split(",");
		String allowed = getAllowedColls(combination, splitCommittee);

		filePath = createFileWithDataset(
				s.executeQuery(createQuery(candidate, allowed)), directoryPath
						+ combination + ".csv",
				(14 + calculateNoOfAttributes(Integer.parseInt(combination))),
				candidate + allowed);
		conn.close();

		return filePath;
	}

	public String createQuery(String candidate, String committe) {

		String query = "SELECT "
				+ candidate
				+ " "
				+ committe
				+ " FROM candidates,committees where COMID=COMMFECID order by commid";
		logger.info(">>>>>>>>>" + query);
		return query;
	}

	public String getAllowedColls(String combination, String[] splitCommittee) {

		StringBuffer sb = new StringBuffer();

		// sb.append(candidate);
		int i = 0;
		for (String comite : splitCommittee) {
			if (isAddable(Integer.parseInt(combination), i)) {
				sb.append(",");
				sb.append(comite);
			}
			i++;
		}
		return sb.toString();
	}

	public String createFileWithDataset(ResultSet rs, String path, int noOfCol,
			String colName) throws SQLException, IOException {

		StringBuffer stringCreator = new StringBuffer();
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
		bw.append(colName + "\n");
		while (rs.next()) {
			stringCreator = new StringBuffer();
			stringCreator.append(rs.getString(1));
			for (int i = 2; i <= noOfCol; i++) {
				stringCreator.append(",");
				stringCreator.append(rs.getString(i));
			}
			stringCreator.append("\n");
			bw.append(stringCreator.toString());
			bw.flush();
		}
		bw.close();

		logger.info("Path--->" + path);
		return path;
	}

}
