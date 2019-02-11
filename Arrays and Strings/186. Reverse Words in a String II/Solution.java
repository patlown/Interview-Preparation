/*
    First though: can iterate through string adding words to a stack.  When
    finished,overwrite char[] by popping words from stack.
*/

class Solution {
    public void reverseWords(char[] str) {
        if(str == null || str.length == 0) return;
        StringBuilder sb = new StringBuilder();
        Stack<String> stack = new Stack();
        
        for(int i = 0; i < str.length; i++){
            if(str[i] != ' ') sb.append(str[i]);
            else{
                stack.push(sb.toString());
                sb = new StringBuilder();
            }
        }
        stack.push(sb.toString());
        int count = 0;
        while(!stack.empty()){
            String curr = stack.pop();
            for(int i =0 ; i < curr.length(); i++){
                str[count] = curr.charAt(i);
                count++;
            }
            if(count < str.length) str[count] = ' ';
            count++;
        }
        
        
    }
}

/*
    Second approach: we can first reverse the entire senetence, then we can
    go through and reverse each word.
*/
class Solution {
    public void reverseWords(char[] str) {
        if(str == null || str.length == 0) return;
        reverse(str,0,str.length-1);
        int i =0;
        int j = 0;
        while(i < str.length){
            while(j < str.length && str[j] != ' ') j++;
            reverse(str,i,j-1);
            j++;
            i = j;
        }
    }
    
    private void reverse(char[] str, int i, int j){
        while(i < j){
            char temp = str[j];
            str[j] = str[i];
            str[i] = temp;
            i++;
            j--;
        }
    }
}