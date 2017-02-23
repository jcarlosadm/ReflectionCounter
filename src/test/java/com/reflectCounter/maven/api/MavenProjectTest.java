package com.reflectCounter.maven.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Field;

import org.junit.Test;

public class MavenProjectTest {

	private static final String CORRECT_POM = "src/test/resources/correct_pom.xml";
	private static final String WRONG_POM_1 = "src/test/resources/wrong_pom.xml";
	private static final String WRONG_POM_2 = "src/test/resources/wrong_pom2.xml";
	private static final String WRONG_POM_3 = "src/test/resources/wrong_pom3.xml";
	
	private MavenProject mavenProject;
	
	private String getVersionObject(String pathname) throws Exception {
		this.mavenProject = new MavenProject(new File(pathname));
		
		Field field = MavenProject.class.getDeclaredField("version");
		field.setAccessible(true);
		return (String) field.get(this.mavenProject);
	}
	
	@Test
	public void testConstructor() throws Exception {
		String version = this.getVersionObject(CORRECT_POM);
		assertNotNull(version);
		assertFalse(version.isEmpty());
	}
	
	@Test
	public void testContructorWrongParameters() throws Exception {
		String version = this.getVersionObject(WRONG_POM_1);
		assertNull(version);
		version = this.getVersionObject(WRONG_POM_2);
		assertNull(version);
		version = this.getVersionObject(WRONG_POM_3);
		assertNull(version);
	}

	@Test
	public void testDownloadJar() throws Exception {
		this.mavenProject = new MavenProject(new File(CORRECT_POM));
		String path = this.mavenProject.downloadJar();
		
		assertNotNull(path);
		assertFalse(path.isEmpty());
		
		File file = new File(path);
		assertTrue(file.exists() && !file.isDirectory());
		
		assertTrue(file.delete());
	}
	
	@Test
	public void testDownloadJarWrongParameters() throws Exception {
		this.mavenProject = new MavenProject(new File(WRONG_POM_1));
		String path = this.mavenProject.downloadJar();
		assertNull(path);
		
		this.mavenProject = new MavenProject(new File(WRONG_POM_2));
		path = this.mavenProject.downloadJar();
		assertNull(path);
		
		this.mavenProject = new MavenProject(new File(WRONG_POM_3));
		path = this.mavenProject.downloadJar();
		assertNull(path);
	}

}
