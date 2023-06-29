
public class SearchResult {
	boolean result;
	int depth;
	
	public SearchResult(boolean result, int depth) {
		this.result=result;
		this.depth=depth;
	}

	public SearchResult(SearchResult searchResult) {
		this.result=searchResult.result;
		this.depth=searchResult.depth;
	}

}
