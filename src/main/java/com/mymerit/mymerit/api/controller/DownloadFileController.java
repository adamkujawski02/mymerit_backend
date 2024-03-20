package com.mymerit.mymerit.api.controller;

import com.mymerit.mymerit.domain.entity.DownloadFile;
import com.mymerit.mymerit.domain.service.DownloadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mymerit.mymerit.api.payload.response.ApiResponse;

import java.io.IOException;

@RestController 
@RequestMapping("/auth")
public class DownloadFileController {

    @Autowired
    private DownloadFileService fileService;

    @PostMapping("/file/upload")
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file, @RequestParam("filename") String filename ) throws IOException {
        return new ResponseEntity<>(fileService.addFile(file, filename), HttpStatus.OK);
    }

    @GetMapping("/file/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        DownloadFile loadFile = fileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }

    @DeleteMapping("/file/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws IOException {
        fileService.DeleteFile(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}