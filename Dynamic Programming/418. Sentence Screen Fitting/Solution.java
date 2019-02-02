/*
    First approach: place the words down within constraints and count. can 
    fit the words one at a time and keep track of count every time we lay down
    a sentence.
    Runtime: number of words that fit in a line * number of lines
    O(n/col * rows)
*/
class Solution {
    public int wordsTyping(String[] sentence, int rows, int cols) {
        int i = 0;
        int count = 0;
        while(rows > 0){
            int col = cols;
            while(col > 0){
                if(col >= sentence[i].length()){
                    col -= sentence[i].length();
                    if(i == sentence.length-1)
                        count++;
                    i = (i+1)%sentence.length;
                    col--;
                }else{
                    break;
                }
            }
            rows--;
        }
        return count;
    }
}

/*
Second appraoch:  find total length of a sentence and place the sentences
down. Implementation:  We need a count that keeps track of where we are in our
sentence.  Add the col length to our count and determine if we are in the middle
of a word.  If we are, then we need backtrack out of the middle of the word to make'
the line fit, if land perfectly on a space. Just add one to our count as we dont
need to start with a space on the next line.  At the end, our answer is simply
the count we have divided by the length of the sentence.

Runtime: O(rows* word length) 'for each row, we may need to retreat at most a word length
so the time is rows *word length'
space: O(sentence length)
*/
class Solution {
    public int wordsTyping(String[] sentence, int rows, int cols) {
        
        StringBuilder sb = new StringBuilder();
        for(String word : sentence){
            sb.append(word);
            sb.append(" ");
        }
        String s = sb.toString();
        int count = 0;
        int n = s.length();
        for(int i = 0; i < rows; i++){
            count += cols;
            if(s.charAt(count%n) == ' '){
                count++;
            }else{
                while(count > 0 && s.charAt((count-1)%n) != ' '){
                    --count;
                }
            }
        }
        return count/n;
        
    }
}