package com.reflectCounter.asm;

public class MethodNameFormatter {

	private MethodNameFormatter() {
	}

	public static String format(String methodSig) {
		String aux = getStringBeforeParenthesis(methodSig);
		aux = getStringBeforeMethodName(aux);

		methodSig = buildMethodSignature(methodSig, aux);
		methodSig = formatMethodName(methodSig);

		return methodSig;
	}

	private static String formatMethodName(String methodSig) {
		String returnType = methodSig.substring(0, methodSig.indexOf(" "));

		String methodName = methodSig.substring(methodSig.indexOf(returnType) + returnType.length() + 1,
				methodSig.indexOf("("));
		int index = methodName.lastIndexOf(".");
		if (index != -1) {
			String newM = methodName.substring(index + 1);
			methodSig = methodSig.replace(methodName, newM);
		}
		return methodSig;
	}

	private static String buildMethodSignature(String methodSig, String aux) {
		int index = aux.lastIndexOf(" ");
		if (index == -1)
			return methodSig.substring(0, methodSig.indexOf(")"));

		while (methodSig.substring(index, index + 1) == " ")
			++index;
		return methodSig.substring(index + 1, methodSig.indexOf(")") + 1);
	}

	private static String getStringBeforeMethodName(String aux) {
		aux = aux.substring(0, aux.lastIndexOf(" "));
		while (aux.substring(aux.length() - 1) == " ")
			aux = aux.substring(0, aux.length() - 1);
		return aux;
	}

	private static String getStringBeforeParenthesis(String methodSig) {
		String aux = methodSig.substring(0, methodSig.indexOf("("));
		while (aux.substring(aux.length() - 1) == " ")
			aux = aux.substring(0, aux.length() - 1);
		return aux;
	}
}
