public class WordFreq implements Comparable<Object> {

	private String aWord;
	private int wordCount;
	
	WordFreq(String aWord, int wordCount) {
		this.aWord = aWord;
		this.wordCount = wordCount;
	}
	public String key() {
		return aWord;
	}
	public void setaWord(String aWord) {
		this.aWord = aWord;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	
	@Override
	public String toString() {
		return "Word: " + aWord + ", count: " + wordCount;
	}
	
	@Override
	public int compareTo(Object o) { 
		WordFreq newWord = (WordFreq)o;   
		return this.aWord.compareTo(newWord.key());
	}
	
}
