package com.reflectCounter.maven.api;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.reflectCounter.maven.api.MavenVersionJSON;

public class MavenVersionJSONTest {

	private static final String GROUP = "log4j";
	private static final String ARTIFACT = "log4j";
	private static final String WRONG_GROUP = "jjsjs";
	private static final String WRONG_ARTIFACT = "log4jaass";

	@Test
	public void testGetJson() throws Exception {
		JSONObject jsonObject = MavenVersionJSON.getJson(GROUP, ARTIFACT);
		assertNotNull(jsonObject);
	}
	
	@Test
	public void testWrongArtifact() throws Exception {
		JSONObject jsonObject = MavenVersionJSON.getJson(GROUP, WRONG_ARTIFACT);
		assertNotNull(jsonObject);
	}
	
	@Test
	public void testWrongGroup() throws Exception {
		JSONObject jsonObject = MavenVersionJSON.getJson(WRONG_GROUP, ARTIFACT);
		assertNotNull(jsonObject);
	}
	
	@Test
	public void testWrongGroupAndWrongArtifact() throws Exception {
		JSONObject jsonObject = MavenVersionJSON.getJson(WRONG_GROUP, WRONG_ARTIFACT);
		assertNotNull(jsonObject);
	}

}
