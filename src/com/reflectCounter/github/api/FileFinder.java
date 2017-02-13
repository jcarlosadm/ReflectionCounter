package com.reflectCounter.github.api;

import java.util.ArrayList;
import java.util.List;

import javax.naming.LimitExceededException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FileFinder {
	private static final String INSUFFICIENT_INFORMATION_EXCEPTION_MESSAGE = "insufficient information for search.";

	private static final String URL_PREFIX = "https://api.github.com/search/code?q=";

	private static final String URL_SUFFIX = "&per_page=100&page=";

	private String searchTerm = "";
	private String language = "";
	private String reponame = "";
	private String username = "";

	private String url = "";

	public FileFinder(SearchTerms searchTerms) throws Exception {

		this.searchTerm = searchTerms.getMainSearchTerm();
		this.language = searchTerms.getLanguage();
		this.reponame = searchTerms.getReponame();
		this.username = searchTerms.getAuthorname();

		if (this.searchTerm == null || this.searchTerm.isEmpty() || this.reponame == null || this.reponame.isEmpty()
				|| this.username == null || this.username.isEmpty())
			throw new Exception(INSUFFICIENT_INFORMATION_EXCEPTION_MESSAGE);

		this.buildUrl();
	}

	private void buildUrl() {
		StringBuilder url = new StringBuilder(URL_PREFIX);

		url.append(this.searchTerm.replaceAll(" ", "%20"));
		
		if (this.language != null && !this.language.isEmpty())
			url.append("+language:" + this.language);
		
		url.append("+in:file+repo:" + this.username + "/" + this.reponame);
		
		this.url = url.toString();
	}

	public List<String> run() throws LimitExceededException, Exception {
		List<String> list = new ArrayList<>();
		int page = 1;

		GithubSearchApi githubSearchApi = GithubSearchApi.getInstance(true);
		JSONObject json = githubSearchApi.getJsonObject(this.url + URL_SUFFIX + page);

		int total = this.getNumberOfFiles(json);
		if (total > 1000)
			throw new LimitExceededException("Over 1000 results in this search.");

		int remains = total;
		while (remains > 0) {
			if (remains < total)
				json = githubSearchApi.getJsonObject(this.url + URL_SUFFIX + page);

			JSONArray jsonArray = (JSONArray) json.get("items");

			for (Object obj : jsonArray) {
				JSONObject jsonObj = (JSONObject) obj;

				list.add((String) jsonObj.get("path"));
			}

			++page;
			remains = this.getNewRemains(remains);
		}

		return list;
	}

	private int getNewRemains(int remains) {
		return (remains > 100 ? remains - 100 : 0);
	}

	private int getNumberOfFiles(JSONObject json) {
		Long value = (Long) json.get("total_count");
		
		if (value == null || value.longValue() <= 0)
			return 0;
		else if (value.longValue() > 1000)
			return 1001;
		
		return value.intValue();
	}
}
