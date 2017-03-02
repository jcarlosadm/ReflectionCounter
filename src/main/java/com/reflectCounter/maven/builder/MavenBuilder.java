package com.reflectCounter.maven.builder;

import java.io.File;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import com.reflectCounter.maven.explorer.MavenExplorer;
import com.reflectCounter.util.repositoryBuilder.RepoBuilder;

import br.com.commons.util.PropertiesManager;

public class MavenBuilder implements RepoBuilder {

	private String projectFolder = "";
	private boolean success = false;

	public MavenBuilder(String projectFolder) {
		this.projectFolder = projectFolder;
	}

	@Override
	public boolean build() {
		File pomFile = new File(this.projectFolder + File.separator + "pom.xml");
		if (!pomFile.exists() || pomFile.isDirectory())
			return false;

		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(pomFile);
		request.setGoals(Collections.singletonList("package"));
		request.setBaseDirectory(new File(this.projectFolder));

		Invoker invoker = new DefaultInvoker();
		
		invoker.setMavenHome(new File(PropertiesManager.getProperty("maven.home")));
		
		try {
			invoker.execute(request);
		} catch (MavenInvocationException e) {
			e.printStackTrace();
			return false;
		}

		this.success = true;
		return true;
	}

	@Override
	public File getJar() {
		if (this.success == false)
			return null;

		MavenExplorer mvnE = new MavenExplorer(new File(this.projectFolder + File.separator + "pom.xml"));
		String artifact = mvnE.getArtifact();
		String version = mvnE.getVersion();
		if (artifact == null || artifact.isEmpty() || version == null || version.isEmpty())
			return null;
		
		File jar = new File(this.projectFolder + File.separator + "target" + File.separator + artifact + "-" + version + ".jar");
		if (!jar.exists() || jar.isDirectory())
			return null;

		return jar;
	}

}
