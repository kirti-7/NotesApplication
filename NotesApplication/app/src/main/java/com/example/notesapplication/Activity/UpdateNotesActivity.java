package com.example.notesapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapplication.Model.Notes;
import com.example.notesapplication.R;
import com.example.notesapplication.ViewModel.NotesViewModel;
import com.example.notesapplication.databinding.ActivityUpdateNotesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateNotesActivity extends AppCompatActivity {

    ActivityUpdateNotesBinding binding;
    String priority = "1";
    String stitle, ssubtitle, spriority, snotes;
    NotesViewModel notesViewModel;
    String title, subtitle, notes;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);


        id = getIntent().getIntExtra("id", 0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        spriority = getIntent().getStringExtra("priority");
        snotes = getIntent().getStringExtra("notes");

        switch (spriority) {
            case "1":
                binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
                break;
            case "2":
                binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
                break;
            case "3":
                binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);
                break;
        }


        binding.upTitle.setText(stitle);
        binding.upSubtitle.setText(ssubtitle);
        binding.upNotes.setText(snotes);

        binding.greenPriority.setOnClickListener(v -> {
            binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);

            priority = "1";
        });
        binding.yellowPriority.setOnClickListener(v -> {
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.redPriority.setImageResource(0);

            priority = "2";
        });
        binding.redPriority.setOnClickListener(v -> {
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);

            priority = "3";
        });

        binding.upButton.setOnClickListener(v -> {
            title = binding.upTitle.getText().toString();
            subtitle = binding.upSubtitle.getText().toString();
            notes = binding.upNotes.getText().toString();

            UpdateNotes(title, subtitle, notes);
        });

    }

    private void UpdateNotes(String title, String subtitle, String notes) {
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        Notes updateNote = new Notes();
        updateNote.id = id;
        updateNote.notesTitle = title;
        updateNote.notesSubtitle = subtitle;
        updateNote.notes = notes;
        updateNote.notesPriority = priority;
        updateNote.notesDate = date;

        notesViewModel.updateNotes(updateNote);

        Toast.makeText(this, "Notes updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.delete){
            BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(UpdateNotesActivity.this, R.style.BottomSheetDialog);
            View view= LayoutInflater.from(UpdateNotesActivity.this).inflate(R.layout.delete_popup,(LinearLayout) findViewById(R.id.bottomsheet));

            bottomSheetDialog.setContentView(view);

            TextView yes,no;
            yes = view.findViewById(R.id.yes);
            no = view.findViewById(R.id.no);
            
            
            yes.setOnClickListener(v->{
                notesViewModel.deleteNotes(id);
                finish();
            });
            no.setOnClickListener(v->{
                bottomSheetDialog.dismiss();
            });

            bottomSheetDialog.show();



        }

        return true;
    }
}