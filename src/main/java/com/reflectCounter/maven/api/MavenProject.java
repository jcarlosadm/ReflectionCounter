package com.reflectCounter.maven.api;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.reflectCounter.maven.explorer.MavenExplorer;
import com.reflectCounter.util.Folders;

public class MavenProject {

	private static final String URL_PREFIX = "https://search.maven.org/remotecontent?filepath=";

	private String group = "";
	private String artifact = "";
	private String version = null;

	public MavenProject(File pom) {
		MavenExplorer mavenExplorer = new MavenExplorer(pom);

		this.group = mavenExplorer.getGroup();
		this.artifact = mavenExplorer.getArtifact();
		this.getVersion();
	}

	/**
	 * download jar and get path
	 * 
	 * @return path of jar, or null if this project don't exists
	 */
	public String downloadJar() {
		if (this.version == null || this.version.isEmpty()) {
			System.out.println("error to get jar. version is null or empty");
			return null;
		}

		String path = Folders.OUTPUTS_FOLDER + File.separator + this.artifact + "-" + this.version + ".jar";
		File file = new File(path);

		if (file.exists() && !file.isDirectory()) {
			return path;
		}

		String jarUrl = URL_PREFIX + this.group.replaceAll("[.]", "/") + "/" + this.artifact.replaceAll("[.]", "/")
				+ "/";
		jarUrl += this.version + "/" + this.artifact + "-" + this.version + ".jar";

		try {
			FileUtils.copyURLToFile(new URL(jarUrl), file);

		} catch (MalformedURLException e) {
			System.out.println("error to build url");
			return null;
		} catch (IOException e) {
			System.out.println("error to get file");
			return null;
		}

		return path;
	}

	private void getVersion() {
		try {
			this.version = MavenVersionSearch.getVersion(this.group, this.artifact);
		} catch (Exception e) {
			System.out.println("error to get version");
		}
	}

}
