package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${students.avatar.dir.path}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    private final static Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentService.getStudent(studentId);
        logger.debug("Found student " + student.getName() + " with input id = " + studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtensions(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        this.avatarRepository.save(avatar);
        logger.info("Was invoked method for upload avatar of student");
    }

    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for download avatar of student");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> findAllAvatars(Integer pageNumber, Integer pageSize) {
        if (pageNumber < 0 || pageSize < 0) {
            logger.error("Input page number or page size is out of range");
            throw new IllegalArgumentException("Требуется указать корректное номер и размер страницы");
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Avatar> allAvatars = this.avatarRepository.findAll(pageRequest).getContent();
        List<Avatar> allAvatarsWithoutData = new ArrayList<>();
        for (Avatar avatar : allAvatars) {
            Avatar avatarWithoutData = new Avatar(avatar.getId(),
                    avatar.getFilePath(),
                    avatar.getFileSize(),
                    avatar.getMediaType(),
                    avatar.getStudent());
            allAvatarsWithoutData.add(avatarWithoutData);
        }
        logger.info("Was invoked method for getting avatars of all students with avatars");
        return allAvatarsWithoutData;
    }
}
