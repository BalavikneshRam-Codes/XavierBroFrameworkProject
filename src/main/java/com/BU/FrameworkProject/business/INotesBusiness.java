package com.BU.FrameworkProject.business;

import com.BU.FrameworkProject.Entity.Notes;
import com.BU.FrameworkProject.vo.NotesVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface INotesBusiness {
    NotesVo saveNotes(NotesVo notesVo, List<MultipartFile> multipartFile) throws Exception;

    NotesVo findNote(NotesVo notes) throws Exception;
}
