package com.roger.notebook.Service.impl;

import com.roger.notebook.Service.NoteService;
import com.roger.notebook.Service.model.NoteModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class NoteServiceImpl implements NoteService {

    @Override
    public List<NoteModel> getNoteList(String bookId, String keywords) {
        return null;
    }

    @Override
    public boolean createNote(NoteModel noteModel) {
        return false;
    }
}
