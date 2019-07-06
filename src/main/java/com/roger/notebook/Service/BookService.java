package com.roger.notebook.Service;

import com.roger.notebook.Service.model.BookModel;

import java.util.List;

public interface BookService {

    public List<BookModel> getBookList(String keywords);

    public boolean createBookCategory(BookModel bookModel);
}
