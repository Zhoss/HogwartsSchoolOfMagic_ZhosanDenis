package ru.hogwarts.school.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import java.util.Arrays;
import java.util.Objects;


@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;
    @OneToOne
    private Student student;

    public Avatar() {
    }

    public Avatar(Long id, String filePath, long fileSize, String mediaType, Student student) {
        setId(id);
        setFilePath(filePath);
        setFileSize(fileSize);
        setMediaType(mediaType);
        setStudent(student);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return fileSize == avatar.fileSize && Objects.equals(id, avatar.id) && Objects.equals(filePath, avatar.filePath) && Objects.equals(mediaType, avatar.mediaType) && Arrays.equals(data, avatar.data) && Objects.equals(student, avatar.student);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, student);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", student=" + student +
                '}';
    }
}
