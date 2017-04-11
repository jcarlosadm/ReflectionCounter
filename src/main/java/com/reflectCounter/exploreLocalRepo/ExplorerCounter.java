package com.reflectCounter.exploreLocalRepo;

import java.util.HashMap;
import java.util.Map;

public class ExplorerCounter {

	private Map<String, Map<String, Integer>> methodCountByClass = new HashMap<>();

	public void addCount(String className, String methodName, int value) {
		if (value < 1)
			return;
		
		if (this.methodCountByClass.containsKey(className) == false) {
			Map<String, Integer> methodCount = new HashMap<>();
			methodCount.put(methodName, value);
			this.methodCountByClass.put(className, methodCount);
		} else {
			Map<String, Integer> methodCount = this.methodCountByClass.get(className);
			if (methodCount.containsKey(methodName))
				methodCount.put(methodName, methodCount.get(methodName) + value);
			else
				methodCount.put(methodName, value);
		}
	}

	public Map<String, Map<String, Integer>> getMap() {
		return this.methodCountByClass;
	}
}
