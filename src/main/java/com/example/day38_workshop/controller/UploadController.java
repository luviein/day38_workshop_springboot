package com.example.day38_workshop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.day38_workshop.repository.UploadRepository;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping
public class UploadController {
    
    @Autowired
    private UploadRepository repo;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> postUploadReturnJson (@RequestPart MultipartFile myFile) {
    
        try{
            String id = repo.saveImage(myFile);
            System.out.printf("Saving to S3: %s\n", id);
            JsonObject resp = Json.createObjectBuilder()
                .add("id", id)
                .build();
            return ResponseEntity.ok(resp.toString());
        } catch (IOException ex) {
            JsonObject resp = Json.createObjectBuilder()
                .add("error", ex.getMessage())
                .build();
            return ResponseEntity.status(500)
                .body(resp.toString());
        }
    }


}