package com.BU.FrameworkProject.service;

import com.BU.FrameworkProject.vo.NotesVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface INotesService {
    NotesVo saveNotes(NotesVo notesVo, List<MultipartFile> multipartFile) throws Exception;
    NotesVo findNote(NotesVo notesVo) throws Exception;
}
