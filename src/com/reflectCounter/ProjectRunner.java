package com.reflectCounter;

import java.io.File;
import java.util.List;

import com.reflectCounter.exploreLocalRepo.ExplorerLocalRepository;
import com.reflectCounter.exploreLocalRepo.ExplorerMode;
import com.reflectCounter.github.api.FileFinder;
import com.reflectCounter.github.api.SearchTerms;
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
		SearchTerms searchTerms = this.fillBasicSearchTerms();

		List<String> listReflectFiles = null;
		this.fillListOfFiles(searchTerms, listReflectFiles, "import java.lang.reflect");

		List<String> listUnsafeFiles = null;
		this.fillListOfFiles(searchTerms, listUnsafeFiles, "import sun.misc.Unsafe");

		if (!this.repository.cloneRepo()) {
			throw new Exception("error to clone repository " + this.repository.getUrlProject());
		}

		System.out.println("Project: " + this.projectName);

		ExplorerLocalRepository expLocalRepo = new ExplorerLocalRepository(
				new File(this.repository.getRepoFolderName()), this.csvBuilder);
		
		List<String> suspiciousReflectList = expLocalRepo.explore(listReflectFiles, ExplorerMode.REFLECTION);
		List<String> suspiciousUnsafeList = expLocalRepo.explore(listUnsafeFiles, ExplorerMode.UNSAFE);
		
		

		// TODO maybe remove
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

	private SearchTerms fillBasicSearchTerms() {
		SearchTerms searchTerms = new SearchTerms();
		searchTerms.setAuthorname(this.getAuthorName());
		searchTerms.setLanguage("java");
		searchTerms.setReponame(this.projectName);
		return searchTerms;
	}

	private void fillListOfFiles(SearchTerms searchTerms, List<String> list, String mainSearchTerm) {
		searchTerms.setMainSearchTerm(mainSearchTerm);
		try {
			FileFinder fileFinderReflect = new FileFinder(searchTerms);
			list = fileFinderReflect.run();
		} catch (Exception e) {
			list = null;
		}
	}

	private String getAuthorName() {
		String url = this.repository.getUrlProject();
		url = url.substring(0, url.lastIndexOf("/"));
		return url.substring(url.lastIndexOf("/") + 1);
	}
}
