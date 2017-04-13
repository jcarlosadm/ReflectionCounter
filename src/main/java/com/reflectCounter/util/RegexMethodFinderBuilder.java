package com.reflectCounter.util;

public final class RegexMethodFinderBuilder {

	private static final String COMMA = "[,]";
	private static final String DOT = "\\.";
	private static final String CLOSE_PAR = "\\)";
	private static final String OPEN_PAR = "\\(";
	private static final String SPACE_LINE_BREAK = "[\\s]*[\\r\\n]*[\\s]*";
	private static final String ID = "[a-zA-Z_][a-zA-Z0-9_]*";
	
	private RegexMethodFinderBuilder() {
	}
	
	public static String build(String methodName, int nArgs) {
		if(methodName == null) methodName = "";
		
		StringBuilder mArgs = new StringBuilder();
		for (int i = 0; i < nArgs; ++i) {
			if (i != 0)
				mArgs.append(SPACE_LINE_BREAK);
			mArgs.append(ID);
			if (i < nArgs - 1)
				mArgs.append(SPACE_LINE_BREAK + COMMA);
		}

		return DOT + SPACE_LINE_BREAK + methodName + SPACE_LINE_BREAK + OPEN_PAR + SPACE_LINE_BREAK + mArgs.toString()
				+ SPACE_LINE_BREAK + CLOSE_PAR;
	}

}
