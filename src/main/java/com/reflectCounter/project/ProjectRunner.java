package com.reflectCounter.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.reflectCounter.asm.AsmRunner;
import com.reflectCounter.exploreLocalRepo.ExplorerLocalRepository;
import com.reflectCounter.exploreLocalRepo.ExplorerMode;
import com.reflectCounter.github.api.FileFinder;
import com.reflectCounter.github.api.SearchTerms;
import com.reflectCounter.maven.api.MavenProject;
import com.reflectCounter.util.Repository;
import com.reflectCounter.util.reports.ProjectErrorReport;
import com.reflectCounter.util.repositoryBuilder.RepoBuilder;

public class ProjectRunner {

	private Repository repository = null;

	public ProjectRunner(String url) {
		this.repository = new Repository(url);
	}

	public void run() throws Exception {

		System.out.println(System.lineSeparator());

		if (!this.cloneProject()) {
			this.deleteProjectFolder();
			return;
		}

		if (this.isGradleOrMaven() == true) {

			// check if jar already exists
			File jarFile = this.searchJarLocally();

			// download maven artifacts (if is maven project)
			if (!this.validJar(jarFile))
				jarFile = this.downloadMavenArtifact();

			// build project
			if (!this.validJar(jarFile))
				jarFile = this.buildProject();

			// run asm and exit
			if (this.validJar(jarFile)) {
				this.runAsmTool(jarFile.getAbsolutePath());
				this.deleteProjectFolder();
				return;
			}
		}

		// TODO explorer local repo
		List<String> files = this.searchFilesOnGithub();
		ExplorerLocalRepository explorerLocalRepository = new ExplorerLocalRepository(
				new File(this.repository.getRepoFolderPath()), this.repository.getUrlProject());
		explorerLocalRepository.explore(files);
		this.deleteProjectFolder();

	}

	private List<String> searchFilesOnGithub() throws Exception {
		List<String> files = new ArrayList<>();

		for (ExplorerMode explorerElement : ExplorerMode.values()) {
			SearchTerms searchTerms = new SearchTerms();
			this.buildSearchTerms(searchTerms, explorerElement);
			FileFinder fileFinder = new FileFinder(searchTerms);

			List<String> tempFiles = null;
			try {
				tempFiles = fileFinder.run();
			} catch (Exception e) {
				tempFiles = null;
			}

			if (tempFiles != null && !tempFiles.isEmpty())
				files.addAll(tempFiles);
		}

		return files;
	}

	private void buildSearchTerms(SearchTerms searchTerms, ExplorerMode explorerElement) {
		String basename = explorerElement.basename();
		searchTerms.setMainSearchTerm("import " + basename);
		searchTerms.setAuthorname(this.repository.getOwnerName());
		searchTerms.setLanguage("java");
		searchTerms.setReponame(this.repository.getRepoFolderName());
	}

	private boolean validJar(File jarFile) {
		return (jarFile != null && jarFile.exists() && !jarFile.isDirectory());
	}

	private File searchJarLocally() {
		JarChecker jarChecker = new JarChecker();
		jarChecker.buildPath(this.repository.getOwnerName(), this.repository.getRepoFolderName());
		try {
			return jarChecker.getJar();
		} catch (Exception e) {
			return null;
		}
	}

	private void deleteProjectFolder() throws Exception {
		FileUtils.deleteDirectory(new File(this.repository.getRepoFolderPath()));
	}

	private void runAsmTool(String jarPath) {
		System.out.println("running asm tool in " + jarPath);
		AsmRunner asmRunner = new AsmRunner(jarPath, this.repository.getUrlProject());
		asmRunner.run();
		System.out.println("finished");
	}

	private File getPomFile() {
		return new File(this.repository.getRepoFolderPath() + File.separator + "pom.xml");
	}

	private boolean cloneProject() throws Exception {
		System.out.println("cloning " + this.repository.getUrlProject());
		if (!this.repository.cloneRepo()) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(), "error on clone");
			System.out.println("error to clone repository. exiting");
			return false;
		}

		return true;
	}

	private boolean isGradleOrMaven() throws Exception {
		if (!this.repository.isMavenProject() && !this.repository.isGradleProject()) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(),
					"this is not a gradle or maven repository. exiting");
			System.out.println("this is not a gradle or maven repository. exiting");
			return false;
		}

		return true;
	}

	private File downloadMavenArtifact() throws Exception {
		if (this.repository.isMavenProject()) {
			MavenProject mavenProject = new MavenProject(this.getPomFile());
			System.out.println("this is a maven project. searching public artifacts");
			File jarFile = null;
			try {
				jarFile = new File(mavenProject.downloadJar());
			} catch (Exception e) {
				jarFile = null;
			}

			jarFile = moveJarFile(jarFile);

			if (jarFile == null || !jarFile.exists() || jarFile.isDirectory()) {
				System.out.println("error to download jar");
				return null;
			}

			return jarFile;
		}

		return null;
	}

	private File buildProject() throws Exception {
		RepoBuilder repoBuilder = this.repository.getRepoBuilderInstance();
		System.out.println("building project " + this.repository.getUrlProject());
		if (repoBuilder.build() == false) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(), "error on build");
			System.out.println("error to build this project");
			return null;
		}

		File jar = repoBuilder.getJar();
		if (jar == null || !jar.exists() || jar.isDirectory()) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(), "error on build");
			System.out.println("jar don't exists");
			return null;
		}

		jar = this.moveJarFile(jar);

		return jar;
	}

	private File moveJarFile(File jarFile) throws Exception {
		if (jarFile == null)
			return null;

		JarChecker jarChecker = new JarChecker();
		jarChecker.buildPath(this.repository.getOwnerName(), this.repository.getRepoFolderName());
		try {
			return jarChecker.moveJarToDefaultFolder(jarFile);
		} catch (Exception e) {
			return null;
		}
	}
}
