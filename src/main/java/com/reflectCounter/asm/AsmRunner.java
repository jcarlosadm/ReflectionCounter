package com.reflectCounter.asm;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;
import org.objectweb.asm.commons.Method;

import com.reflectCounter.exploreLocalRepo.ExplorerMode;
import com.reflectCounter.util.CSVBuilder;

public class AsmRunner {
	private String targetClass;
	private Method targetMethod;

	private AppClassVisitor cv;

	private ArrayList<Callee> callees = new ArrayList<Callee>();
	private String targetMethodDeclaration;
	private String jarPath;

	private static class Callee {
		String className;
		String methodName;
		String methodDesc;
		String source;
		int line;

		public Callee(String cName, String mName, String mDesc, String src, int ln) {
			className = cName;
			methodName = mName;
			methodDesc = mDesc;
			source = src;
			line = ln;
		}
	}

	private class AppMethodVisitor extends MethodAdapter {

		boolean callsTarget;
		int line;

		public AppMethodVisitor() {
			super(new EmptyVisitor());
		}

		public void visitMethodInsn(int opcode, String owner, String name, String desc) {
			if (owner.equals(targetClass) && name.equals(targetMethod.getName())
					&& desc.equals(targetMethod.getDescriptor())) {
				callsTarget = true;
			}
		}

		public void visitCode() {
			callsTarget = false;
		}

		public void visitLineNumber(int line, Label start) {
			this.line = line;
		}

		public void visitEnd() {
			if (callsTarget)
				callees.add(new Callee(cv.className, cv.methodName, cv.methodDesc, cv.source, line));
		}
	}

	private class AppClassVisitor extends ClassAdapter {

		private AppMethodVisitor mv = new AppMethodVisitor();

		public String source;
		public String className;
		public String methodName;
		public String methodDesc;

		public AppClassVisitor() {
			super(new EmptyVisitor());
		}

		public void visit(int version, int access, String name, String signature, String superName,
				String[] interfaces) {
			className = name;
		}

		public void visitSource(String source, String debug) {
			this.source = source;
		}

		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			methodName = name;
			methodDesc = desc;

			return mv;
		}
	}

	private void findCallingMethodsInJar()
			throws Exception {

		this.targetMethod = Method.getMethod(targetMethodDeclaration);

		this.cv = new AppClassVisitor();

		JarFile jarFile = new JarFile(this.jarPath);
		Enumeration<JarEntry> entries = jarFile.entries();

		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();

			if (entry.getName().endsWith(".class")) {
				InputStream stream = new BufferedInputStream(jarFile.getInputStream(entry), 1024);
				ClassReader reader = new ClassReader(stream);

				reader.accept(cv, 0);

				stream.close();
			}
		}

		jarFile.close();
	}
	
	private List<Callee> getCalleeList() {
		return this.callees;
	}
	
	private AsmRunner(String jarPath, String targetClass, String targetMethodDeclaration) {
		this.jarPath = jarPath;
		this.targetClass = targetClass;
		this.targetMethodDeclaration = targetMethodDeclaration;
	}
	
	public void run(String jarPath, CSVBuilder csvBuilder) {
		// TODO implement
		
	}
	
	public static void main(String[] args) throws SecurityException, ClassNotFoundException {
		String className = ExplorerMode.UNSAFE.getListOfClassNames().get(0);
		java.lang.reflect.Method[] methods = Class.forName(className).getMethods();
		
		System.out.println("class: "+ExplorerMode.UNSAFE.getListOfClassNames().get(0)+"\n");
		for (java.lang.reflect.Method method : methods) {
			/*System.out.print(method.getReturnType().toString() + " " + method.getName() + "(");
			boolean first = true;
			for (Type type : method.getParameterTypes()) {
				if(first){ first = false; }
				else { System.out.print(", "); }
				System.out.print(type.toString());
			}
			System.out.println(")");*/
			System.out.println(method);
		}
		System.out.println("--------------------------");
	}

	/*public static void main(String[] args) {
		try {
			AsmRunner app = new AsmRunner(args[0], args[1], args[2]);

			app.findCallingMethodsInJar();

			for (Callee c : app.callees) {
				System.out
						.println(c.source + ":" + c.line + " " + c.className + " " + c.methodName + " " + c.methodDesc);
			}

			System.out.println("--\n" + app.callees.size() + " methods invoke " + app.targetClass + " "
					+ app.targetMethod.getName() + " " + app.targetMethod.getDescriptor());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}*/

}
