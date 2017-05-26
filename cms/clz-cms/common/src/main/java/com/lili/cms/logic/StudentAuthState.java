package com.lili.cms.logic;

import java.util.List;
import java.util.Map;

public class StudentAuthState {
private Map<String, List<String>> studentStateMaps;
	
	public Map<String, List<String>> getStudentStateMaps() {
		return studentStateMaps;
	}

	public void setStudentStateMaps(Map<String, List<String>> studentStateMaps) {
		this.studentStateMaps = studentStateMaps;
	}

	public boolean compareSteps(String currentStep,String reqStep){
		if(studentStateMaps != null){
			List<String> preStepsList = studentStateMaps.get(reqStep);
			if(preStepsList == null || preStepsList.size() <= 0){
				return false;
			}

			if(preStepsList.contains(currentStep)){
				return true;
			}
		}
		return false;
	}
}
