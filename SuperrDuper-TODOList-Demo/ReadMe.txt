
################# SuperrDupper-TODOList-Demo #################
	
# Frameworks used
	* Spring Boot (v1.5.0))
	* Hibernate (Using Spring Data JPA)
	* h2 database
	* Spring test
	* Mockito
	* JUnit

# Project setup
	* Import the project as a maven project in EclipseIDE
	
# Run the project
	* Run the Application.java as an application
	
# Rest services
	There are three REST service ItemLists, Items and Reminders 
	* ItemList service
		http://localhost:8080/itemLists
		Supports GET (Reads all item lists), POST
	
	* Item service
		http://localhost:8080/itemLists/{itmLitId}/items
		Supports GET (Read all items in a item list), POST and PUT
		Does not supports DELETE as a deleted item needs to be restored. Therefore used the flag "active". 
		("active == false" means deleted)
		
	* Reminder service
		http://localhost:8080/itemLists/{itmLitId}/items/{itemId}/reminders
		Supports GET (Reads all reminders in a item) and POST
		
# Class diagram is designed with StarUML