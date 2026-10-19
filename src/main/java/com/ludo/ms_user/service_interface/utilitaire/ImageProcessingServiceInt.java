package com.ludo.ms_user.service_interface.utilitaire;

import org.springframework.web.multipart.MultipartFile;

import com.ludo.ms_user.service_implementation.utilitaire.ImageProcessingServiceImp.ProcessedImage;

public interface ImageProcessingServiceInt {
	ProcessedImage resizeIfNeeded(MultipartFile file);
}
