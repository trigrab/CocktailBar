package com.venezilu.barkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class CocktailDetailsActivity extends Activity {

	private static final String COCKTAIL_DETAILS = "com.example.cocktailbar.cocktailDetails";
	private ImageView mPicture;
	private int mCocktailPosition;
	private TextView mName;
	private TextView mDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cocktail_details);
		
		
		
		// Initialize View
		mPicture = (ImageView) findViewById(R.id.cocktailLogo);
		mName = (TextView) findViewById(R.id.cocktailName);
		mDescription = (TextView) findViewById(R.id.cocktailDescription);
		
		Intent intent = getIntent();
		mCocktailPosition = intent.getExtras().getInt(COCKTAIL_DETAILS + ".position");
		mPicture.setImageResource(intent.getExtras().getInt(COCKTAIL_DETAILS + ".imageId"));
		mName.setText(intent.getExtras().getString(COCKTAIL_DETAILS + ".name"));
		mDescription.setText(intent.getExtras().getString(COCKTAIL_DETAILS + ".description"));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cocktail_details, menu);
		return true;
	}
	
	public void finish(View view){
		Intent cocktail = new Intent();
		cocktail.putExtra(COCKTAIL_DETAILS + ".position", mCocktailPosition);
		setResult(RESULT_OK, cocktail);
		finish();
	}
	
	public void goBack(View view){
		setResult(RESULT_CANCELED);
		finish();
	}

}
