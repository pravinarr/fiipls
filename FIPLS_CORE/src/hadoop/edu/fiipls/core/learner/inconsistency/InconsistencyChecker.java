package hadoop.edu.fiipls.core.learner.inconsistency;

import hadoop.edu.fiipls.core.learner.beans.DataSetBean;
import hadoop.edu.fiipls.core.learner.beans.ResultBean;

public class InconsistencyChecker {

	public boolean isInconsistent(DataSetBean bean, ResultBean result) {
		double best = 100 * (bean.getBestResults().getNonConsistentCases() / bean.getBestResults().getTotalCase());
		double curent = 100
				* (bean.getCurrentResult().getNonConsistentCases() / bean.getCurrentResult().getTotalCase());
		System.out.println("Current inconsistency % :" + curent +" Best is :"+best);
		if (best > curent) {
			bean.setBestResults(bean.getCurrentResult());
			System.out.println("Better knowledge Found and saved");
			bean.setBestResults(result);
			double inconsistency = 100 * (bean.getBestResults().getNonConsistentCases() / result.getTotalCase());
			System.out.println("Current inconsistency % :" + inconsistency);
			if (inconsistency < bean.getAllowedInconsistency()) {
				System.out.println("Best knowledge within allowed inconsistency found");
				return true;
			} else {
				System.out.println("Better knowledge found by non consistent.So continuing learning process");
				return false;
			}
		} else {
			System.out.println("Not a better knowledge. Continuing learning process");
			return false;
		}

	}

}
