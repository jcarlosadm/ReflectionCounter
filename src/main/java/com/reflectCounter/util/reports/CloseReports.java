package com.reflectCounter.util.reports;

public abstract class CloseReports {
	
	private CloseReports() {
	}
	
	public static void close() throws Exception {
		GeneralErrors.getInstance().close();
		MethodCounter.getInstance().close();
		MethodErrors.getInstance().close();
		ProjectErrorReport.getInstance().close();
	}
}
