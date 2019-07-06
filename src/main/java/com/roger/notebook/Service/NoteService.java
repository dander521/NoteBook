package com.roger.notebook.Service;

import com.roger.notebook.Service.model.NoteModel;

import java.util.List;

public interface NoteService {

    public List<NoteModel> getNoteList(String bookId, String keywords);

    public boolean createNote(NoteModel noteModel);
}
