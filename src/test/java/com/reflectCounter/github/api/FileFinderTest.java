package com.reflectCounter.github.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.reflectCounter.github.api.FileFinder;
import com.reflectCounter.github.api.SearchTerms;

public class FileFinderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	// TODO remove Ignore if using github api
	@Ignore
	@Test
	public void testFileFinder() throws Exception {
		thrown.expect(Exception.class);
		
		SearchTerms searchTerms = new SearchTerms();
		@SuppressWarnings("unused")
		FileFinder fileFinder = new FileFinder(searchTerms);
	}

	// TODO remove Ignore if using github api
	@Ignore
	@Test
	public void testRun() throws Exception {
		SearchTerms searchTerms = new SearchTerms();
		searchTerms.setMainSearchTerm("import java.io.File");
		searchTerms.setAuthorname("cSploit");
		searchTerms.setLanguage("java");
		searchTerms.setReponame("android");
		
		FileFinder fileFinder = new FileFinder(searchTerms);
		
		List<String> list = fileFinder.run();
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
	}

}
