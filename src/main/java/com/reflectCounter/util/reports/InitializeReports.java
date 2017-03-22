package com.reflectCounter.util.reports;

public abstract class InitializeReports {
	
	private InitializeReports() {
	}
	
	public static void initialize() throws Exception {
		GeneralErrors.getInstance();
		MethodCounter.getInstance();
		MethodErrors.getInstance();
		ProjectErrorReport.getInstance();
	}
}
