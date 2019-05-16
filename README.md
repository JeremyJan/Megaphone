# MEGAPHONE SPRINT ONE

## Contributors
Aaron Shouldis
Brandon Morris
Jeremy Manandic

## Features implemented (Sprint One)

Create an account - User is able to create an account that has the information email, username, password. This is stored in a database in Heroku.

Log in with an existing account - User is able to log in after an account has been created.

User information is stored into a database - The user information is stored in a database

View rooms - User is able to see the rooms joined.

Create a room - User is able to create a room

## User stories implemented (Sprint One)

User creates an account: High Priority
Description: The user opens the application and is prompted with the login screen where they can enter their username and password to go further. This user has not yet set up an account, so they select the “Create New Account” button. The button moves the user to a new screen where they can enter all the relevant information to create a new account. After filling out the form, they select the “Submit” button, which creates a new account in the account database and sends the customer back to the login screen.

Bugs/Deficiencies: None

User logs in with existing account: High Priority
Description: The user opens the application and is prompted with the login screen where they can enter their username and password to go further. They already have a registered account, so they enter their username and password into the respective fields and click the “Sign In” button. After the system verifies that the username and password are correct, the user is moved to the home screen.

Bugs/Deficiencies: 
Deficiency: Doesn’t validate through the database but it does validate through a regex in the code. So the only thing the two edittext fields look at is if the email follows the regex and if the password is longer than or equal to six.

User creates a new group chat: High Priority
Description: 
The user is at the main home screen and wants to set up a new geofence based group chat. They select the “Create New Group” button. The user is shown a new screen with options for their new chat to be set before creation. They enter the name for the chat, the radius size of the geofence, and other permissions like whether they want non admin users to 
be able to send messages in the chat. After filling out the options, the user selects the “Create” button at the bottom of the menu and the group is created. The user is navigated to their group list page and you the new group has been added to the list.

Bugs/Deficiencies: 

Deficiency: The radius slider and the toggle button don’t actually do anything. The things that get returned are default values for now. Theses things will be implemented in the next sprint. The reason for this is because the database cannot take a zero value for anything.