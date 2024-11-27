package com.BU.FrameworkProject.service;

import com.BU.FrameworkProject.Entity.Notes;
import com.BU.FrameworkProject.business.INotesBusiness;
import com.BU.FrameworkProject.vo.NotesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class NotesService implements INotesService{
    @Autowired
    private INotesBusiness notesBusiness;
    @Override
    public NotesVo saveNotes(NotesVo notesVo, List<MultipartFile> multipartFile) throws Exception {
        return notesBusiness.saveNotes(notesVo,multipartFile);
    }

    @Override
    public NotesVo findNote(NotesVo notes) throws Exception {
        return notesBusiness.findNote(notes);
    }
}
