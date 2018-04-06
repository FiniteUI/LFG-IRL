# LFG-IRL, Fall 2017
This is the semester group project for my Fall 2017 Software Engineering class.

This was my second group programming project, and my first with a group this large. There were 12 students in this group. This project was meant mostly to teach students the difficulties of group software developement.

For this project we had to go through the entire software developement process. We made a group, came up with an idea, wrote a specification, made a design, implemented it, and presented it.

Our application, LFG-IRL, is an application for finding groups of people for activities.

Originally intended to be a web application, we decided to switch to a desktop application (I was outvoted).

We used a MySQL database and an FTP server hosted on HelioHost.

Most of the windows were made using WindowBuilder in Eclipse.

The exact project requirements are in SESemesterProjectRequirements.pdf

Specification.pdf is our original specification and proposal for the project, with the entire feature list.

Design.pdf is our design documentation, with four different designs and analysis of them.

Presentation.pdf is the slideshow that accompanied our presentation.

Brochure.pdf is a handout we gave to the class during our presentation.

FutureWork.pdf is a list of features we were not able to implement and would like to in the future.

## Code:
### Code I wrote:
AddMember.java, a module for adding a member to a group.

AddReview.java, a module for reviewing a user.

BlockUser.java, a module for blocking a user from a group.

CreateGroup.java, a module for creating a new group.

DeleteGroup.java, a module for deleting a group.

GetCategories.java, a module for retrieving the most popular group categories.

GetMembers.java, a module to retrieve the members of a group.

GroupFoundation.java, the main window for all group operations and the main window of the application.

GroupSearch.java, a module to search for groups.

OnlineChat.java, a module to for maintaining the online chat file for each group.

RemoveMember.java, a module for removing a member from a group.

### Code I worked on:
chatmain.java, the main window for the chat room.

LoginMember.java, the login window.

RegistrationForm.java, the window for new user registration.

RevModule.java, the window for reviewing a user.

UpdateAccount.java, the window for changing account details.

ConnectionMySQL.java, a module for connecting to the online database.

### Other Code:
DriverWindow.java, a background window that the application runs from.

LoginFail.java, a window for a failed login.

UsersAgreement.java, a window for displaying the User Agreement.

ViewGroups.java, a module for retrieving groups that a user is in.

WelcomeUser.java, the first window after Login, to choose update account or view groups.



