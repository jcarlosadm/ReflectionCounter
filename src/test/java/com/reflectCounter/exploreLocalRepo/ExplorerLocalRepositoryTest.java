package com.reflectCounter.exploreLocalRepo;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.reflectCounter.util.reports.CloseReports;

public class ExplorerLocalRepositoryTest {

	@Test
	public void testExplore() throws Exception {

		ExplorerLocalRepository explorer = new ExplorerLocalRepository(
				new File("repos/groovy-html-renderer"),
				"project test");
		List<String> list = new ArrayList<>();
		explorer.explore(list);
		
		for (String string : list) {
			System.out.println(string);
		}
		
		CloseReports.close();
	}

}
