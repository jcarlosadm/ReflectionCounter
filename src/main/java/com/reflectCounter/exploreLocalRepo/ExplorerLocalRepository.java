package com.reflectCounter.exploreLocalRepo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.reflectCounter.util.reports.MethodCounter;

public class ExplorerLocalRepository {

	private File repoFolder = null;
	private String projectName = "";

	public ExplorerLocalRepository(File repoFolder, String projectName) {
		this.repoFolder = repoFolder;
		this.projectName = projectName;
	}

	// TODO make tests
	/**
	 * explore list of files, to search methods of classes of reflection and
	 * Unsafe
	 * 
	 * @param listOfFiles
	 * @throws Exception
	 */
	public void explore(List<String> listOfFiles) throws Exception {
		ExplorerCounter explorerCounter = new ExplorerCounter();

		if (listOfFiles == null || listOfFiles.isEmpty())
			this.fillList(listOfFiles);

		for (String filepath : listOfFiles) {
			FileSearcher fileSearcher = new FileSearcher(filepath, explorerCounter);
			fileSearcher.run();
		}

		Map<String, Map<String, Integer>> explorerMap = explorerCounter.getMap();
		this.report(explorerMap);

	}

	private void report(Map<String, Map<String, Integer>> explorerMap) throws Exception {
		for (String className : explorerMap.keySet()) {
			for (String methodName : explorerMap.get(className).keySet()) {
				MethodCounter.getInstance().write(this.projectName, className, methodName,
						explorerMap.get(className).get(methodName));
			}
		}
	}

	private void fillList(List<String> listOfFiles) throws Exception {
		String[] extensions = { "java" };

		if (listOfFiles == null)
			listOfFiles = new ArrayList<>();

		for (File file : FileUtils.listFiles(this.repoFolder, extensions, true))
			listOfFiles.add(file.getAbsolutePath());
	}
}
