package in.amankumar110.whatsappapp.utils;

import static android.content.Context.MODE_PRIVATE;
import static in.amankumar110.whatsappapp.utils.Constants.HEADER_COLOR_PREFERENCE_KEY;
import static in.amankumar110.whatsappapp.utils.Constants.MAIN_SCREEN_COLOR_PREFERENCE_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class ColorUtil {


    public static void saveGroupColors(Drawable mainDrawable, Drawable headerDrawable,
                                       Context context) {

        SharedPreferences preferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);  // SharedPreferences initialization

        if(headerDrawable instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) headerDrawable;

            // Extract color from GradientDrawable
            int headerColor = gradientDrawable.getColor().getDefaultColor();
            String colorHex = String.format("#%06X", (0xFFFFFF & headerColor));

            // Save header color in SharedPreferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(HEADER_COLOR_PREFERENCE_KEY, colorHex); // Store color as hex string
            editor.apply();  // Save changes
        }

        if(mainDrawable instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) mainDrawable;

            // Extract color from GradientDrawable
            int mainColor = gradientDrawable.getColor().getDefaultColor();
            String colorHex = String.format("#%06X", (0xFFFFFF & mainColor));

            // Save header color in SharedPreferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(MAIN_SCREEN_COLOR_PREFERENCE_KEY, colorHex); // Store color as hex string
            editor.apply();  // Save changes
        }
    }

    public static int getHeaderColor(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);  // SharedPreferences initialization
        String savedHeaderColor = preferences.getString(HEADER_COLOR_PREFERENCE_KEY, "#FFFFFF"); // Default to white if no color is saved
        int color = Color.parseColor(savedHeaderColor);  // Convert stored string to color
        return color;
    }

    public static int getMainColor(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);  // SharedPreferences initialization
        String savedMainColor = preferences.getString(MAIN_SCREEN_COLOR_PREFERENCE_KEY, "#FFFFFF"); // Default to white if no color is saved
        int color = Color.parseColor(savedMainColor);  // Convert stored string to color
        return color;
    }
}
