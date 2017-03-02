package com.reflectCounter.gradle.builder;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reflectCounter.util.Folders;
import com.reflectCounter.util.Repository;
import com.reflectCounter.util.repositoryBuilder.RepoBuilder;

public class GradleBuilderTest {

	private static final String URL_GRADLE_PROJECT = "https://github.com/jitpack/gradle-simple";

	private Repository repository = null;

	@Before
	public void downloadAndSetProject() {
		Folders.makeFolders();

		this.repository = new Repository(URL_GRADLE_PROJECT);
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
	public void testBuild() {
		assertTrue(this.repository.getRepoBuilderInstance().build());
	}

	@Test
	public void testGetJar() {
		RepoBuilder repoBuilder = this.repository.getRepoBuilderInstance();
		assertTrue(repoBuilder.build());
		File jar = repoBuilder.getJar();
		assertNotNull(jar);
	}

}
