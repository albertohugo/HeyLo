package alberto.hugo.locationalert.adapter;


import alberto.hugo.locationalert.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryImageAdapter extends BaseAdapter 
{
    private Context mContext;

	 private Integer[] mImageIds = {
	            R.drawable.ic_home,
	            R.drawable.ic_friends,
	            R.drawable.ic_love,
	            R.drawable.ic_ok,
	            R.drawable.ic_nok,
	            R.drawable.ic_mark,
	            R.drawable.ic_plane,
	            R.drawable.ic_beach,
	            R.drawable.ic_stadium
	    };


    public GalleryImageAdapter(Context context) 
    {
        mContext = context;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup) 
    {
        ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[index]);
        i.setLayoutParams(new Gallery.LayoutParams(50, 50));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
}