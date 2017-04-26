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

		@Override
		public String basename() {
			return "java.lang.reflect";
		}
	},
	UNSAFE {
		@Override
		public List<String> getListOfClassNames() {
			List<String> list = new ArrayList<>();
			list.add("sun.misc.Unsafe");
			return list;
		}

		@Override
		public String basename() {
			return "sun.misc.Unsafe";
		}
	},
	CLASS {
		@Override
		public List<String> getListOfClassNames() {
			List<String> list = new ArrayList<>();
			list.add("java.lang.Class");
			return list;
		}

		@Override
		public String basename() {
			return "java.lang.Class";
		}
	},
	CLASS_LOADER {
		@Override
		public List<String> getListOfClassNames() {
			List<String> list = new ArrayList<>();
			list.add("java.lang.ClassLoader");
			return list;
		}

		@Override
		public String basename() {
			return "java.lang.ClassLoader";
		}
	};
	
	public abstract List<String> getListOfClassNames();
	public abstract String basename();
	
}
