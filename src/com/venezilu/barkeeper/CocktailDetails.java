package com.venezilu.barkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class CocktailDetails extends Activity {

	private static final String COCKTAIL_DETAILS_NAME = "com.example.cocktailbar.cocktailName";
	private ImageView mPicture;
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
		mPicture.setImageResource(R.drawable.vodka_martini);
		Intent intent = getIntent();
		mName.setText(intent.getExtras().get(COCKTAIL_DETAILS_NAME).toString());
		if (mName.getText().equals("Pina Colada")){
			mDescription.setText("3cl white rum\n" + "3cl cream of coconut\n" + "9cl pineapple juice\n");
		}
		else if (mName.getText() == "Caipirinha"){
			mDescription.setText("5cl cachaça\n" + "1/2 Lime cut into 4 wedges\n" + "2 teaspoons crystal or refined sugar\n");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cocktail_details, menu);
		return true;
	}
	
	public void finish(View view){
		Intent cocktail = new Intent();
		cocktail.putExtra(COCKTAIL_DETAILS_NAME, mName.getText());
		setResult(RESULT_OK, cocktail);
		finish();
	}

}
