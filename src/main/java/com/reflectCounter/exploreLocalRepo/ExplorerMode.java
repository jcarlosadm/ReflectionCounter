package com.reflectCounter.exploreLocalRepo;

import java.util.ArrayList;
import java.util.List;

public enum ExplorerMode {
	
	REFLECTION {
		@Override
		public List<String> getListOfClassNames() {
			List<String> list = new ArrayList<>();
			list.add("java.lang.reflect.AccessibleObject");
			list.add("java.lang.reflect.Array");
			list.add("java.lang.reflect.Constructor");
			list.add("java.lang.reflect.Executable");
			list.add("java.lang.reflect.Field");
			list.add("java.lang.reflect.Method");
			list.add("java.lang.reflect.Modifier");
			list.add("java.lang.reflect.Parameter");
			list.add("java.lang.reflect.ReflectPermission");
			list.add("java.lang.reflect.Proxy");
			return list;
		}
	},
	UNSAFE {
		@Override
		public List<String> getListOfClassNames() {
			List<String> list = new ArrayList<>();
			list.add("sun.misc.Unsafe");
			return list;
		}
	};
	
	public abstract List<String> getListOfClassNames();
	
}
