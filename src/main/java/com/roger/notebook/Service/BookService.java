package com.roger.notebook.Service;

import com.roger.notebook.Service.model.BookModel;

import java.util.List;
import java.util.Map;

public interface BookService {

    public List<BookModel> getBookList(int uuid, String keyword, int page, int pageSize);

    public boolean createBookCategory(BookModel bookModel);

    public BookModel getBookModel(int bookid);
}
