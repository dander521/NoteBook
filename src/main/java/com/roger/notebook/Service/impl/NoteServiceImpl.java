package com.roger.notebook.Service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.roger.notebook.Service.NoteService;
import com.roger.notebook.Service.model.BookModel;
import com.roger.notebook.Service.model.NoteModel;
import com.roger.notebook.dao.NoteDOMapper;
import com.roger.notebook.dataObject.BookDO;
import com.roger.notebook.dataObject.NoteDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteDOMapper noteDOMapper;

    @Override
    public List<NoteModel> getNoteList(int bookId, String keyword, int page, int pageSize) {

        // 开始分页
        PageHelper.startPage(page, pageSize);

        Map map = new HashMap();
        map.put("keyword", keyword);
        List<NoteDO> noteDOS = null;
        if (!StringUtils.isEmpty(keyword)) {
            noteDOS = noteDOMapper.selectNoteListByKeyword(map);
        } else if (bookId > 0) {
            noteDOS = noteDOMapper.selectNoteListByBookId(bookId);
        } else {
            noteDOS = noteDOMapper.selectAll();
        }

        List<NoteModel> noteModels = new ArrayList<>();
        for (NoteDO noteDO: noteDOS) {
            NoteModel noteModel = new NoteModel();
            BeanUtils.copyProperties(noteDO, noteModel);
            noteModels.add(noteModel);
        }

        return noteModels;
    }

    @Override
    public boolean createNote(NoteModel noteModel) {

        NoteDO noteDO = new NoteDO();

        BeanUtils.copyProperties(noteModel, noteDO);

        int insert = noteDOMapper.insert(noteDO);

        if (insert>0) {
            return true;
        }
        return false;
    }

    @Override
    public NoteModel getNoteModel(int noteid) {

        NoteDO noteDO = noteDOMapper.selectByPrimaryKey(noteid);
        NoteModel noteModel = new NoteModel();
        BeanUtils.copyProperties(noteDO, noteModel);

        return noteModel;
    }
}
