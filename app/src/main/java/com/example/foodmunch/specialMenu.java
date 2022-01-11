package com.example.foodmunch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class specialMenu extends AppCompatActivity {

    String menulist[] = {"\t\t\tCafe 1 : Burger\n\n" + "RS 200","\t\t\tCafe 2 : Biryani\n\n" + "RS 150","\t\t\tCafe 3 : Fries\n\n" + "RS 180","\t\t\tCafe 4 : Pizza\n\n" + "RS 400"};
    int menuimages [] = {R.drawable.burger, R.drawable.biryani, R.drawable.fries, R.drawable.pizza};
   ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_menu);

        l1 = (ListView) findViewById(R.id.specialmenu);
        CustomBaseAdapter c1 =new CustomBaseAdapter(getApplicationContext(),menulist,menuimages);
        l1.setAdapter(c1);
    }
}