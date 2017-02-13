package test.java.com.reflectCounter.maven.api;

import static org.junit.Assert.*;

import org.junit.Test;

import com.reflectCounter.maven.api.MavenVersionSearch;

public class MavenVersionSearchTest {

	private static final String GROUP = "log4j";
	private static final String ARTIFACT = "log4j";
	private static final String WRONG_GROUP = "log4jdd";
	private static final String WRONG_ARTIFACT = "log4jwww";
	
	@Test
	public void testGetVersion() throws Exception {
		String version = MavenVersionSearch.getVersion(GROUP, ARTIFACT);
		assertNotNull(version);
		assertFalse(version.isEmpty());
	}
	
	@Test
	public void testWrongGroup() throws Exception {
		String version = MavenVersionSearch.getVersion(WRONG_GROUP, ARTIFACT);
		assertNull(version);
	}

	@Test
	public void testWrongArtifact() throws Exception {
		String version = MavenVersionSearch.getVersion(GROUP, WRONG_ARTIFACT);
		assertNull(version);
	}
	
	@Test
	public void testWrongGroupAndArtifact() throws Exception {
		String version = MavenVersionSearch.getVersion(WRONG_GROUP, WRONG_ARTIFACT);
		assertNull(version);
	}
}
