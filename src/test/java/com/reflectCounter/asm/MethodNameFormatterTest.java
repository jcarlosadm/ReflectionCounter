package com.reflectCounter.asm;

import static org.junit.Assert.*;

import org.junit.Test;

public class MethodNameFormatterTest {

	private static final String STRING_TO_FORMAT3 = "public static native double java.lang.reflect.Array."
			+ "getDouble(java.lang.Object,int) throws java.lang.IllegalArgumentException,java."
			+ "lang.ArrayIndexOutOfBoundsException";
	private static final String STRING_TO_FORMAT2 = "public void java.lang.reflect.AccessibleObject."
			+ "setAccessible(boolean) throws java.lang.SecurityException";
	private static final String STRING_TO_FORMAT1 = "public java.lang.annotation.Annotation java.lang."
			+ "reflect.AccessibleObject.getAnnotation(java.lang.Class)";
	
	private static final String EXPECTED3 = "double getDouble(java.lang.Object,int)";
	private static final String EXPECTED2 = "void setAccessible(boolean)";
	private static final String EXPECTED1 = "java.lang.annotation.Annotation getAnnotation(java.lang.Class)";
	private static final String EXPECTED_WRONG1 = "java.lang.annotation.Annotation java.lang."
			+ "reflect.AccessibleObject.getAnnotation(java.lang.Class)";

	@Test
	public void testFormat() throws Exception {
		String formated1 = MethodNameFormatter.format(STRING_TO_FORMAT1);
		String formated2 = MethodNameFormatter.format(STRING_TO_FORMAT2);
		String formated3 = MethodNameFormatter.format(STRING_TO_FORMAT3);

		assertEquals(EXPECTED1, formated1);
		assertEquals(EXPECTED2, formated2);
		assertEquals(EXPECTED3, formated3);
	}
	
	@Test
	public void testWrongFormat() throws Exception {
		String formated = MethodNameFormatter.format(STRING_TO_FORMAT1);
		assertNotEquals(EXPECTED_WRONG1, formated);
	}

}
