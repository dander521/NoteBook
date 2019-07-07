package com.roger.notebook.Service.impl;

import com.roger.notebook.Service.BookService;
import com.roger.notebook.Service.model.BookModel;
import com.roger.notebook.dao.BookDOMapper;
import com.roger.notebook.dataObject.BookDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDOMapper bookDOMapper;

    @Override
    public List<BookModel> getBookList(String title) {
        List<BookDO> bookDOS = bookDOMapper.selectBookList(title);

        List<BookModel> bookModels = new ArrayList<>();
        for (BookDO bookDo: bookDOS) {
            BookModel bookModel = new BookModel();
            BeanUtils.copyProperties(bookDo, bookModel);
            bookModels.add(bookModel);
        }

        return bookModels;
    }

    @Override
    public BookModel createBookCategory(BookModel bookModel) {

        BookDO bookDO = new BookDO();

        BeanUtils.copyProperties(bookModel, bookDO);

        int insert = bookDOMapper.insert(bookDO);

        if (insert>0) {
            BookDO dbDo = bookDOMapper.selectByPrimaryKey(insert);
            BookModel result = new BookModel();
            BeanUtils.copyProperties(dbDo, result);
            return result;
        }
        return null;
    }
}
