package com.gsnotes.services;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gsnotes.bo.Ligne;

public interface IFileUploaderService {

	public void uploadFile(MultipartFile file);
}

