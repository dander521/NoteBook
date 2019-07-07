package com.roger.notebook.Service.impl;

import com.roger.notebook.Service.BookService;
import com.roger.notebook.Service.model.BookModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    @Override
    public List<BookModel> getBookList(String keywords) {
        return null;
    }

    @Override
    public boolean createBookCategory(BookModel bookModel) {
        return false;
    }
}
