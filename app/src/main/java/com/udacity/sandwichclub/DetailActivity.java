package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnown;
    private TextView mDescription;
    private TextView mIngredients;
    private TextView mOrigin;
    private LinearLayout mAlsoKnownLL;
    private LinearLayout mDescriptionLL;
    private LinearLayout mIngredientsLL;
    private LinearLayout mOriginLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAlsoKnown = (TextView) findViewById(R.id.also_known_tv);
        mDescription = (TextView) findViewById(R.id.description_tv);
        mOrigin = (TextView) findViewById(R.id.origin_tv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        mAlsoKnownLL = (LinearLayout) findViewById(R.id.also_known_ll);
        mDescriptionLL = (LinearLayout) findViewById(R.id.description_ll);
        mIngredientsLL = (LinearLayout) findViewById(R.id.ingredients_ll);
        mOriginLL = (LinearLayout) findViewById(R.id.origin_ll);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Fill in the data for the sandwich

        // Also known as
        String alsoKnownString = "";
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs.isEmpty()) {
            mAlsoKnownLL.setVisibility(View.GONE);
        } else {
            for (String alias : alsoKnownAs
                    ) {
                alsoKnownString += alias + "\n";
            }
            alsoKnownString = alsoKnownString.substring(0, alsoKnownString.length() - 1);
            mAlsoKnown.setText(alsoKnownString);
            mAlsoKnownLL.setVisibility(View.VISIBLE);
        }

        // Origin
        String origin = sandwich.getPlaceOfOrigin();
        if (origin.length() < 1) {
            mOriginLL.setVisibility(View.GONE);
        } else {
            mOriginLL.setVisibility(View.VISIBLE);
        }
        mOrigin.setText(origin);

        // Description
        String description = sandwich.getDescription();
        if (description.length() < 1) {
            mDescriptionLL.setVisibility(View.GONE);
        } else {
            mDescription.setText(sandwich.getDescription());
            mDescriptionLL.setVisibility(View.VISIBLE);
        }

        // Ingredients
        List<String> ingredients = sandwich.getIngredients();
        String ingredientsString = "";
        if (ingredients.isEmpty()) {
            mIngredientsLL.setVisibility(View.GONE);
        } else {
            for (String ingredient : ingredients) {
                ingredientsString += ingredient + "\n";
            }
            ingredientsString = ingredientsString.substring(0, ingredientsString.length() - 1);
            mIngredients.setText(ingredientsString);
            mIngredientsLL.setVisibility(View.VISIBLE);
        }
    }
}
