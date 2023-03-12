package com.example.notesapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapplication.Model.Notes;
import com.example.notesapplication.Repositoy.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> lowToHigh;
    public LiveData<List<Notes>> highToLow;

    public NotesViewModel( Application application) {
        super(application);

        repository=new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
        lowToHigh = repository.lowToHigh;
        highToLow = repository.highToLow;

    }

    public void insertNotes(Notes notes){
        repository.insertNotes(notes);
    }
    public void deleteNotes(int id){
        repository.deleteNotes(id);
    }
    public void updateNotes(Notes notes){
        repository.updateNotes(notes);
    }

}
