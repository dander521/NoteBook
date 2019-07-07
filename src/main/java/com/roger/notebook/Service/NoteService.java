package com.roger.notebook.Service;

import com.roger.notebook.Service.model.NoteModel;

import java.util.List;

public interface NoteService {

    public List<NoteModel> getNoteList(int bookid, String keyword, int page, int pageSize);

    public boolean createNote(NoteModel noteModel);

    public NoteModel getNoteModel(int noteid);
}
