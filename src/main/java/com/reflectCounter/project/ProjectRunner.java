package com.reflectCounter.project;

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

		if (!this.cloneProject() || !this.isGradleOrMaven())
			return;

		File jarFile = null;
		
		// check if jar already exists
		jarFile = this.searchJarLocally();
		
		// download maven artifacts (if is maven project)
		if (!this.validJar(jarFile))
			jarFile = this.downloadMavenArtifact();
		
		// build project
		if (!this.validJar(jarFile))
			jarFile = this.buildProject();
		
		// error to get jar
		if (!this.validJar(jarFile)) {
			this.deleteProjectFolder();
			return;
		}

		// run asm
		if(!this.runAsmTool(jarFile))
			return;

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
	
	private boolean cloneProject() throws Exception {
		System.out.println("cloning " + this.repository.getUrlProject());
		if (!this.repository.cloneRepo()) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(), "error on clone");
			System.out.println("error to clone repository. exiting");
			this.deleteProjectFolder();
			return false;
		}
		
		return true;
	}
	
	private boolean isGradleOrMaven() throws Exception {
		if (!this.repository.isMavenProject() && !this.repository.isGradleProject()) {
			ProjectErrorReport.getInstance().write(this.repository.getUrlProject(),
					"this is not a gradle or maven repository. exiting");
			System.out.println("this is not a gradle or maven repository. exiting");
			this.deleteProjectFolder();
			return false;
		}
		
		return true;
	}
	
	private File downloadMavenArtifact() throws Exception{
		if (this.repository.isMavenProject()) {
			MavenProject mavenProject = new MavenProject(this.getPomFile());
			System.out.println("this is a maven project. searching public artifacts");
			File jarFile = new File(mavenProject.downloadJar());
			
			// TODO move jar to jar folder and set jarFile to newPath
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
		
		// TODO move jar to jar folder and set jar to new path
		jar = this.moveJarFile(jar);
		
		return jar;
	}
	
	private File moveJarFile(File jarFile) throws Exception {
		JarChecker jarChecker = new JarChecker();
		jarChecker.buildPath(this.repository.getOwnerName(), this.repository.getRepoFolderName());
		try {
			return jarChecker.moveJarToDefaultFolder(jarFile);
		} catch (Exception e) {
			return null;
		}
	}
	
	private boolean runAsmTool(File jar) throws Exception {
		this.runAsmTool(jar.getAbsolutePath());

		this.deleteProjectFolder();
		
		return true;
	}
}
