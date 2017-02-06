package com.reflectCounter.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;

public class Repository {
	public static boolean cloneRepo(String urlProject) {

		File repofolder = new File(Folders.REPOS_FOLDER + File.separator + getLastRepoFolderName(urlProject));
		if (!repofolder.exists() && repofolder.mkdirs()) {
			try {
				Git.cloneRepository().setURI(urlProject).setDirectory(repofolder).call();
			} catch (Exception e) {
				try {
					FileUtils.deleteDirectory(repofolder);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	private static String getLastRepoFolderName(String urlProject) {
		String lastRepoFolder;
		if (urlProject.endsWith(".git")) {
			lastRepoFolder = urlProject.substring(urlProject.lastIndexOf("/") + 1, urlProject.lastIndexOf("."));
		} else {
			lastRepoFolder = urlProject.substring(urlProject.lastIndexOf("/") + 1);
		}
		return lastRepoFolder;
	}
}
