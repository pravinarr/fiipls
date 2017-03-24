package hadoop.edu.fiipls.core.learner.relearner;

import java.util.List;

import edu.fiipls.model.Newrecords;
import edu.fiipls.rest.client.PostResults;
import hadoop.edu.fiipls.core.learner.beans.DataSetBean;
import hadoop.edu.fiipls.core.learner.beans.ResultBean;
import hadoop.edu.fiipls.core.learner.controller.LearnerController;
import hadoop.edu.fiipls.core.learner.inconsistency.InconsistencyChecker;
import hadoop.edu.fiipls.core.learner.utils.UtilFunctions;

public class RelearningTrigger {

	public void triggerLearning(DataSetBean bean) throws Exception {

		LearnerController controller = new LearnerController();
		controller.startLearning(bean);

		PostResults re = new PostResults();
		while (true) {
			List<Newrecords> res = re.get();
			if (!res.isEmpty()) {
				UtilFunctions.appendDataToFile(bean, res);
				controller.prelimStepsForRelearning(bean);
				ResultBean b = controller.scoreTheLearntModel(bean);
				double inconsistency = 100
						* (bean.getBestResults().getNonConsistentCases() / bean.getCurrentResult().getTotalCase());
				if (inconsistency < bean.getAllowedInconsistency()) {
					continue;
				} else {
					InconsistencyChecker checker = new InconsistencyChecker();
					while (!checker.isInconsistent(bean, b)) {
						System.out.println("Relearning....");
						controller.relearner(bean);
						System.out.println("Scoring....");
						b = controller.scoreTheLearntModel(bean);
						controller.postResults(b, bean);
						System.out.println("Checking for Inconsistency....");
					}
				}
			} else {
				Thread.sleep(10000);
			}
		}
	}
}
