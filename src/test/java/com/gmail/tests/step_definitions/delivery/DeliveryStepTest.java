package com.gmail.tests.step_definitions.delivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.mail.Message;

import com.gmail.pageobjects.common.CommonMethods;
import com.gmail.pageobjects.common.ExcelFileReader;
import com.gmail.pageobjects.pages.EmailDeliveryPage;
import com.gmail.pageobjects.utils.email.DeliveyEmailField;
import com.gmail.pageobjects.utils.email.EmailMessageUtils;
import com.gmail.pageobjects.utils.email.EmailUtils;
import com.gmail.pageobjects.utils.email.Mailbox;
import com.gmail.pageobjects.utils.email.MailboxFactory;

import cucumber.api.java.en.Then;

public class DeliveryStepTest {

	public CommonMethods commonMethods = new CommonMethods();
	public EmailDeliveryPage emailDeliveryPage = new EmailDeliveryPage();
	public EmailUtils emailUtils = new EmailUtils();
	public ExcelFileReader excelFileReader = new ExcelFileReader();
	public EmailMessageUtils emailMessageUtils = new EmailMessageUtils();

	@Then("^the 'New Message' popup is(| not) displayed$")
	public void theNewMessagePopupIsDisplayed(String not) throws Throwable {
		assertEquals("Check whether 'New Message' popup is displayed", not.isEmpty(),
				emailDeliveryPage.isNewMessagePopupDisplayed());
	}

	@Then("^the user fills in the email fields as follows$")
	public void theUserFillsInTheEmailFieldsAsFollows(Map<String, String> emailOptions) throws Throwable {
		for (Map.Entry<String, String> entry : emailOptions.entrySet()) {
			try {
				emailUtils.editValue(DeliveyEmailField.getByFieldDisplayName(entry.getKey()), entry.getValue());
			} catch (Exception e) {
				throw new Exception("Could not find or modify field '" + entry.getKey() + "'", e);
			}
		}
	}

	@Then("^user receives an email at \"([^\"]*)\" with message \"([^\"]*)\" and with subject \"([^\"]*)\"$")
	public void userReceivesAnEmailAtWithMessageAndWithSubject(String email, String note, String subject)
			throws Throwable {
		Mailbox mailbox = MailboxFactory.getMailboxByEmail(email);
		Message message = mailbox.waitForMessageWithTitle(subject, 120, 10);
		assertTrue("The email does not contain '" + note + "' text ",
				emailMessageUtils.isEmailContainsText(message, note));
	}

}
