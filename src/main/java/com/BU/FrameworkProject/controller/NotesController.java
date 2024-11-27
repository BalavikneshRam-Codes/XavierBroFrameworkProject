package com.BU.FrameworkProject.controller;

import com.BU.FrameworkProject.Entity.Notes;
import com.BU.FrameworkProject.service.INotesService;
import com.BU.FrameworkProject.service.NotesService;
import com.BU.FrameworkProject.vo.NotesUserVO;
import com.BU.FrameworkProject.vo.NotesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NotesController {

    @Autowired
    private INotesService notesService;

    @PostMapping("/addNotes")
    public NotesVo saveNotes(@RequestPart NotesVo notesVo, @RequestPart List<MultipartFile> multipartFile){
        try{
            notesService.saveNotes(notesVo,multipartFile);
            notesVo.setStatusCode(HttpStatus.ACCEPTED.value());
            notesVo.setMessage("Successfully Storage in DB and File uploaded in AWS S3");
        }catch (Exception e){
            notesVo.setStatusCode(HttpStatus.NO_CONTENT.value());
            notesVo.setMessage(HttpStatus.NO_CONTENT.name());
            e.printStackTrace();
        }
        return notesVo;
    }

    @GetMapping("/getNote")
    public NotesVo findNote(@RequestBody NotesVo notes){
        try{
            if(notes.getNotes_id() == null){
                notes.setStatusCode(HttpStatus.NO_CONTENT.value());
                notes.setMessage(HttpStatus.NO_CONTENT.name());
                return notes;
            }
            notes = notesService.findNote(notes);
            notes.setStatusCode(HttpStatus.FOUND.value());
            notes.setMessage(HttpStatus.FOUND.name());
        }catch (Exception e){
            notes.setStatusCode(HttpStatus.NO_CONTENT.value());
            notes.setMessage(HttpStatus.NO_CONTENT.name());
        }
        return notes;
    }
}
