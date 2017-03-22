package com.reflectCounter;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.reflectCounter.asm.AsmRunner;
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

		System.out.println("cloning " + this.repository.getUrlProject());
		if (!this.repository.cloneRepo()) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(), "error on clone");
			System.out.println("error to clone repository. exiting");
			this.deleteProjectFolder();
			return;
		}

		// TODO remove if uses github api
		if (!this.repository.isMavenProject() && !this.repository.isGradleProject()) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(),
					"this is not a gradle or maven repository. exiting");
			System.out.println("this is not a gradle or maven repository. exiting");
			this.deleteProjectFolder();
			return;
		}

		// TODO check if jar already exists
		
		// search artifacts if is maven project
		if (this.repository.isMavenProject()) {
			MavenProject mavenProject = new MavenProject(this.getPomFile());
			System.out.println("this is a maven project. searching public artifacts");
			String jarPath = mavenProject.downloadJar();
			// TODO move jar to jar folder (in project+user folder)
			if (jarPath != null && !jarPath.isEmpty()) {
				this.runAsmTool(jarPath);
				this.deleteProjectFolder();
				return;
			} else
				System.out.println("error to download jar");
		}

		// build

		RepoBuilder repoBuilder = this.repository.getRepoBuilderInstance();
		System.out.println("building project " + this.repository.getUrlProject());
		if (repoBuilder.build() == false) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(), "error on build");
			System.out.println("error to build this project");
			this.deleteProjectFolder();
			return;
		}

		File jar = repoBuilder.getJar();
		if (jar == null || !jar.exists() || jar.isDirectory()) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(), "error on build");
			System.out.println("jar don't exists");
			this.deleteProjectFolder();
			return;
		}

		this.runAsmTool(jar.getAbsolutePath());

		this.deleteProjectFolder();

		// // using github api
		//
		// SearchTerms searchTerms = this.fillBasicSearchTerms();
		//
		// List<String> listReflectFiles = null;
		// this.fillListOfFiles(searchTerms, listReflectFiles, "import
		// java.lang.reflect");
		//
		// List<String> listUnsafeFiles = null;
		// this.fillListOfFiles(searchTerms, listUnsafeFiles, "import
		// sun.misc.Unsafe");
		//
		// if (!this.repository.cloneRepo()) {
		// throw new Exception("error to clone repository " +
		// this.repository.getUrlProject());
		// }
		//
		// System.out.println("Project: " + this.projectName);
		//
		// ExplorerLocalRepository expLocalRepo = new ExplorerLocalRepository(
		// new File(this.repository.getRepoFolderName()), this.csvBuilder);
		//
		// List<String> suspiciousReflectList =
		// expLocalRepo.explore(listReflectFiles, ExplorerMode.REFLECTION);
		// List<String> suspiciousUnsafeList =
		// expLocalRepo.explore(listUnsafeFiles, ExplorerMode.UNSAFE);
		//
	}

	private void deleteProjectFolder() throws Exception {
		FileUtils.deleteDirectory(new File(this.repository.getRepoFolderName()));
	}

	private void runAsmTool(String jarPath) {
		System.out.println("running asm tool in " + jarPath);
		AsmRunner asmRunner = new AsmRunner(jarPath, this.repository.getUrlProject());
		asmRunner.run();
		System.out.println("finished");
	}

	private File getPomFile() {
		return new File(this.repository.getRepoFolderName() + File.separator + "pom.xml");
	}

	// private SearchTerms fillBasicSearchTerms() {
	// SearchTerms searchTerms = new SearchTerms();
	// searchTerms.setAuthorname(this.getAuthorName());
	// searchTerms.setLanguage("java");
	// searchTerms.setReponame(this.projectName);
	// return searchTerms;
	// }
	//
	// private void fillListOfFiles(SearchTerms searchTerms, List<String> list,
	// String mainSearchTerm) {
	// searchTerms.setMainSearchTerm(mainSearchTerm);
	// try {
	// FileFinder fileFinderReflect = new FileFinder(searchTerms);
	// list = fileFinderReflect.run();
	// } catch (Exception e) {
	// list = null;
	// }
	// }
	//
	// private String getAuthorName() {
	// String url = this.repository.getUrlProject();
	// url = url.substring(0, url.lastIndexOf("/"));
	// return url.substring(url.lastIndexOf("/") + 1);
	// }
}
