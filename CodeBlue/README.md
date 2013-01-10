Installation
------------
Install Java: ```sudo apt-get install openjdk-6-jre```
Install ADT Bundle: http://developer.android.com/sdk/installing/bundle.html 
Install Egit: http://www.vogella.com/articles/EGit/article.html

In terminal:
```git clone git@github.com:tgarv/code-blue-android-client.git```

In Eclipse:
File -> Import -> Git -> Projects from Git
Local
Navigate to repository
Next/Finish
Import existing projects
After project is created, might need to right click on the project name and Refactor -> rename to "CodeBlue"

To get Git working:
set GIT_SSH environment variable to point to your ssh (mine was /usr/bin/ssh)


Install Google API sdk:
Window -> Android SDK Manager
Check Google APIs under Android 2.3.3 and install
Go to Project -> Properties
Click on Android Tab
Set the Target Name to Google APIs
Set up API key
