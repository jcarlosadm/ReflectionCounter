package com.reflectCounter.exploreLocalRepo;

import java.util.HashMap;
import java.util.Map;

public class ExplorerCounter {

	private Map<String, Map<String, Integer>> methodCountByClass = new HashMap<>();

	public void incrementCount(String className, String methodName) {
		if (this.methodCountByClass.containsKey(className) == false) {
			Map<String, Integer> methodCount = new HashMap<>();
			methodCount.put(methodName, 1);
			this.methodCountByClass.put(className, methodCount);
		} else {
			Map<String, Integer> methodCount = this.methodCountByClass.get(className);
			methodCount.put(methodName, methodCount.get(methodName) + 1);
		}
	}

	public Map<String, Map<String, Integer>> getMap() {
		return this.methodCountByClass;
	}
}
