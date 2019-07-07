package com.roger.notebook.Service;

import com.roger.notebook.Service.model.BookModel;

import java.util.List;

public interface BookService {

    public List<BookModel> getBookList(String title);

    public BookModel createBookCategory(BookModel bookModel);
}
