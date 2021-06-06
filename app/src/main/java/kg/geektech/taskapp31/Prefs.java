package kg.geektech.taskapp31;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveBoardState() {
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isShown() {
        return preferences.getBoolean("isShown", false);
    }
    public void putString(String Save,String s){
        preferences.edit().putString("save",s).apply();
    }
    public String getString(String Save){
        return preferences.getString("save","");
    }
    public void clearPreferences(){
        preferences.edit().clear().commit();
    }
    public void clearEditText(){
        preferences.edit().remove(getString("save"));
    }
}

