@now
Feature: As a user I can login gmail 

Scenario Outline: 
	Given Gmail user is logged in with the following details 
		| userName | ResearchATesting1 |
	When the user clicks on button "COMPOSE" 
	Then the 'New Message' popup is displayed 
	Then the user fills in the email fields as follows 
		| To          | <mailbox>     |
		| Subject     | <subject>     |
		| MessageText | <messageText> |
	Then the user clicks on button "Send" 
	Then user receives an email at "<mailbox>" with message "<messageText>" and with subject "<subject>" 
	Examples: 
		| mailbox               | messageText |subject     |
		| GmailTestA@yandex.com | Text        |SubjectTest |
		
		
		
