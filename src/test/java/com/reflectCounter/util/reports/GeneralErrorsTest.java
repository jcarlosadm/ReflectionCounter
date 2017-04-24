package com.reflectCounter.util.reports;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.utils.TestFolder;

public class GeneralErrorsTest {
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();

	private static final String FILEPATH = TestFolder.PATH + File.separator + "general_errors.txt";
	private static final String STRING_TEST = "My string test";
	
	private GeneralErrors generalErrors = null;
	
	@Before
	public void setNewInstance() throws Exception {
		TestFolder.createFolder();

		Constructor<GeneralErrors> constructor = GeneralErrors.class.getDeclaredConstructor(File.class);
		constructor.setAccessible(true);
		this.generalErrors = constructor.newInstance(new File(FILEPATH));
	}
	
	@After
	public void destroyFolder() throws Exception {
		generalErrors.close();
		TestFolder.destroyFolder();
	}

	@Test
	public void testWrite() throws Exception {
		
		generalErrors.write(STRING_TEST);
		generalErrors.close();

		File file = new File(FILEPATH);
		BufferedReader bReader = new BufferedReader(new FileReader(file));
		String line = "";
		String lines = "";
		while ((line = bReader.readLine()) != null) {
			lines += line + "\n";
		}
		bReader.close();

		assertEquals(STRING_TEST + "\n", lines);
	}

	@Test
	public void testGetInstance() throws Exception {
		Assert.assertNotNull(generalErrors);
	}

	@Test
	public void testClose() throws Exception {
		thrown.expect(IOException.class);
		
		generalErrors.write(STRING_TEST);
		generalErrors.close();
		generalErrors.write(STRING_TEST);
	}
}
