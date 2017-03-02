package com.reflectCounter.maven.builder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reflectCounter.util.Folders;
import com.reflectCounter.util.Repository;
import com.reflectCounter.util.repositoryBuilder.RepoBuilder;

public class MavenBuilderTest {

	private static final String URL_MAVEN_PROJECT = "https://github.com/jcarlosadm/common-java-class";
	
	private Repository repository = null;
	
	@Before
	public void downloadAndSetProject() {
		Folders.makeFolders();
		
		this.repository = new Repository(URL_MAVEN_PROJECT);
		this.repository.cloneRepo();
	}
	
	@After
	public void cleanWorkspace() {
		File repo_folder = new File(Folders.REPOS_FOLDER);
		if (repo_folder.exists() && repo_folder.isDirectory()) {
			try {
				FileUtils.deleteDirectory(repo_folder);
			} catch (Exception e) {
			}
		}
		
	}
	
	@Test
	public void testBuild() throws Exception {
		assertTrue(this.repository.getRepoBuilderInstance().build());
	}

	@Test
	public void testGetJar() throws Exception {
		RepoBuilder repoBuilder = this.repository.getRepoBuilderInstance();
		assertTrue(repoBuilder.build());
		File jar = repoBuilder.getJar();
		assertNotNull(jar);
	}

}
