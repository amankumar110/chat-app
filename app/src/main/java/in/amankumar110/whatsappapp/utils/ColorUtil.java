package in.amankumar110.whatsappapp.utils;

import static android.content.Context.MODE_PRIVATE;
import static in.amankumar110.whatsappapp.utils.Constants.HEADER_COLOR_PREFERENCE_KEY;
import static in.amankumar110.whatsappapp.utils.Constants.MAIN_SCREEN_COLOR_PREFERENCE_KEY;
import static in.amankumar110.whatsappapp.utils.Constants.TEXT_COLOR_PREFERENCE_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import in.amankumar110.whatsappapp.R;

public class ColorUtil {


    public static void saveGroupColors(Drawable mainDrawable, Drawable headerDrawable, boolean useLightText,
                                       Context context) {

        String colorTextString;

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

        if(useLightText) {
            int lightTextColor = context.getColor(R.color.colorTextLight);
            colorTextString = String.format("#%06X", (0xFFFFFF & lightTextColor));
        } else {
            int darkTextColor = context.getColor(R.color.colorTextDark);
            colorTextString = String.format("#%06X", (0xFFFFFF & darkTextColor));
        }
        preferences.edit().putString(TEXT_COLOR_PREFERENCE_KEY,colorTextString).apply();
    }

    public static int getHeaderColor(Context context) {

        int primaryColor = context.getColor(R.color.colorPrimary);
        String stringPrimaryColor = String.format("#%06X", (0xFFFFFF & primaryColor));
        SharedPreferences preferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);  // SharedPreferences initialization
        String savedHeaderColor = preferences.getString(HEADER_COLOR_PREFERENCE_KEY, stringPrimaryColor); // Default to white if no color is saved
        int color = Color.parseColor(savedHeaderColor);  // Convert stored string to color
        return color;
    }

    public static int getMainColor(Context context) {

        int secondaryColor = context.getColor(R.color.colorPrimary);
        String stringSecondaryColor = String.format("#%06X", (0xFFFFFF & secondaryColor));
        SharedPreferences preferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);  // SharedPreferences initialization
        String savedMainColor = preferences.getString(MAIN_SCREEN_COLOR_PREFERENCE_KEY, stringSecondaryColor); // Default to white if no color is saved
        int color = Color.parseColor(savedMainColor);  // Convert stored string to color
        return color;
    }

    public static int getTextColor(Context context) {

        int textColor = context.getColor(R.color.colorText);
        String stringTextColor = String.format("#%06X", (0xFFFFFF & textColor));
        SharedPreferences preferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);  // SharedPreferences initialization
        String color = preferences.getString(TEXT_COLOR_PREFERENCE_KEY,stringTextColor);
        return Color.parseColor(color);
    }

    public static boolean isUsingLightColor(Context context) {

        int colorTextLight = context.getColor(R.color.colorTextLight);
        int color = getTextColor(context);
        return  colorTextLight == color;
    }
}
