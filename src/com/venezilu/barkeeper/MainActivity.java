package com.venezilu.barkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainActivity extends Activity {

	public static final int PICK_COCKTAIL_REQUEST = 0;
	private static final String COCKTAIL_DETAILS = "com.example.cocktailbar.cocktailDetails";
	private static final String COCKTAIL_SELECTION = "com.example.cocktailbar.cocktailSelection";
	private static final int DELETE_COCKTAIL_REQUEST = 1;
	private Intent mCocktailHistoryIntent;
	private ListView mCocktailList;
	private LinearLayout mCocktailListView;
	private ViewStub mEmptyView;
	private int mSelectedCocktailPosition = -1;
	private CocktailAdapter mCocktailAdapter;
	private int mCocktailCount;
	private boolean mIsEmpty = true;
	private ImageView mCocktailImage;
	private TextView mCocktailName;
	private TextView mCocktailDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mCocktailCount = 0;

		// Initialize CocktailHistory
		mCocktailHistoryIntent = new Intent(this.getBaseContext(), CocktailHistoryActivity.class);
		
		// Initialize Views
		mCocktailListView = (LinearLayout) findViewById(R.id.cocktailListView);
		mCocktailList = (ListView) findViewById(R.id.cocktailList); 
        mEmptyView = (ViewStub) findViewById(R.id.empty);
        
        // Initialize CocktailDetails
        mCocktailImage = (ImageView) findViewById(R.id.cocktailLogo);
		mCocktailName = (TextView) findViewById(R.id.cocktailName);
		mCocktailDescription = (TextView) findViewById(R.id.cocktailDescription);
        
		// Initialize CocktailList
		mCocktailAdapter = new CocktailAdapter(this);
        mCocktailList.setAdapter(mCocktailAdapter);
        setOnItemClickListener();
        
        // Initialize which view is visible
        mEmptyView.inflate();
        setEmptyView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    
		// If the request went well (OK)
	    if (resultCode == Activity.RESULT_OK) {
	    	// If the request was PICK_COCKTAIL_REQUEST
	    	if (resultCode == Activity.RESULT_OK && requestCode == PICK_COCKTAIL_REQUEST) {
		        Bundle extras = data.getExtras();
		    	if (extras.getInt(COCKTAIL_SELECTION + ".cocktailId") == -1){
		        	Cocktail cocktail = new Cocktail(extras.getString(COCKTAIL_SELECTION + ".name"), extras.getString(COCKTAIL_SELECTION + ".description"), extras.getInt(COCKTAIL_SELECTION + ".imageId"), mCocktailCount);
		        	mCocktailCount++;
		        	mCocktailAdapter.addCocktail(cocktail);
		        	selectCocktailByPosition(mCocktailAdapter.getCount() -1);
		        	mCocktailAdapter.notifyDataSetChanged();
		        	mSelectedCocktailPosition = mCocktailAdapter.getCount() -1;
		        	
		        	// Set visibility of scrollbar if there are two many cocktails for screen
		        	if (mCocktailAdapter.getCount() >= mCocktailList.getLastVisiblePosition()) {
		        		mCocktailList.setScrollbarFadingEnabled(false);
		        	}
		        	
		        }
		    }
		    
	    	// If the request was DELETE_COCKTAIL_REQUEST
		    if (resultCode == Activity.RESULT_OK && requestCode == DELETE_COCKTAIL_REQUEST) {
		    	int cocktailPosition = data.getExtras().getInt(COCKTAIL_DETAILS + ".position");
		    	if (cocktailPosition != -1){
		        	if (mSelectedCocktailPosition != -1){
		        		mCocktailAdapter.removeCocktail(mSelectedCocktailPosition);
		        		mSelectedCocktailPosition = -1;
		        		if (mCocktailAdapter.getCount() < mCocktailList.getLastVisiblePosition()) {
		        			mCocktailList.setScrollbarFadingEnabled(true);
		        		}
		        		mCocktailAdapter.notifyDataSetChanged();
		        	}
		        }
		    }
		    
		    // If list is empty, set the view for empty list.
			if (mIsEmpty) {
		    	setListView();
		    }
	    }
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
	}

	private void setEmptyView() {
		mCocktailListView.setVisibility(View.INVISIBLE);
		mEmptyView.setVisibility(View.VISIBLE);
		mIsEmpty = true;
	}
	
	private void setListView() {
		mCocktailListView.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.INVISIBLE);
		mIsEmpty = false;
	}
	
	public void selectCocktail(View view){
		Intent selection = new Intent(this.getBaseContext(), CocktailSelectionActivity.class);
    	startActivityForResult(selection, PICK_COCKTAIL_REQUEST);
	}
	
	public void showCocktailHistory(View view){
		// TODO make new view for cocktailhistory and start it here.
		startActivityForResult(mCocktailHistoryIntent, DELETE_COCKTAIL_REQUEST);
	}
	
	public void finishCocktail(View view){
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


