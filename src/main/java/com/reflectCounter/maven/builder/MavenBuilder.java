package com.reflectCounter.maven.builder;

import java.io.File;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import com.reflectCounter.util.repositoryBuilder.RepoBuilder;

public class MavenBuilder extends RepoBuilder {

	public MavenBuilder(String projectFolder) {
		super(projectFolder);
	}

	@Override
	public boolean build() {
		File pomFile = new File(this.projectFolder + File.separator + "pom.xml");
		if (!pomFile.exists() || pomFile.isDirectory()) {
			return false;
		}
		
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(pomFile);
		request.setGoals(Collections.singletonList("install"));
		
		Invoker invoker = new DefaultInvoker();
		try {
			invoker.execute(request);
		} catch (MavenInvocationException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public File getJar() {
		// TODO Auto-generated method stub
		return null;
	}

}
