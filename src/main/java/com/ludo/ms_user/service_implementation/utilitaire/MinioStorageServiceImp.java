package com.ludo.ms_user.service_implementation.utilitaire;

import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;

import java.io.InputStream;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.config.MinioProperties;

import com.ludo.ms_user.exception.global.FileStorageException;

import com.ludo.ms_user.service_interface.utilitaire.MinioStorageServiceInt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MinioStorageServiceImp implements MinioStorageServiceInt {

	private final MinioClient minioInternalClient;
	private final MinioClient minioExternalClient;
	private final MinioProperties minioProperties;
	
	@Override
	public String getPresignedUrl(String objectKey) {
		try {
			return minioExternalClient.getPresignedObjectUrl(
				GetPresignedObjectUrlArgs.builder()
					.method(Method.GET)
					.bucket(minioProperties.getBucket())
					.object(objectKey)
					.expiry(60*60*12)
					.build()
			);
			
		} catch (Exception e) {
	        throw new FileStorageException("Could not generate file URL : " + e.getMessage());
	    }
	}

	@Override
	public String uploadFile(InputStream inputStream, long size, String contentType, String folder, UUID userId) {
		return uploadFile(inputStream, size, contentType, folder, userId, null);
	}
	
	@Override
	public String uploadFile(InputStream inputStream, long size, String contentType, String folder, UUID userId, String additional) {
		try {
			String objectName = "";
			
			if (additional != null && additional.trim().length() > 0) {
				objectName = folder + "/" + userId + additional + ".jpg";				
			} else {				
				objectName = folder + "/" + userId + ".jpg";
			}
			
			minioInternalClient.putObject(
				PutObjectArgs.builder()
					.bucket(minioProperties.getBucket())
					.object(objectName)
					.stream(inputStream, size, -1)
					.contentType(contentType)
					.build()
			);
			
			return objectName;
		} catch (Exception e) {
			throw new FileStorageException("Could not upload file to storage : " + e.getMessage());
		}
	}
	
	@Override
	public InputStream downloadFile(String objectName) {
        try {
            return minioInternalClient.getObject(
                GetObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(objectName)
                    .build()
            );

        } catch (Exception e) {
            throw new FileStorageException("Could not download file from storage : " + e.getMessage());
        }
    }
	
	@Override
	public void deleteFile(String objectName) {
        try {
        	minioInternalClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(objectName)
                    .build()
            );

        } catch (Exception e) {
            throw new FileStorageException("Could not delete file from storage : " + e.getMessage());
        }
    }
	
}
