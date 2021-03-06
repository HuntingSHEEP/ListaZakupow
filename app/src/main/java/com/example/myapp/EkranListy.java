package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapp.logic.AppLogic;
import com.example.myapp.logic.ListaZakupow;

import java.io.File;

public class EkranListy extends AppCompatActivity {
    private Button back,addProduct,addRecipe, removeList,end;
    private RecyclerView recycle;
    private RecyclerView.LayoutManager layoutManager;
    private CustomAdapterListScreen adapter;
    public AppLogic logic;
    private File directory;
    private int shoppingListIndex;
    private TextView tytul;
    private ListaZakupow listaZakupow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ekran_listy);
        end = (Button) findViewById(R.id.koniec);
        back = (Button) findViewById(R.id.backFromList);
        addProduct = (Button) findViewById(R.id.addProductToList);
        addRecipe = (Button) findViewById(R.id.addRecipeToList);
        removeList = (Button) findViewById(R.id.buttonRemoveList);
        tytul = (TextView) findViewById(R.id.textView7);

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("TendOfShopping");
                endOfShopping();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToListOfLists();
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addProductToList();
            }
        });
        addRecipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addRecipeToList();
            }
        });
        removeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeShoppingList(shoppingListIndex);
            }
        });

        Intent intent = getIntent();
        shoppingListIndex = (int) intent.getIntExtra(CustomAdapterShoppingListsScreen.recipeID, 0);

        directory  = getFilesDir();
        logic = new AppLogic(directory);
        listaZakupow = logic.getShoppingListBase().getItem(shoppingListIndex);

        tytul.setText(listaZakupow.getDescription());

        recycle = (RecyclerView) findViewById(R.id.recycleShoppingList);
        recycle.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CustomAdapterListScreen(shoppingListIndex,this);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);
    }

    private void removeShoppingList(int shoppingListIndex) {

        logic.getShoppingListBase().remove(shoppingListIndex);
        logic.save();

        backToListOfLists();
    }

    private void addRecipeToList() {
        Intent intent = new Intent(this, EkranDodawaniaPrzepisu.class);
        intent.putExtra(CustomAdapterShoppingListsScreen.recipeID, shoppingListIndex);
        startActivity(intent);
    }

    private void addProductToList() {
        Intent intent = new Intent(this, EkranDodawaniaProduktu.class);
        intent.putExtra(CustomAdapterShoppingListsScreen.recipeID, shoppingListIndex);
        startActivity(intent);
    }

    private void backToListOfLists() {
        Intent intent = new Intent(this, EkranListZakupow.class);
        startActivity(intent);
    }

    private  void endOfShopping() {
        System.out.println("TET");
        listaZakupow.koniecZakupow();
        logic.save();
        Intent intent = new Intent (this, EkranListy.class);
        intent.putExtra(CustomAdapterShoppingListsScreen.recipeID, shoppingListIndex);
        startActivity(intent);

    }
}