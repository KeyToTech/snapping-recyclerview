# snapping-recyclerview

Snapping RecyclerView is a custom RecyclerView that provides "center-snap" behavior.
- After a scroll, the item closest to the centre will be automatically scrolled to the centre.
- You can set first start item position. List will scroll to this position and this item will be scrolled to the center.
- on click you can select item and it will scroll to center

# Gradle



# Usage
Step 1: 

              <com.keytotech.snapping_recyclerview.SnappingRecyclerView
                      android:id="@+id/item_picker"
                      android:layout_width="match_parent"
                      android:layout_height="wrap_content">
              </com.keytotech.snapping_recyclerview.SnappingRecyclerView>
 
 Step 2: in your Activity or Fragment you must set the orientation :
        
        viewManager.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager = viewManager
        
   and set item size and padding:
        
        val itemWidth = resources.getDimension(R.dimen.item_width)
        val paddingItemWidth = resources.getDimension(R.dimen.padding_item_width)
        initDimensions(itemWidth, paddingItemWidth, this@MainActivity)
        
Step 3: you can implement the inteface SnappingRecyclerView.InteractionListener and implement method: 
        
        override fun onItemSelected(position: Int, previousCalculatedPosition: Int) {}
        
For more details, you can read the demo of the project.


 
