package com.reflectCounter.exploreLocalRepo;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

public class FileSearcherTest {

	private static final String METHOD_01 = "get";
	private static final String METHOD_02 = "isEmpty";
	private static final String METHOD_03 = "add";
	private static final String METHOD_04 = "size";

	private static final int N_ARGS_01 = 1;
	private static final int N_ARGS_02 = 0;

	private static final String FILECODE_PATH = "src/test/resources/testCode.java";

	@Test
	public void countMethodFreqTest() throws Exception {
		FileSearcher fS = new FileSearcher(FILECODE_PATH, new ExplorerCounter());

		Method m = FileSearcher.class.getDeclaredMethod("fillFileContent", new Class<?>[] {});
		m.setAccessible(true);
		m.invoke(fS, (Object[]) null);

		Method m2 = FileSearcher.class.getDeclaredMethod("countMethodFreq", new Class<?>[] { String.class, int.class });
		m2.setAccessible(true);

		assertEquals(1, (int) m2.invoke(fS, METHOD_01, N_ARGS_01));
		assertEquals(2, (int) m2.invoke(fS, METHOD_02, N_ARGS_02));
		assertEquals(1, (int) m2.invoke(fS, METHOD_03, N_ARGS_01));
		assertEquals(2, (int) m2.invoke(fS, METHOD_04, N_ARGS_02));
	}

}
