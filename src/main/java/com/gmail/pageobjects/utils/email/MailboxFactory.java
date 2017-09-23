package com.gmail.pageobjects.utils.email;

import com.gmail.pageobjects.common.ExcelFileReader;

public class MailboxFactory {

    public static Mailbox getMailboxByEmail(String email) {
        String password = ExcelFileReader.getEmailPassword(email);
        return getMailboxByEmail(email, password);
    }

    public static Mailbox getMailboxByEmail(String email, String password) {
        String[] parts = email.split("@");
        return new ImapsMailbox(parts[0], parts[1], password);
    }
}
