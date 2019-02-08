Update: If we sort the input array in ascending order, this problem can 
actually be rephrased as finding the kth smallest element in a sorted matrix, 
where the matrix element at position (i, j) is given by 
matrix[i][j] = nums[j] - nums[i], and k = K + n(n+1)/2 with n = nums.length. 
It then can be solved using multiple algorithms developed in my other post 
(PriorityQueue, BinarySearch, ZigzagSearch).

Well, normally we would refrain from using the naive trial and error algorithm for solving problems since it generally 
leads to bad time performance. 
However, there are situations where this naive algorithm may outperform other more sophisticated solutions, 
and LeetCode does have a few such problems (listed at the end of this post -- ironically most of them are "hard" problems). 
So I figure it might be a good idea to bring it up and describe a general procedure for applying this algorithm.

The basic idea for the trial and error algorithm is actually very simple and summarized below:

Step 1: Construct a candidate solution.
Step 2: Verify if it meets our requirements.
Step 3: If it does, accept the solution; else discard it and repeat from Step 1.

However, to make this algorithm work efficiently, the following two conditions need to be true:

Condition 1: We have an efficient verification algorithm in Step 2;
Condition 2: The search space formed by all candidate solutions is small or we have efficient ways to traverse (or search) 
    this space if it is large.

The first condition ensures that each verification operation can be done quickly while the second condition limits the total number 
of such operations that need to be done. 
The two combined will guarantee that we have an efficient trial and error algorithm (which also means if any of them cannot be 
satisfied, you should probably not even consider this algorithm).

Now let's look at this problem: 719. Find The K-th Smallest Pair Distance, and see how we can apply the trial and error algorithm.


I -- Construct a candidate solution

To construct a candidate solution, we need to understand first what the desired solution is. The problem description requires 
we output the K-th smallest pair distance, which is nothing more than a non-negative integer (since the input array nums 
is an integer array and pair distances are absolute values). Therefore our candidate solution should also be a non-negative integer.


II -- Search space formed by all the candidate solutions

Let min and max be the minimum and maximum numbers in the input array nums, and d = max - min, 
then any pair distance from nums must lie in the range [0, d]. As such, our desired solution is also within this range, which implies 
the search space will be [0, d] (any number outside this range can be ruled out immediately without further verification).


III -- Verify a given candidate solution

This is the key part of this trial and error algorithm. So given a candidate integer, how do we determine if it is the 
K-th smallest pair distance?

First, what does the K-th smallest pair distance really mean? By definition, if we compute all the pair distances and sort them in 
ascending order, then the K-th smallest pair distance will be the one at index K - 1. This is essentially the naive way for 
solving this problem (but will be rejected due to MLE, as expected).

Apparently the above definition cannot be used to do the verification, as it requires explicit computation of the pair distance array. 
Fortunately there is another way to define the K-th smallest pair distance: given an integer num, let count(num) denote the number 
of pair distances that are no greater than num, then the K-th smallest pair distance will be the smallest integer 
such that count(num) >= K.

Here is a quick justification of the alternative definition. Let num_k be the K-th pair distance in the sorted pair distance array 
with index K - 1, as specified in the first definition. Since all the pair distances up to index K - 1 are no greater than num_k, 
we have count(num_k) >= K. Now suppose num is the smallest integer such that count(num) >= K, 
we show num must be equal to num_k as follows:

    If num_k < num, since count(num_k) >= K, then num will not be the smallest integer such that count(num) >= K, 
    which contradicts our assumption.

    If num_k > num, since count(num) >= K, by definition of the count function, there are at least K pair distances that are 
    no greater than num, which implies there are at least K pair distances that are smaller than num_k. 
    This means num_k cannot be the K-th pair distance, contradicting our assumption again.

Taking advantage of this alternative definition of the K-th smallest pair distance, we can transform the verification process into 
a counting process. So how exactly do we do the counting?


IV -- Count the number of pair distances no greater than the given integer

As I mentioned, we cannot use the pair distance array, which means the only option is the input array itself. If there is no 
order among its elements, we get no better way other than compute and test each pair distance one by one. This leads to a O(n^2) 
verification algorithm, which is as bad as, if not worse than, the aforementioned naive solution. 
So we need to impose some order to nums, which by default means sorting.

Now suppose nums is sorted in ascending order, how do we proceed with the counting for a given number num? Note that 
each pair distance d_ij is characterized by a pair of indices (i, j) with i < j, that is d_ij = nums[j] - nums[i]. If we keep the 
first index i fixed, then d_ij <= num is equivalent to nums[j] <= nums[i] + num. This suggests that at least we can do a 
binary search to find the smallest index j such that nums[j] > nums[i] + num for each index i, 
then the count from index i will be j - i - 1, and in total we have an O(nlogn) verification algorithm.

It turns out the counting can be done in linear time using the classic two-pointer technique if we make use of the following property: 
assume we have two starting indices i1 and i2 with i1 < i2, let j1 and j2 be the smallest index such that nums[j1] > nums[i1] + num and 
nums[j2] > nums[i2] + num, respectively, then it must be true that j2 >= j1. The proof is straightforward: suppose j2 < j1, 
since j1is the smallest index such that nums[j1] > nums[i1] + num, we should have nums[j2] <= nums[i1] + num. 
On the other hand, nums[j2] > nums[i2] + num >= nums[i1] + num. 
The two inequalities contradict each other, thus validate our conclusion above.


V -- How to traverse (or search) the search space efficiently

Up to this point, we know the search space, know how to construct the candidate solution and how to verify it by counting, we still 
need one last piece for the puzzle: how to traverse the search space.

Of course we can do the naive linear walk by trying each integer from 0 up to d and choose the first integer num such that count(num) >= K. 
The time complexity will be O(nd). However, given that d can be much larger than n, this algorithm can be much worse than 
the naive O(n^2) solution mentioned before.

The key observation here is that the candidate solutions are sorted naturally in ascending order, so a binary search is possible. 
Another fact is the non-decreasing property of the count function: given two integers num1 and num2 such that num1 < num2, 
we have count(num1) <= count(num2) (I will leave the verification to you). So a binary walk of the search space will look like this:

    Let [l, r] be the current search space, and initialize l = 0, r = d.
    If l < r, compute the middle point m = (l + r) / 2 and evaluate count(m).
    If count(m) < K, we throw away the left half of current search space and set l = m + 1; else if count(m) >= K we throw 
    away the right half and set r = m.

You probably will wonder why we throw away the right half of the search space even if count(m) == K. Note that the K-th smallest 
pair distance num_k is the minimum integer such that count(num_k) >= K. If count(m) == K, then we know num_k <= m 
(but not necessarily num_k == m, think about it!) so it makes no sense keeping the right half.


VI -- Putting everything together, aka, solutions

The time complexity is O(nlogd + nlogn) (don't forget the sorting) and space complexity is O(1).

Time: O(nlogn) to sort, we do n passes over that nums array logd times so O(nlogd) for total time of : O(nlogd + nlogn)

class Solution {
    public int smallestDistancePair(int[] nums, int k) {
    Arrays.sort(nums);
    
    int n = nums.length;
    int l = 0;
    int r = nums[n - 1] - nums[0];
    
    for (int cnt = 0; l < r; cnt = 0) {
        int m = l + (r - l) / 2;
        
        for (int i = 0, j = 0; i < n; i++) {
            while (j < n && nums[j] <= nums[i] + m) j++;
            cnt += j - i - 1;
        }
        
        if (cnt < k) {
            l = m + 1;
        } else {
            r = m;
        }
    }
    
    return l;
    }
}