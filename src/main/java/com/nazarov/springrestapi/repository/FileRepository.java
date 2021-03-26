package com.nazarov.springrestapi.repository;

import com.nazarov.springrestapi.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    File getFileByName(String name);
    List<File> getFilesByType(String type);
}
