package ru.hogwarts.school.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;


@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;
    @OneToOne
    private Student student;

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public Student getStudent() {
        return student;
    }

    public void setId(Long id) {
        if (id > 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный id");
        }
    }

    public void setFilePath(String filePath) {
        if (filePath != null && !filePath.isEmpty() && !filePath.isBlank()) {
            this.filePath = filePath;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный путь");
        }
    }

    public void setFileSize(long fileSize) {
        if (fileSize > 0) {
            this.fileSize = fileSize;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный размер");
        }
    }

    public void setMediaType(String mediaType) {
        if (mediaType != null && !mediaType.isEmpty() && !mediaType.isBlank()) {
            this.mediaType = mediaType;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный тип медиа");
        }
    }

    public void setData(byte[] data) {
        if (data != null) {
            this.data = data;
        }
    }

    public void setStudent(Student student) {
        if (student != null) {
            this.student = student;
        }
    }
}
