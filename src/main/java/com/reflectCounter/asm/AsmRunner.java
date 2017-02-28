package com.reflectCounter.asm;

import java.io.BufferedInputStream;
import java.io.InputStream;
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

/**
 * AsmRunner See App class in
 * http://stackoverflow.com/questions/930289/how-can-i-find-all-the-methods-that-call-a-given-method-in-java
 * get occurrences of methods in jar file
 */
public class AsmRunner {
	private String targetClass;
	private Method targetMethod;

	private AppClassVisitor cv;

	private ArrayList<Callee> callees = new ArrayList<Callee>();
	private String targetMethodDeclaration;
	private String jarPath;
	private CSVBuilder csvBuilder;

	@SuppressWarnings("unused")
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

	private void findCallingMethodsInJar() throws Exception {

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

	public AsmRunner(String jarPath, CSVBuilder csvBuilder) {
		this.jarPath = jarPath;
		this.csvBuilder = csvBuilder;
	}

	public void run() {
		List<String> classes = new ArrayList<>();
		classes.addAll(ExplorerMode.REFLECTION.getListOfClassNames());
		classes.addAll(ExplorerMode.UNSAFE.getListOfClassNames());

		for (String className : classes) {
			this.targetClass = className.replace(".", "/");
			try {
				for (java.lang.reflect.Method method : Class.forName(className).getMethods()) {

					this.targetMethodDeclaration = MethodNameFormatter.format(method.toString());

					try {
						this.findCallingMethodsInJar();

						// TODO replace by write report with csvBuilder
						System.out.println(this.callees.size() + " methods invoke " + this.targetClass + " "
								+ this.targetMethod.getName() + " " + this.targetMethod.getDescriptor() + "\n");
					} catch (Exception e) {
						// TODO output to report_error
						System.out.println("error to analyze method \"" + this.targetMethodDeclaration + "\"");
					}
					this.callees.clear();

				}
			} catch (SecurityException | ClassNotFoundException e) {
				System.out.println("error to analyze \"" + className + "\"");
			}
		}

	}

	// TODO write junit test
}
