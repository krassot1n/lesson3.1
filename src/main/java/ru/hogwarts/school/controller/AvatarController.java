package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;


import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

@RequestMapping("/avatar")
@RestController
public class AvatarController {
    private final AvatarService service;

    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        service.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<byte[]> find(@PathVariable long studentId) {
        var avatar = service.findStudentAvatar(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("/file/{studentId}")
    public void findFile(@PathVariable long studentId, HttpServletResponse response) throws IOException {
        Avatar avatar = service.findStudentAvatar(studentId);
        if (avatar == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        try (var out = response.getOutputStream();
             var in = new FileInputStream(avatar.getFilePath())) {
            in.transferTo(out);
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
        }
    }

    @GetMapping("/avatar_page")
    public Collection<Avatar> getPage(@RequestParam int numberPage,@RequestParam int pageSize){
        return service.getAvatarPage(numberPage, pageSize);
    }



}
