package com.venezilu.barkeeper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CocktailAdapter extends BaseAdapter {

	private List<Cocktail> mData;
    private LayoutInflater mLayoutInflater;

    public CocktailAdapter(Context context) {
    	mLayoutInflater = LayoutInflater.from(context);
    	mData = new ArrayList<Cocktail>();
    }
    
    public CocktailAdapter(List<Cocktail> cocktail) {
        mData = cocktail;
    }
    
    public void addCocktail(Cocktail cocktail) {
        mData.add(cocktail);
        notifyDataSetChanged();
    }
    
    public boolean removeCocktail(int location){
    	if (mData.get(location) != null) {
    		mData.remove(location);
    		notifyDataSetChanged();
        	return true;
    	}
    	else {
    		return false;
    	}
    }
    
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Cocktail getItem(int location) {
        return mData.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.text_item, parent, false);
        }

        ((TextView) convertView).setText(getItem(position).getName());

        return convertView;
    }
}

