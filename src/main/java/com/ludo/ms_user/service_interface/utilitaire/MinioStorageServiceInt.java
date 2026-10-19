package com.ludo.ms_user.service_interface.utilitaire;

import java.io.InputStream;

import java.util.UUID;

public interface MinioStorageServiceInt {
	String getPresignedUrl(String objectKey);
	
	String uploadFile(InputStream inputStream, long size, String contentType, String folder, UUID userId);
	String uploadFile(InputStream inputStream, long size, String contentType, String folder, UUID userId, String additional);
	
	InputStream downloadFile(String objectName);
	
	void deleteFile(String objectName);
}
