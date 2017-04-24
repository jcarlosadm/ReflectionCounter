package test.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public abstract class TestFolder {
	
	public static final String PATH = "testTempFolder";
	
	private TestFolder() {
	}

	public static void createFolder() {
		File file = new File(PATH);
		file.mkdirs();
	}
	
	public static void destroyFolder() {
		try {
			FileUtils.deleteDirectory(new File(PATH));
		} catch (IOException e) {
			System.out.println("error to delete test folder");
		}
	}
	
}
