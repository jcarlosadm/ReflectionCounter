package com.reflectCounter.gradle.builder;

import java.io.File;

import com.reflectCounter.util.StreamGobbler;
import com.reflectCounter.util.repositoryBuilder.RepoBuilder;

public class GradleBuilder implements RepoBuilder {

	private String projectFolder = "";

	public GradleBuilder(String projectFolder) {
		this.projectFolder = projectFolder;
	}

	@Override
	public boolean build() {
		String command = "gradle build";

		try {
			Process p = Runtime.getRuntime().exec(command, null, new File(this.projectFolder));

			StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream());
			StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream());
			errorGobbler.start();
			outputGobbler.start();

			p.waitFor();
		} catch (Exception e) {
			System.out.println("error to build this project (exception)");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public File getJar() {
		if (this.projectFolder.endsWith(File.separator))
			this.projectFolder = this.projectFolder.substring(0, this.projectFolder.length() - 1);

		File folder = new File(this.projectFolder + File.separator + "build" + File.separator + "libs");
		if (!folder.exists() || !folder.isDirectory())
			return null;

		File[] listOfFiles = folder.listFiles();
		File jar = null;
		for (File file : listOfFiles) {
			if (file.exists() && !file.isDirectory() && !file.getName().endsWith("javadoc.jar")
					&& !file.getName().endsWith("sources.jar") && file.getName().endsWith(".jar")) {
				jar = file;
				break;
			}
		}

		if (!jar.exists() || jar.isDirectory())
			return null;

		return jar;
	}

}
