package com.uas.sipami.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.uas.sipami.R;

public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private int[] imageIds = {R.drawable.sawah, R.drawable.sensus, R.drawable.survei7, R.drawable.survei3, R.drawable.survei5};

    public ImageSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_image, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageIds[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
