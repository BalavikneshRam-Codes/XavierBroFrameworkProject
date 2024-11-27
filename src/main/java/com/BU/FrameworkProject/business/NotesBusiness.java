package com.BU.FrameworkProject.business;

import com.BU.FrameworkProject.Entity.*;
import com.BU.FrameworkProject.repository.NotesRepo;
import com.BU.FrameworkProject.repository.UserRepo;
import com.BU.FrameworkProject.vo.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class NotesBusiness implements INotesBusiness{
    @Autowired
    private NotesRepo notesRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${application.bucket.name}")
    private String bucketName;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public NotesVo saveNotes(NotesVo notesVo, List<MultipartFile> multipartFile) throws Exception{
        try {
            validate(notesVo, multipartFile);
            Notes notesV0 = convertVoToEntity(notesVo, multipartFile);
            notesRepo.save(notesV0);
        }catch (Exception e){
            multipartFile.clear();
            throw new Exception(e.getMessage());
        }
        return notesVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NotesVo findNote(NotesVo notesVo) throws Exception {
        Notes notes = findByNotesID(notesVo.getNotes_id());
        return convertEntityToVO(notes);
    }

    private Notes findByNotesID(Long id) throws Exception {
        return notesRepo.findById(id).orElseThrow(()-> new Exception("Notes id not Available is DB"));
    }
    private NotesVo convertEntityToVO(Notes notes){
        NotesVo notesVo = new NotesVo();
        notesVo.setNotes_id(notes.getNotes_id());
        notesVo.setStatus(notes.getNoteStatus());
        notesVo.setDescription(notes.getNoteDesc());
        notesVo.setTitle(notes.getNoteTitle());
        notesVo.setNotesUserVOS(EntityToVoNotesUser(notes));
        notesVo.setActionItemVOS(EntityToVoActionItem(notes));
        notesVo.setAttachmentVos(EntityToVoAttachment(notes));
        return notesVo;
    }

    private List<AttachmentVo> EntityToVoAttachment(Notes notes){
        List<AttachmentVo> attachmentVos = new ArrayList<>();
        notes.getAttachments()
                .stream()
                .sorted(Comparator.comparingLong(Attachment::getAttachmentOrder))
                .forEach(attachment -> {
            AttachmentVo attachmentVo = new AttachmentVo();
            attachmentVo.setAttachment_id(attachment.getAttachmentId());
            attachmentVo.setOrder(attachment.getAttachmentOrder());
            attachmentVo.setFileName(attachment.getFileName());
            attachmentVo.setFileUrl(attachment.getFileUrl());
            attachmentVos.add(attachmentVo);
        });
        return attachmentVos;
    }

//    private URL getURl(String fileName){
//       return amazonS3.getUrl(bucketName,fileName);
//    }

    private List<NotesUserVO> EntityToVoNotesUser(Notes notes){
        List<NotesUserVO> notesUserVOS = new ArrayList<>();
        notes.getNotesUsers().forEach(notesUser -> {
            NotesUserVO notesUserVO = new NotesUserVO();
            notesUserVO.setNotes_id(notesUser.getNoteUserId());
            notesUserVO.setStatus(notesUser.getNoteStatus());
            UserVo userVo = new UserVo();
            userVo.setUser_email(notesUser.getNoteUser().getUseEmail());
            userVo.setUser_id(notesUser.getNoteUser().getUserId());
            userVo.setUser_name(notesUser.getNoteUser().getUserName());
            notesUserVO.setUserVo(userVo);
            notesUserVOS.add(notesUserVO);
        });
        return notesUserVOS;
    }

    private List<ActionItemVO> EntityToVoActionItem(Notes notes){
        List<ActionItemVO> actionItemVOS = new ArrayList<>();
        notes.getActionItems()
                .stream()
                .sorted(Comparator.comparingLong(value -> value.getActionOrder()))
                .forEach(actionItem -> {
                ActionItemVO actionItemVO = new ActionItemVO();
                actionItemVO.setAction_id(actionItem.getActionItemId());
                actionItemVO.setFree_Text(actionItem.getFreeText());
                actionItemVO.setAction_order(actionItem.getActionOrder());
                actionItemVOS.add(actionItemVO);
        });

        return actionItemVOS;
    }

    private void uploadS3(MultipartFile multipartFile){
            File file;
            try {
                file = convertMultiPartFileToFile(multipartFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

//            String changeFile = System.currentTimeMillis()+" - "+multipartFile.getOriginalFilename();

            PutObjectResult putObjectResult = amazonS3.putObject(bucketName,multipartFile.getOriginalFilename(),file);
            if(putObjectResult != null)
                file.delete();
            System.out.println("File uploading successfully :"+multipartFile.getOriginalFilename());
    }
    private File convertMultiPartFileToFile(MultipartFile multipartFile) throws Exception {
        File file = new File(multipartFile.getOriginalFilename());
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        }catch (Exception e){
            throw new Exception(e);
        }
        return file;
    }

    private Notes convertVoToEntity(NotesVo notesVo,List<MultipartFile> file){
        Notes notes = new Notes();
        notes.setNoteStatus("Open");
        notes.setNoteTitle(notesVo.getTitle());
        notes.setNoteDesc(notesVo.getDescription());
        if(!CollectionUtils.isEmpty(notesUserList(notes,notesVo)))
            notes.setNotesUsers(notesUserList(notes,notesVo));
        if(!CollectionUtils.isEmpty(actionItemList(notes,notesVo)))
            notes.setActionItems(actionItemList(notes,notesVo));
        notes.setAttachments(attachmentSet(notes,file));
        return notes;
    }

    private Set<Attachment> attachmentSet(Notes notes,List<MultipartFile> multipartFile){
        Set<Attachment> attachmentSet_ = new HashSet<>();
        AtomicLong atomicLong = new AtomicLong(1);
        multipartFile.forEach(multipartFile_ -> {
                Attachment attachment = new Attachment();
                attachment.setAttachmentOrder(atomicLong.getAndIncrement());
                attachment.setFileName(multipartFile_.getOriginalFilename());
                attachment.setNotesId(notes);
                attachment.setFileUrl(amazonS3.getUrl(bucketName,multipartFile_.getOriginalFilename())+"");
                attachmentSet_.add(attachment);
            try {
                uploadS3(multipartFile_);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return attachmentSet_;
    }
    private Set<ActionItem> actionItemList(Notes notes, NotesVo notesVo){
        Set<ActionItem> result = new HashSet<>();
        AtomicLong atomicLong = new AtomicLong(1);
        notesVo.getActionItemVOS().forEach(actionItemVO -> {
            ActionItem actionItem = new ActionItem();
            actionItem.setActionOrder(atomicLong.getAndIncrement());
            actionItem.setFreeText(convertStringToHTML(actionItemVO.getFree_Text()));
            actionItem.setNotesId(notes);
            result.add(actionItem);
        });
        return result;
    }
    private String convertStringToHTML(String freeText){
        return "<p>" + (freeText.replace("\n", "</br>")) + "</p>";
    }

    private Set<NotesUser> notesUserList(Notes notesE,NotesVo notes){
        Set<NotesUser> result = new HashSet<>();
        notes.getNotesUserVOS().forEach(notesUser -> {
            NotesUser notesUser_ = new NotesUser();
            notesUser_.setNoteStatus("Open");
            notesUser_.setNoteUser(userRepo.findById(notesUser.getUserVo().getUser_id()).orElseThrow());
            notesUser_.setNotesId(notesE);
            result.add(notesUser_);
        });
        return result;
    }

    private void validate(NotesVo notesVo,List<MultipartFile> multipartFiles) throws Exception {
        if(StringUtils.isBlank(notesVo.getTitle())){
            throw new Exception();
        }
        if (CollectionUtils.isEmpty(notesVo.getNotesUserVOS())){
            throw new Exception();
        }
        if(multipartFiles.isEmpty()){
            throw new Exception();
        }
    }
}
