package com.reflectCounter.util.reports;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.utils.TestFolder;

public class MethodCounterTest {
	@Rule
    public ExpectedException thrown= ExpectedException.none();

	private static final String FILEPATH = TestFolder.PATH + File.separator + "method_counter.csv";
	private static final String SEPARATOR = ";";
	private static final String HEADER = "Project" + SEPARATOR + "ClassName" + SEPARATOR + "Method" + SEPARATOR
			+ "Count";

	private MethodCounter getInstance() throws Exception {
		Constructor<MethodCounter> constructor = MethodCounter.class.getDeclaredConstructor(File.class, String.class,
				String.class);
		constructor.setAccessible(true);
		return constructor.newInstance(new File(FILEPATH), SEPARATOR, HEADER);
	}
	
	@Before
	public void createTestFolder() {
		TestFolder.createFolder();
	}
	
	@After
	public void destroyFolder() {
		TestFolder.destroyFolder();
	}

	@Test
	public void testGetInstance() throws Exception {
		assertNotNull(this.getInstance());
	}

	@Test
	public void testWriteString() throws Exception {
		MethodCounter methodCounter = this.getInstance();
		methodCounter.write("1", "2", "3", 1);
		methodCounter.close();
		
		BufferedReader bReader = new BufferedReader(new FileReader(new File(FILEPATH)));
		String lines = "", line = "";
		while((line = bReader.readLine()) != null)
			lines += line + "\n";
		bReader.close();
		
		assertEquals(HEADER+"\n1;2;3;1\n", lines);
	}

	@Test
	public void testClose() throws Exception {
		thrown.expect(IOException.class);
		
		MethodCounter methodCounter = this.getInstance();
		methodCounter.write("1", "2", "3", 1);
		methodCounter.close();
		methodCounter.write("1", "2", "3", 1);
	}

}
