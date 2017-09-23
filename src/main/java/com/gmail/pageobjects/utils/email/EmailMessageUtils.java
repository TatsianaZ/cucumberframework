package com.gmail.pageobjects.utils.email;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailMessageUtils {

	protected static final Logger LOG = LoggerFactory.getLogger(Mailbox.class);

	private static final String BASE_PATH = "C:/temp/test-downloads";
	private static final String DATE_PATTERN_FOR_FOLDER = "yyyy-MM-dd_hh-mm-ss";

	public boolean isEmailContainsText(Message message, String text) throws Exception {
		return getMessageBody(message).toLowerCase().contains(text.toLowerCase());
	}

	public boolean isEmailContainsRegex(Message message, String regex) throws Exception {
		Pattern p = Pattern.compile(regex);
		return p.matcher(getMessageBody(message)).find();
	}

	public String getMessageBody(Message message) throws Exception {
		if (message.isMimeType("text/*")) {
			return message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			String result = "";
			Multipart multipart = (Multipart) message.getContent();
			int count = multipart.getCount();
			for (int i = 0; i < count; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				if (bodyPart.isMimeType("text/*")) {
					result = result + "\n" + bodyPart.getContent();
					break;
				}
			}
			return result;
		}
		throw new Exception("Message text was not found");
	}

	public String retrieveAttachmentName(Message message) throws Exception {
		if (message.isMimeType("multipart/*")) {

			Multipart multipart = (Multipart) message.getContent();

			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && bodyPart.getFileName() != null) {
					continue;
				}
				return bodyPart.getFileName();
			}
		}
		throw new Exception("Attachment file not found");
	}

	public File downloadAttachment(Message message) throws Exception {
		return downloadAttachment(message, BASE_PATH);
	}

	public File downloadAttachment(Message message, String baseFolder) throws Exception {

		File file = null;
		Multipart multipart = (Multipart) message.getContent();

		for (int i = 0; i < multipart.getCount(); i++) {
			BodyPart bodyPart = multipart.getBodyPart(i);
			if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && bodyPart.getFileName() != null) {
				continue;
			}
			InputStream is = bodyPart.getInputStream();

			file = new File(baseFolder + "/" + new SimpleDateFormat(DATE_PATTERN_FOR_FOLDER).format(new Date()) + "/" + bodyPart.getFileName());
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buf)) != -1) {
				fos.write(buf, 0, bytesRead);
			}
			fos.close();
		}

		return file;
	}

	public String getAttachmentExtension(Message message) throws Exception {
		String attachmentName = retrieveAttachmentName(message);
		if (attachmentName == null || attachmentName.isEmpty()) {
			throw new Exception("Attachment file not found");
		}
		String[] parts = attachmentName.split("\\.");
		if (parts.length < 2) {
			LOG.info("File name does not contain extension: " + attachmentName);
			return "";
		}
		return parts[parts.length - 1];
	}

	public boolean hasAttachment(Message message) {
		String attachmentName;
		try {
			attachmentName = retrieveAttachmentName(message);
		} catch (Exception e) {
			return false;
		}
		return attachmentName.isEmpty();
	}
	
}
