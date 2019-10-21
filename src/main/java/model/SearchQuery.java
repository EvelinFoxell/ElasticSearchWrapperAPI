package model;

public class SearchQuery {

    private String userSearchInput;
    private String sortFlag; //Enum for search options?

    public SearchQuery() {}

    public SearchQuery(String userSearchInput, String sortFlag) {
        this.userSearchInput = userSearchInput;
        this.sortFlag = sortFlag;
    }

    public String getUserSearchInput() {
        return userSearchInput;
    }

    public void setUserSearchInput(String userSearchInput) {
        this.userSearchInput = userSearchInput;
    }

    public String getSortFlag() {
        return sortFlag;
    }

    public void setSortFlag(String sortFlag) {
        this.sortFlag = sortFlag;
    }
}
