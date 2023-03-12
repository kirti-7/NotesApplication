package com.example.notesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapplication.Activity.AddNotesActivity;
import com.example.notesapplication.Adapter.NotesAdapter;
import com.example.notesapplication.Model.Notes;
import com.example.notesapplication.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNotes;
    NotesViewModel notesViewModel;
    RecyclerView notesRV;
    NotesAdapter adapter;

    TextView noFilter, lowToHigh, highToLow;
    List<Notes> filterNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesViewModel= ViewModelProviders.of(this).get( NotesViewModel.class);
        notesRV = findViewById(R.id.notesRV);

        noFilter=findViewById(R.id.noFilter);
        lowToHigh=findViewById(R.id.lowToHigh);
        highToLow=findViewById(R.id.highToLow);

        noFilter.setBackgroundResource(R.drawable.selected_filter);

        noFilter.setOnClickListener(v->{
            loadData(0);
            noFilter.setBackgroundResource(R.drawable.selected_filter);
            lowToHigh.setBackgroundResource(R.drawable.unselected_filter);
            highToLow.setBackgroundResource(R.drawable.unselected_filter);

        });
        lowToHigh.setOnClickListener(v->{
            loadData(1);
            noFilter.setBackgroundResource(R.drawable.unselected_filter);
            lowToHigh.setBackgroundResource(R.drawable.selected_filter);
            highToLow.setBackgroundResource(R.drawable.unselected_filter);
        });
        highToLow.setOnClickListener(v->{
            loadData(2);
            noFilter.setBackgroundResource(R.drawable.unselected_filter);
            lowToHigh.setBackgroundResource(R.drawable.unselected_filter);
            highToLow.setBackgroundResource(R.drawable.selected_filter);
        });






        addNotes=findViewById(R.id.addNotes);
        addNotes.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, AddNotesActivity.class));
        });

        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filterNameList=notes;
            }
        });

    }

    private void loadData(int i) {
        switch(i){
            case 0:
                notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
                    @Override
                    public void onChanged(List<Notes> notes) {
                        setAdapter(notes);
                        filterNameList=notes;
                    }
                });
                break;
            case 1:
                notesViewModel.lowToHigh.observe(this, new Observer<List<Notes>>() {
                    @Override
                    public void onChanged(List<Notes> notes) {
                        setAdapter(notes);
                        filterNameList=notes;
                    }
                });
                break;
            case 2:
                notesViewModel.highToLow.observe(this, new Observer<List<Notes>>() {
                    @Override
                    public void onChanged(List<Notes> notes) {
                        setAdapter(notes);
                        filterNameList=notes;
                    }
                });
                break;
        }
    }

    public void setAdapter(List<Notes> notes){
        notesRV.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this,notes);
        notesRV.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem menuItem=menu.findItem(R.id.app_bar_search);
        SearchView searchView=(SearchView)menuItem.getActionView();

        searchView.setQueryHint("Search Notes here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String newText) {
        ArrayList<Notes> FilterNames= new ArrayList<>();
        for(Notes notes:this.filterNameList){
            if(notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText)){
                FilterNames.add(notes);
            };
        }
        this.adapter.searchNotes(FilterNames);
    }
}