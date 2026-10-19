package com.ludo.ms_user.service_implementation.utilitaire;

import java.util.concurrent.Executor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.config.MsUserConfiguration;

import com.ludo.ms_user.service_interface.utilitaire.EmailServiceInt;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailServiceInt {
	
	private final MsUserConfiguration config;
	private final JavaMailSender mailSender;
	private final Executor emailExecutor;
	
	private void sendEmail(String emailText, String to, String subject, String from) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			helper.setText(emailText, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setFrom(from);
			
			mailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			throw new IllegalStateException("failed to send email" + e);
		}
	}

	private void prepareActivationEmail(String to, String from, String activationToken) {
		String emailText = "Your activation password is " + activationToken;
		
		emailExecutor.execute(() ->
			sendEmail(emailText, to, "Confirm your email", from)
		);
	}
	
	private void prepareresetPasswordEmail(String to, String from, String passwordToken) {
		String emailText = "follow this link for reset your password : \n"
				+ config.getClientAppUrl() + "/reset-password/" + passwordToken;
		
		emailExecutor.execute(() ->
		sendEmail(emailText, to, "reset your password", from)
				);
	}

	@Override
	public void initialiseEmail(String mailType, String to, String token) {
		
		if (config.isEmailSenderIntern()) {
			String from = config.getEmailSenderApp();
			
			switch (mailType) {
			case "ACTIVATION_MAIL": {
				prepareActivationEmail(to, from, token);
				break;
			}
			case "RESET_PASSWORD_MAIL": {
				prepareresetPasswordEmail(to, from, token);
				break;
			}
			default:
				throw new IllegalArgumentException(mailType + " not implemented on EmailService");
			}
		}else {
//			TODO: si il y a un microservice de mailing implementer l'envois ici
		}
		
	}
	
	
}
