package com.reflectCounter.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegexMethodFinderBuilderTest {

	private static final String EXPECTED_OUTPUT_01 = "\\.[\\s]*[\\r\\n]*[\\s]*add[\\s]*[\\r\\n]*[\\s]*"
			+ "\\([\\s]*[\\r\\n]*[\\s]*[\\s]*[\\r\\n]*[\\s]*\\)";
	private static final String EXPECTED_OUTPUT_02 = "\\.[\\s]*[\\r\\n]*[\\s]*[\\s]*[\\r\\n]*[\\s]*\\"
			+ "([\\s]*[\\r\\n]*[\\s]*[a-zA-Z_][a-zA-Z0-9_]*[\\s]*[\\r\\n]*[\\s]*[,]"
			+ "[\\s]*[\\r\\n]*[\\s]*[a-zA-Z_][a-zA-Z0-9_]*[\\s]*[\\r\\n]*[\\s]*[,]"
			+ "[\\s]*[\\r\\n]*[\\s]*[a-zA-Z_][a-zA-Z0-9_]*[\\s]*[\\r\\n]*[\\s]*\\)";
	private static final String EXPECTED_OUTPUT_03 = "\\.[\\s]*[\\r\\n]*[\\s]*[\\s]*[\\r\\n]*[\\s]*"
			+ "\\([\\s]*[\\r\\n]*[\\s]*[\\s]*[\\r\\n]*[\\s]*\\)";
	private static final String EXPECTED_OUTPUT_04 = "\\.[\\s]*[\\r\\n]*[\\s]*add[\\s]*[\\r\\n]*[\\s]*"
			+ "\\([\\s]*[\\r\\n]*[\\s]*[\\s]*[\\r\\n]*[\\s]*\\)";

	private static final String METHOD_NAME_01 = "add";
	private static final String METHOD_NAME_02 = "";
	private static final String METHOD_NAME_03 = null;

	private static final int METHOD_N_ARGS_01 = 0;
	private static final int METHOD_N_ARGS_02 = 3;
	private static final int METHOD_N_ARGS_03 = -1;

	@Test
	public void testBuild() throws Exception {
		String output = RegexMethodFinderBuilder.build(METHOD_NAME_01, METHOD_N_ARGS_01);
		assertEquals(EXPECTED_OUTPUT_01, output);
		
		output = RegexMethodFinderBuilder.build(METHOD_NAME_02, METHOD_N_ARGS_02);
		assertEquals(EXPECTED_OUTPUT_02, output);
		
		output = RegexMethodFinderBuilder.build(METHOD_NAME_03, METHOD_N_ARGS_01);
		assertEquals(EXPECTED_OUTPUT_03, output);
		
		output = RegexMethodFinderBuilder.build(METHOD_NAME_01, METHOD_N_ARGS_03);
		assertEquals(EXPECTED_OUTPUT_04, output);
	}

}
