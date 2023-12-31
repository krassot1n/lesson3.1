package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;


import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${students.avatar.dir.path}")
    private String avatarDir;
    private final AvatarRepository repository;
    private final StudentService service;

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(AvatarRepository repository, StudentService service) {
        this.repository = repository;
        this.service = service;
    }
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException{
        logger.debug("Вызван метод загрузки аватара для студента");
        Student student = service.findStudent(studentId);

        Path filePath = Path.of(avatarDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ){
            bis.transferTo(bos);
        }

        Avatar avatar = findStudentAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateAvatarData(filePath));

        repository.save(avatar);
    }
    public byte[] generateAvatarData(Path filePath) throws IOException{
        logger.debug("Вызван метод представления аватара в бинарном виде");
        try (InputStream is = Files.newInputStream(filePath);
        BufferedInputStream bis = new BufferedInputStream(is,1024);
        ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight()/(image.getWidth()/100);
            BufferedImage data = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = data.createGraphics();
            graphics.drawImage(image,0,0,100,height,null);
            graphics.dispose();

            ImageIO.write(data,getExtension(filePath.toString()),baos);
            return baos.toByteArray();

        }


    }
    public Avatar findStudentAvatar(Long studentId){
        logger.debug("Вызван метод поиска аватара студента");
        return repository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtension(String fileName) {
        logger.debug("Вызван метод получения расширения файла");

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String saveOnDisk(MultipartFile file, Student student) throws IOException {
        logger.debug("Вызван метод сохранения аватара в память");
        var path = Path.of(avatarDir);
        if (!path.toFile().exists()) {
            Files.createDirectories(path);
        }

        var dotIndex = file.getOriginalFilename().lastIndexOf('.');
        var ext = file.getOriginalFilename().substring(dotIndex + 1);
        var filePath = avatarDir + "/" + student.getId() + "_" + student.getName() + "." + ext;

        try (var in = file.getInputStream();
             var out = new FileOutputStream(filePath)) {
            in.transferTo(out);
        }
        return filePath;
    }

    public List<Avatar> getAvatarPage(int pageNumber, int pageSize){
        logger.debug("Вызван метод загрузки страницы: '{}",pageNumber);
        Pageable request = PageRequest.of(pageNumber, pageSize);
        return repository.findAll(request).getContent();
    }
}
