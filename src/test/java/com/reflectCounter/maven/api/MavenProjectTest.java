package test.java.com.reflectCounter.maven.api;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Field;

import org.junit.Test;

import com.reflectCounter.maven.api.MavenProject;

public class MavenProjectTest {

	private static final String GROUP = "log4j";
	private static final String ARTIFACT = "log4j";
	private static final String WRONG_GROUP = "aaaaaa";
	private static final String WRONG_ARTIFACT = "bbbbbb";
	
	private MavenProject mavenProject;
	
	private String getVersionObject(String group, String artifact) throws Exception {
		this.mavenProject = new MavenProject(group, artifact);
		
		Field field = MavenProject.class.getDeclaredField("version");
		field.setAccessible(true);
		return (String) field.get(this.mavenProject);
	}
	
	@Test
	public void testConstructor() throws Exception {
		String version = this.getVersionObject(GROUP, ARTIFACT);
		assertNotNull(version);
		assertFalse(version.isEmpty());
	}
	
	@Test
	public void testContructorWrongParameters() throws Exception {
		String version = this.getVersionObject(WRONG_GROUP, ARTIFACT);
		assertNull(version);
		version = this.getVersionObject(GROUP, WRONG_ARTIFACT);
		assertNull(version);
		version = this.getVersionObject(WRONG_GROUP, WRONG_ARTIFACT);
		assertNull(version);
	}

	@Test
	public void testDownloadJar() throws Exception {
		this.mavenProject = new MavenProject(GROUP, ARTIFACT);
		String path = this.mavenProject.downloadJar();
		
		assertNotNull(path);
		assertFalse(path.isEmpty());
		
		File file = new File(path);
		assertTrue(file.exists() && !file.isDirectory());
		
		assertTrue(file.delete());
	}
	
	@Test
	public void testDownloadJarWrongParameters() throws Exception {
		this.mavenProject = new MavenProject(WRONG_GROUP, WRONG_ARTIFACT);
		String path = this.mavenProject.downloadJar();
		assertNull(path);
		
		this.mavenProject = new MavenProject(GROUP, WRONG_ARTIFACT);
		path = this.mavenProject.downloadJar();
		assertNull(path);
		
		this.mavenProject = new MavenProject(WRONG_GROUP, ARTIFACT);
		path = this.mavenProject.downloadJar();
		assertNull(path);
	}

}
