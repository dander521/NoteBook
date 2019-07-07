package com.roger.notebook.Service;

import com.roger.notebook.Service.model.BookModel;

import java.util.List;
import java.util.Map;

public interface BookService {

    public List<BookModel> getBookList(String title);

    public boolean createBookCategory(BookModel bookModel);
}
