package com.gsnotes.services.impl;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.gsnotes.bo.Ligne;
import com.gsnotes.services.IFileUploaderService;

@Service
public class FileUploaderServiceImpl implements IFileUploaderService {
	
	@Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
	
	Workbook workbook ;

    public void uploadFile(MultipartFile file) {
    	Path copyLocation;
        try {
             copyLocation = Paths
                .get(uploadDir + File.separator + StringUtils.cleanPath("Sheet.xlsx"));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file " + file.getOriginalFilename()
                + ". Please try again!");
        }
    }
	
}