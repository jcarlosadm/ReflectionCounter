package com.reflectCounter.exploreLocalRepo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.reflectCounter.util.OcurrencesCsv;

public class ExplorerLocalRepository {

	private File repoFolder = null;

	private OcurrencesCsv csvBuilder = null;

	public ExplorerLocalRepository(File repoFolder, OcurrencesCsv csvBuilder) {
		this.repoFolder = repoFolder;
		this.csvBuilder = csvBuilder;
	}

	/**
	 * explore list of files, to search methods of classes of reflection or Unsafe
	 * @param listOfFiles
	 * @param mode explorer mode (i.e., reflection classes or Unsafe class)
	 * @return list of uncertain files (use other search algorithm)
	 * @throws Exception
	 */
	public List<String> explore(List<String> listOfFiles, ExplorerMode mode) throws Exception {
		if (listOfFiles == null || listOfFiles.isEmpty()) {
			return this.exploreAllFiles(mode);
		}
		
		List<String> suspiciousFiles = new ArrayList<>();
		
		for (String filepath : listOfFiles) {
			if (!this.searchMethods(filepath, mode))
				suspiciousFiles.add(filepath);
		}
		
		return suspiciousFiles;
	}

	/**
	 * explore all files of this repository
	 * @param mode explorer mode (i.e., reflection classes or Unsafe class)
	 * @return list of uncertain files (use other search algorithm)
	 * @throws Exception
	 */
	private List<String> exploreAllFiles(ExplorerMode mode) throws Exception {
		String[] extensions = {"java"};
		List<String> suspiciousFiles = new ArrayList<>();
		for (File file : FileUtils.listFiles(this.repoFolder, extensions, true)) {
			if (!this.searchMethods(file.getAbsolutePath(), mode))
				suspiciousFiles.add(file.getAbsolutePath());
		}
		
		return suspiciousFiles;
	}

	/**
	 * search methods on designated file
	 * @param filepath file to search
	 * @param mode explorer mode (i.e., reflection classes or Unsafe class)
	 * @return true if get all methods without uncertain. false if otherwise
	 * @throws Exception
	 */
	private boolean searchMethods(String filepath, ExplorerMode mode) throws Exception {
		// TODO implement (if using github api)
		File file = new File(filepath);
		List<String> classes = mode.getListOfClassNames();
		
		
		return false;
	}

}
