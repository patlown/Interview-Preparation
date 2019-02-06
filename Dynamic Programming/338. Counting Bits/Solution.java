/*
    Simple first solution: iterate through the numbers 0-n and count the bits
    in each number.  This can be achieved by left shifting a 1 up to 31 bits
    to catch all possible in a 32 bit number.  

*/
class Solution {
    public int[] countBits(int num) {
        int[] res = new int[num+1];
        res[0] = 0;
        if(num == 0) return res;
        res[1] = 1;
        for(int i = 2; i <= num; i++){
            int count = 0;
            for(int j = 0; j < 32; j++){
                if( ((1 << j) & i) > 0) count++;
            }
            res[i] = count;
        }
        
        return res;
        
    }
}
/*
    More efficient approach: we can notice taht every power of 2 just has
    1 bit so all powers of 2 can easibly be set to 1.  Additionally, we can notice
    that we don't really need the highest order bit. For example:
    
    1 - 0001 -> b(1) = 1
    2 - 0010 -> b(2) = 1
    3 - 0011 -> b(3) = b(2) + b(1)
    4 - 0100 -> b(4) = 1
    5 - 0101 -> b(5) = b(4) + b(1)
    6 - 0110 -> b(6) = b(4) + b(2)
    7 - 0111 -> b(7) = b(4) + b(3)
    8 - 1000 -> b(8) = 1

    Looking at this chart, we can see the pattern arise, b(5) is just b(4) which
    is 1 + the number of bits in number 1.  This is essentailly disregarding the highest
    order bit and just using the previously calculated answers.  This works because
    by the time we get a new higher order bit, we have computed all the combinations
    of lower bits before it so we can use the fact that our new highest order bit
    just adds 1 to all those previously computed values.
*/
class Solution {
    public int[] countBits(int num) {
        
        int[] res = new int[num+1];
        int x = 1, p = 1;
        for(int i =1; i < res.length; i++){
            if(i == x){
                res[i] = 1;
                x *= 2;
                p = 1;
            }else{
                res[i] = 1 + res[p];
                p++;
            }
        }
        return res;
    }
}