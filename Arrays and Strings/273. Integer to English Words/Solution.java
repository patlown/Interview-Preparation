/*

    We need to keep all the numbers under 20, all tens possibilities and all
    thousands possibilities.  We can store these in String[] arrays where the indices
    match the elements so a number 1 matches less_than_20[1] and a number
    10 matches tens[1].  We can then implement our solution:

    while the number is greater than 0, we cant to check if we can split to
    its 3 lower order digits, this can be achieved by taking the mod of the number
    with 1000.  We can keep track of our thousands place with an i counter that
    increments every time we divide the number by 1000. Once we have isolated 3 or less
    numbers, we can pass them to our helper function:

        the helper function will check if our number is 0 or less than 20, if so,
        it simply returns the number from our table.  If the number is > 20 but less than
        100 we can retrieve our answer by first putting down our tens string and then
        putting down our 1s string.  If the number is > 100, we must handle the 
        first digit as a less than 20 and our last two digits tje same way as above.
    
        return the final string created from these calls
    Keep doing this until our number is divided by 1000 into 0.
*/

class Solution {
    private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};

    public String numberToWords(int num) {
        if (num == 0) return "Zero";

        int i = 0;
        String words = "";

        while (num > 0) {
            if (num % 1000 != 0)
                words = helper(num % 1000) +THOUSANDS[i] + " " + words;
            num /= 1000;
            i++;
        }

        return words.trim();
    }

    private String helper(int num) {
        if (num == 0)
            return "";
        else if (num < 20)
            return LESS_THAN_20[num] + " ";
        else if (num < 100)
            return TENS[num / 10] + " " + helper(num % 10);
        else
            return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
    }
}