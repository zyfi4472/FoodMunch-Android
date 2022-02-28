package com.naqvi.foodmunch.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.naqvi.foodmunch.Common.UserModel;

public class SharedReference {

    SharedPreferences ShredRef;

    public SharedReference(Context context) {
        ShredRef = context.getSharedPreferences("myRef", Context.MODE_PRIVATE);
    }

    //Login
    public void saveUser(UserModel userModel) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("userId", userModel.userId);
        editor.putString("userType", userModel.userType);
        editor.commit();
    }

    public UserModel getUser() {
        UserModel userModel = new UserModel();

        userModel.userId = ShredRef.getString("userId", "No Value");
        userModel.userType = ShredRef.getString("userType", "No Value");
        return userModel;
    }

    public void removeUser() {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("userId", "No Value");
        editor.putString("userType", "No Value");
        editor.commit();
    }

}

