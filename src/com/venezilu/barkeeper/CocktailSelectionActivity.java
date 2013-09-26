package com.venezilu.barkeeper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CocktailSelectionActivity extends Activity {

	private static final String COCKTAIL_SELECTION = "com.example.cocktailbar.cocktailSelection";
	private ListView mListView;
	private CocktailAdapter mCocktailAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selection);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Initialize ListView
        mListView = (ListView) findViewById(R.id.selectableCocktails);         
        mCocktailAdapter = new CocktailAdapter(this);
        
        mCocktailAdapter.addCocktail(new Cocktail("Caipirinha",
        							 "50 ml cachaça\n" + "1/2 Lime cut into 4 wedges\n" + "2 teaspoons crystal or refined sugar\n",
        							 R.drawable.caipirinha, -1));
        mCocktailAdapter.addCocktail(new Cocktail("Pina Colada",
        		"30 ml (one part) white rum" + "30 ml (one part) cream of coconut" + "90 ml (3 parts) pineapple juice",
				 R.drawable.pina_colada, -1));
        mListView.setAdapter(mCocktailAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				select((Cocktail) mListView.getItemAtPosition(position));				
			}
		});
        
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
		getMenuInflater().inflate(R.menu.selection, menu);
		return true;
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
	
	private void select(Cocktail cocktail) {
		Intent data = new Intent();
		data.putExtra(COCKTAIL_SELECTION + ".name", cocktail.getName());
		data.putExtra(COCKTAIL_SELECTION + ".description", cocktail.getDescription());
		data.putExtra(COCKTAIL_SELECTION + ".imageId", cocktail.getImageUri());
		data.putExtra(COCKTAIL_SELECTION + ".cocktailId", cocktail.getId());
		setResult(RESULT_OK, data);
		finish();
	}

}
