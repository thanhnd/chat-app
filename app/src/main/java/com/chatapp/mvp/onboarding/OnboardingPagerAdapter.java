package com.chatapp.mvp.onboarding;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chatapp.R;

import java.util.ArrayList;
import java.util.List;

public class OnboardingPagerAdapter extends PagerAdapter {

    private Context context;
    List<OnboardingItem> items;

    public OnboardingPagerAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
        items.add(new OnboardingItem("FREE MESSAGING", "Make your chats come alive with an endless variety ..."));
        items.add(new OnboardingItem("FREE CALL, VIDEO CALL", "Talk with your AppName friends whenever you want for free!"));
        items.add(new OnboardingItem("WELCOME TO THE APPNAME", "Go anywhere & always take AppName with you."));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        OnboardingItem item = items.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.layout_item_onboarding, view, false);
        ((TextView)layout.findViewById(R.id.tv_title)).setText(item.getTitle());
        ((TextView)layout.findViewById(R.id.tv_description)).setText(item.getDescription());
        view.addView(layout);
        return layout;
    }
}