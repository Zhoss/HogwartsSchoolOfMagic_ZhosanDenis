package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatarFile) throws IOException {
        if (avatarFile.getSize() > 300 * 1024) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        this.avatarService.uploadAvatar(studentId, avatarFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{studentId}/avavtar-from-bd")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long studentId) {
        Avatar avatar = this.avatarService.findAvatar(studentId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        httpHeaders.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(avatar.getData());
    }

    @GetMapping(value = "/{studentId}/avavtar-from-file")
    public void downloadAvatar(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        Avatar avatar = this.avatarService.findAvatar(studentId);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "students")
    public ResponseEntity<List<Avatar>> getAllAvatars(@RequestParam(name = "page") Integer pageNumber,
                                                      @RequestParam(name = "size") Integer pageSize) throws IOException {
        List<Avatar> allAvatars = this.avatarService.findAllAvatars(pageNumber, pageSize);
        if (allAvatars.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allAvatars);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
