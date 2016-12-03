package edu.sjsu.cmpe275.project.notification;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.UserService;

/**
 * @author Onkar Ganjewar
 *
 */
@Component
public class CustomMailSender {

	@Autowired
	private UserService service;

	@Autowired
	private MessageSource messages;

	@Autowired
	private Environment env;

	@Autowired
	private MailSender javaMailSender;

	@Async
	public Future<Void> sendMail(User user, String appUrl) throws InterruptedException {

		final String token = UUID.randomUUID().toString();
		service.createVerificationTokenForUser(user, token);

		final SimpleMailMessage email = constructEmailMessage(user, token, appUrl);
		javaMailSender.send(email);
		System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
		return new AsyncResult<Void>(null);
	}

	private final SimpleMailMessage constructEmailMessage(final User user, final String token, final String appUrl) {
		final String recipientAddress = user.getEmail();
		final String subject = "Registration Confirmation";
		final String confirmationUrl = appUrl + "/registrationConfirm.html?token=" + token;
		final String message = messages.getMessage("message.regSucc", null, Locale.getDefault());
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + " \r\n" + confirmationUrl);
		email.setFrom(env.getProperty("support.email"));
		return email;
	}
}