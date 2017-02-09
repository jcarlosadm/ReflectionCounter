package com.reflectCounter;

import java.io.File;

import com.reflectCounter.util.CSVBuilder;
import com.reflectCounter.util.Repository;
import com.reflectCounter.util.repositoryBuilder.GradleBuilder;
import com.reflectCounter.util.repositoryBuilder.MavenBuilder;
import com.reflectCounter.util.repositoryBuilder.RepoBuilder;

public class ProjectRunner {

	private String projectName = "";

	private Repository repository = null;

	private CSVBuilder csvBuilder = null;

	public ProjectRunner(String url, CSVBuilder csvBuilder) {
		this.repository = new Repository(url);
		this.projectName = this.repository.getRepoFolderName()
				.substring(this.repository.getRepoFolderName().lastIndexOf(File.separator));
		this.csvBuilder = csvBuilder;
	}

	public void run() throws Exception {
		// TODO Auto-generated method stub

		if (!this.repository.cloneRepo()) {
			throw new Exception("error to clone repository " + this.repository.getUrlProject());
		}

		System.out.println("Project: " + this.projectName);

		RepoBuilder repoBuilder = null;
		if (repository.isMavenProject()) {
			repoBuilder = new MavenBuilder(this.repository.getRepoFolderName());
		} else if (repository.isGradleProject()) {
			repoBuilder = new GradleBuilder(this.repository.getRepoFolderName());
		} else {
			throw new Exception(this.projectName + " is not a maven or gradle project. abort");
		}

		if (!repoBuilder.build()) {
			throw new Exception("error to build project " + this.projectName);
		}

		File jarFile = repoBuilder.getJar();

		App app = new App();

		/*
		 * TODO call app over methods of classes of reflection api, and over
		 * methods of Unsafe class write frequency of the method in csv file
		 */
		// app.findCallingMethodsInJar(jarPath, targetClass, targetMethod);
	}

	public static void main(String[] args) {
		// TODO test get all method of reflection
		// https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/package-summary.html
	}
}
