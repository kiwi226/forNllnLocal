package model;

public class SearchOption {
	private String searchField;
	private String keyword;
	private String from;
	private String to;
	public String getSearchField() {
		return searchField;
	}
	public String getKeyword() {
		return keyword;
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public void setTo(String to) {
		this.to = to;
	}
}
