package com.venezilu.barkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	public static final int PICK_COCKTAIL_REQUEST = 0;
	private static final String COCKTAIL_DETAILS_NAME = "com.example.cocktailbar.cocktailName";
	private static final int DELETE_COCKTAIL_REQUEST = 1;
	private ListView mListView;
	SimpleAdapter mListAdapter;
	private ArrayAdapter<String> mNameListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		// Initialize ListView
        mListView = (ListView) findViewById(R.id.cocktailList);         
        mNameListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(mNameListAdapter);
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
	        if (data.getExtras().getString("Cocktail") != ""){
	        	mNameListAdapter.add(data.getExtras().getString("Cocktail"));
	        	if (mNameListAdapter.getCount() >= mListView.getLastVisiblePosition()) {
	        		mListView.setScrollbarFadingEnabled(false);
	        	}
	        	mNameListAdapter.notifyDataSetChanged();
	        }
	    }
	    if (resultCode == Activity.RESULT_OK && requestCode == DELETE_COCKTAIL_REQUEST) {
	    	String cocktail = data.getExtras().getString(COCKTAIL_DETAILS_NAME);
	    	if (cocktail != ""){
	        	if (mNameListAdapter.getPosition(cocktail) != -1){
	        		mNameListAdapter.remove(cocktail);
	        		mNameListAdapter.notifyDataSetChanged();
	        	
	        		if (mNameListAdapter.getCount() < mListView.getLastVisiblePosition()) {
	        			mListView.setScrollbarFadingEnabled(true);
	        		}
	        		mNameListAdapter.notifyDataSetChanged();
	        	}
	        }
	    }
	}
	
	public void selectCocktail(View view){
		Intent selection = new Intent(this, Selection.class);
    	startActivityForResult(selection, PICK_COCKTAIL_REQUEST);
	}

	private void setOnItemClickListener(){
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				startCocktailDetails(mListView.getItemAtPosition(position).toString());
				
						
			}
		});
	}
	
	private void startCocktailDetails(String cocktail){
		Intent cocktailDetails = new Intent(this, CocktailDetails.class);
		cocktailDetails.putExtra(COCKTAIL_DETAILS_NAME, cocktail);
		startActivityForResult(cocktailDetails, DELETE_COCKTAIL_REQUEST);
	}
}


/**

}
**/