package com.example.notesapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.example.notesapplication.Model.Notes;
import com.example.notesapplication.R;
import com.example.notesapplication.ViewModel.NotesViewModel;
import com.example.notesapplication.databinding.ActivityAddNotesBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNotesActivity extends AppCompatActivity {

    ActivityAddNotesBinding binding;
    String title, subtitle, notes;
    NotesViewModel notesViewModel;
    String priority="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
        notesViewModel= ViewModelProviders.of(this).get( NotesViewModel.class);

        binding.greenPriority.setOnClickListener(v->{
            binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);

            priority="1";
        });
        binding.yellowPriority.setOnClickListener(v->{
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.redPriority.setImageResource(0);

            priority="2";
        });
        binding.redPriority.setOnClickListener(v->{
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);

            priority="3";
        });



        binding.doneFAB.setOnClickListener(v->{
            title=binding.notesTitle.getText().toString();
            subtitle=binding.notesSubtitle.getText().toString();
            notes=binding.notes.getText().toString();

            CreateNotes(title, subtitle, notes);
        });

    }

    private void CreateNotes(String title, String subtitle, String notes) {

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        Notes notes1=new Notes();

        notes1.notesTitle=title;
        notes1.notesSubtitle=subtitle;
        notes1.notes=notes;
        notes1.notesPriority=priority;
        notes1.notesDate=date;

        notesViewModel.insertNotes(notes1);

        Toast.makeText(this, "Notes added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}