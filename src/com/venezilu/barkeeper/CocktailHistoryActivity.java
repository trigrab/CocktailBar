package com.venezilu.barkeeper;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CocktailHistoryActivity extends Activity {

	private ListView mCocktailList;
	private LinearLayout mCocktailListView;
	private ViewStub mEmptyView;
	private int mSelectedCocktailPosition = -1;
	private CocktailAdapter mCocktailAdapter;
	private ImageView mCocktailImage;
	private TextView mCocktailName;
	private TextView mCocktailDescription;
	private TextView mCocktailId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cocktail_history);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Initialize Views
		mCocktailListView = (LinearLayout) findViewById(R.id.cocktailHistoryListView);
		mCocktailList = (ListView) findViewById(R.id.cocktailHistoryList); 
        mEmptyView = (ViewStub) findViewById(R.id.emptyHistory);
        
        // Initialize CocktailDetails
        mCocktailImage = (ImageView) findViewById(R.id.cocktailHistoryLogo);
		mCocktailName = (TextView) findViewById(R.id.cocktailHistoryName);
		mCocktailDescription = (TextView) findViewById(R.id.cocktailHistoryDescription);
		mCocktailId = (TextView) findViewById(R.id.cocktailHistoryId);
		
		// Initialize CocktailList
		mCocktailAdapter = new CocktailAdapter(this);
        mCocktailList.setAdapter(mCocktailAdapter);
        setOnItemClickListener();
        
        // Initialize which view is visible
        mEmptyView.inflate();
        if (mCocktailAdapter.getCount() <= 0){
        	setEmptyView();
        }
        else {
        	setListView();
        }
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cocktail_history, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		if (mCocktailAdapter.getCount() <= 0){
        	setEmptyView();
        }
        else {
        	setListView();
        }
	    super.onResume();

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setOnItemClickListener(){
		mCocktailList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				selectCocktailByPosition(position);
			}
		});
	}
	
		
	private void selectCocktailByPosition(int position){
		mSelectedCocktailPosition = position;
		Cocktail cocktail = mCocktailAdapter.getItem(position);
		mCocktailDescription.setText(cocktail.getDescription());
		mCocktailImage.setImageResource(cocktail.getImageUri());
		mCocktailName.setText(cocktail.getName());
		mCocktailId.setText(Integer.toString(cocktail.getId()));
	}

	private void setEmptyView() {
		mCocktailListView.setVisibility(View.INVISIBLE);
		mEmptyView.setVisibility(View.VISIBLE);
	}
	
	private void setListView() {
		mCocktailListView.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.INVISIBLE);
	}
	
	public void setCocktailUnfinished(View view){
		mCocktailAdapter.removeCocktail(mSelectedCocktailPosition);
		if (mSelectedCocktailPosition <= 0) {
			if (mCocktailAdapter.getCount() <= 0) {
				setEmptyView();
				mSelectedCocktailPosition--;
			}
			else {
				selectCocktailByPosition(mSelectedCocktailPosition);
			}
		}
		else {
			mSelectedCocktailPosition--;
			selectCocktailByPosition(mSelectedCocktailPosition);
		}
	}

}


