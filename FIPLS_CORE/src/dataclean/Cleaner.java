package dataclean;

import java.io.IOException;

public class Cleaner {
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Start of cleaning");
		//CandidateCleaner.clean();
		//CommitteeCleaner.clean();
		ContributorCleaner.clean();
		DonorToCommittee.clean();
		CommitteeToCandidateClean.clean();
		System.out.println("End of cleaning");
	}

}
