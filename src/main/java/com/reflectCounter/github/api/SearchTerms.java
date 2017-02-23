package com.reflectCounter.github.api;

public class SearchTerms {

	private String reponame = "";
	private String authorname = "";
	private String language = "";
	private String mainSearchTerm = "";

	public String getReponame() {
		return reponame;
	}

	public void setReponame(String reponame) {
		this.reponame = reponame;
	}

	public String getAuthorname() {
		return authorname;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMainSearchTerm() {
		return mainSearchTerm;
	}

	public void setMainSearchTerm(String mainSearchTerm) {
		this.mainSearchTerm = mainSearchTerm;
	}

}
