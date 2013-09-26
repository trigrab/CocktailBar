package com.venezilu.barkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainActivity extends Activity {

	public static final int PICK_COCKTAIL_REQUEST = 0;
	private static final String COCKTAIL_DETAILS = "com.example.cocktailbar.cocktailDetails";
	private static final String COCKTAIL_SELECTION = "com.example.cocktailbar.cocktailSelection";
	private static final int DELETE_COCKTAIL_REQUEST = 1;
	private ListView mListView;
	private int mSelectedCocktailPosition = -1;
	private CocktailAdapter mCocktailAdapter;
	private int mCocktailCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mCocktailCount = 0;

		// Initialize ListView
        mListView = (ListView) findViewById(R.id.cocktailList);         
        mCocktailAdapter = new CocktailAdapter(this);
        mListView.setAdapter(mCocktailAdapter);
        setOnItemClickListener();
         
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // If the request went well (OK) and the request was PICK_COCKTAIL_REQUEST
	    if (resultCode == Activity.RESULT_OK && requestCode == PICK_COCKTAIL_REQUEST) {
	        Bundle extras = data.getExtras();
	    	if (extras.getInt(COCKTAIL_SELECTION + ".cocktailId") == -1){
	        	Cocktail cocktail = new Cocktail(extras.getString(COCKTAIL_SELECTION + ".name"), extras.getString(COCKTAIL_SELECTION + ".description"), extras.getInt(COCKTAIL_SELECTION + ".imageId"), mCocktailCount);
	        	mCocktailCount++;
	        	mCocktailAdapter.addCocktail(cocktail);
	        	if (mCocktailAdapter.getCount() >= mListView.getLastVisiblePosition()) {
	        		mListView.setScrollbarFadingEnabled(false);
	        	}
	        	mCocktailAdapter.notifyDataSetChanged();
	        }
	    }
	    if (resultCode == Activity.RESULT_OK && requestCode == DELETE_COCKTAIL_REQUEST) {
	    	int cocktailPosition = data.getExtras().getInt(COCKTAIL_DETAILS + ".position");
	    	if (cocktailPosition != -1){
	        	if (mSelectedCocktailPosition != -1){
	        		mCocktailAdapter.removeCocktail(mSelectedCocktailPosition);
	        		mSelectedCocktailPosition = -1;
	        		if (mCocktailAdapter.getCount() < mListView.getLastVisiblePosition()) {
	        			mListView.setScrollbarFadingEnabled(true);
	        		}
	        		mCocktailAdapter.notifyDataSetChanged();
	        	}
	        }
	    }
	}
	
	public void selectCocktail(View view){
		Intent selection = new Intent(this, CocktailSelectionActivity.class);
    	startActivityForResult(selection, PICK_COCKTAIL_REQUEST);
	}
	
	public void showCocktailHistory(View view){
		return;
	}

	private void setOnItemClickListener(){
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				startCocktailDetails(position);
			}
		});
	}
	
	private void startCocktailDetails(int cocktailPosition){
		mSelectedCocktailPosition = cocktailPosition;
		Cocktail cocktail = mCocktailAdapter.getItem(cocktailPosition);
		Intent cocktailDetails = new Intent(this, CocktailDetailsActivity.class);
		cocktailDetails.putExtra(COCKTAIL_DETAILS + ".name", cocktail.getName());
		cocktailDetails.putExtra(COCKTAIL_DETAILS + ".description", cocktail.getDescription());
		cocktailDetails.putExtra(COCKTAIL_DETAILS + ".imageId", (int) cocktail.getImageUri());
		cocktailDetails.putExtra(COCKTAIL_DETAILS + ".position", cocktailPosition);
		startActivityForResult(cocktailDetails, DELETE_COCKTAIL_REQUEST);
	}
	
}


