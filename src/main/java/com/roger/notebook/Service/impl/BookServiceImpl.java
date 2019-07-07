package com.roger.notebook.Service.impl;

import com.github.pagehelper.PageHelper;
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
    public List<BookModel> getBookList(String keyword, int page, int pageSize) {
        // 开始分页
        PageHelper.startPage(page, pageSize);

        Map map = new HashMap();
        map.put("keyword", keyword);
        List<BookDO> bookDOS = bookDOMapper.selectBookList(map);

        List<BookModel> bookModels = new ArrayList<>();
        for (BookDO bookDo: bookDOS) {
            BookModel bookModel = new BookModel();
            BeanUtils.copyProperties(bookDo, bookModel);
            bookModels.add(bookModel);
        }

        return bookModels;
    }

    @Override
    public boolean createBookCategory(BookModel bookModel) {

        BookDO bookDO = new BookDO();

        BeanUtils.copyProperties(bookModel, bookDO);

        int insert = bookDOMapper.insert(bookDO);

        if (insert>0) {
            return true;
        }
        return false;
    }

    @Override
    public BookModel getBookModel(int bookid) {
        BookDO bookDO = bookDOMapper.selectByPrimaryKey(bookid);
        BookModel bookModel = new BookModel();
        BeanUtils.copyProperties(bookDO, bookModel);
        return bookModel;
    }


}
