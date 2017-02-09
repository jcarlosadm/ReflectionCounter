package com.reflectCounter.github.api;

public class GithubSearchLimitResultsException extends Exception {
	
	private static final long serialVersionUID = -7772374184660224446L;

	public GithubSearchLimitResultsException(String message) {
		super(message);
	}
	
	public GithubSearchLimitResultsException() {
		super();
	}
}
