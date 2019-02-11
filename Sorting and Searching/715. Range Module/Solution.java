/*
  Approach: -Need an Interval class implementation
  Maintain a sorted set of disjoint intervals.  A TreeSet is a useful structure
  for storing such intervals.  We can compare intervals by rightmost coordinates
  We can add and remove ranges in similar ways.  First, we must iterate over the 
  relevant subset of our ranges.  We'll use an iterator so we can remove intervals
  on the fly.
*/

class RangeModule {
    TreeSet<Interval> ranges;
    public RangeModule() {
        ranges = new TreeSet();
    }
    /*
        make left, right the smallest and largest seen interval values.  Remove all
        intervals in between and add the union of them back.  
    */
    public void addRange(int left, int right) {
        Iterator<Interval> itr = ranges.tailSet(new Interval(0,left-1)).iterator();
        while(itr.hasNext()){
            Interval iv = itr.next();
            if(right < iv.left) break;
            left = Math.min(left,iv.left);
            right = Math.max(right,iv.right);
            itr.remove();
        }
        ranges.add(new Interval(left,right));
    }
    /*
        Use the higher() function which returns the closest interval that has a
        right that is strictly greater than the right that is searched.  We can 
        leverage this to search for a interval that has a right that is greater 
        than the queried range.  We can then return whether or not our query is
        is within range of this interval.
    */
    public boolean queryRange(int left, int right) {
        Interval iv = ranges.higher(new Interval(0,left));
        return (iv != null && iv.left <= left && right <= iv.right);
    }
    /*
        As we remove intervals that overlap with the range, we need to save
        the intervals that do not overlap with the range.  Thus, we build a todo
        list that stores all the intervals that must be added back to the set.
    */
    public void removeRange(int left, int right) {
        Iterator<Interval> itr = ranges.tailSet(new Interval(0,left)).iterator();
        List<Interval> todo = new ArrayList();
        while(itr.hasNext()){
            Interval iv = itr.next();
            if(right < iv.left) break;
            if(iv.left < left) todo.add(new Interval(iv.left,left));
            if(right < iv.right) todo.add(new Interval(right,iv.right));
            itr.remove();
        }
        for(Interval iv: todo) ranges.add(iv);
    }
    /*
        implement a comparable interface so that we may order our intervals by 
        right most point.

    */
    class Interval implements Comparable<Interval>{
        int left; 
        int right;
        
        public Interval(int left, int right){
            this.left = left;
            this.right = right;
        }
        
        public int compareTo(Interval that){
            if(this.right == that.right) return this.left - that.left;
            return this.right - that.right;
        }
    }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */