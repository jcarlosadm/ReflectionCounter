package com.reflectCounter.exploreLocalRepo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ExplorerLocalRepository {

	private File repoFolder = null;

	public ExplorerLocalRepository(File repoFolder) {
		this.repoFolder = repoFolder;
	}

	// TODO make tests
	/**
	 * explore list of files, to search methods of classes of reflection and Unsafe
	 * @param listOfFiles
	 * @throws Exception
	 */
	public void explore(List<String> listOfFiles) throws Exception {
		if (listOfFiles == null || listOfFiles.isEmpty())
			this.fillList(listOfFiles);
		
		for (String filepath : listOfFiles) {
			FileSearcher fileSearcher = new FileSearcher(filepath);
			fileSearcher.run();
		}
		
	}

	private void fillList(List<String> listOfFiles) throws Exception {
		String[] extensions = {"java"};
		
		if (listOfFiles == null)
			listOfFiles = new ArrayList<>();
		
		for (File file : FileUtils.listFiles(this.repoFolder, extensions, true))
			listOfFiles.add(file.getAbsolutePath());
	}
}
