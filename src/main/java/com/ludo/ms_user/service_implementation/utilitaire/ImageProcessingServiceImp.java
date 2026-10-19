package com.ludo.ms_user.service_implementation.utilitaire;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.ludo.ms_user.exception.global.FileStorageException;

import com.ludo.ms_user.service_interface.utilitaire.ImageProcessingServiceInt;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageProcessingServiceImp implements ImageProcessingServiceInt {
	
	private static final int MAX_WIDTH = 512;
	private static final int MAX_HEIGHT = 512;
	
	@Override
	public ProcessedImage resizeIfNeeded(MultipartFile file) {
		try {
			BufferedImage originalImage = ImageIO.read(file.getInputStream());
			
			if (originalImage == null) {
				throw new FileStorageException("The uploaded file is not a valid image");				
			}
			
			int width = originalImage.getWidth();
			int height = originalImage.getHeight();
			
			boolean needsResize = width > MAX_WIDTH || height > MAX_HEIGHT;
			
			BufferedImage finalImage = originalImage;
			
			if (needsResize) {
				finalImage = Thumbnails.of(originalImage)
					.size(MAX_WIDTH, MAX_HEIGHT)
					.keepAspectRatio(true)
					.asBufferedImage();
			}
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(finalImage, "jpg", outputStream);
			byte[] imageBytes = outputStream.toByteArray();
			
			return new ProcessedImage(
				new ByteArrayInputStream(imageBytes),
				imageBytes.length,
				"image/jpeg"
			);
		} catch (Exception e) {
			throw new FileStorageException("Could not process the uploaded image : " + e.getMessage());
		}
		
	}
	
	public record ProcessedImage(InputStream inputStream, long size,String contentType) {};
}
