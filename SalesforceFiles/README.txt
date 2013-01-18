Script for pulling Salesforce.com packages and saving them to the git repo
Author: Ray Tam
Date: 1/17/13
Issue: MM-15

Revisions

Author:
Date:
Description:

Author:
Date:
Description:

******INSTRUCTIONS
Read below for how to configure the build.properties file.

Use the command 'ant retrievePkg' to pull a package.
                'ant deployCode' to upload a package to the specified user
                from a local directory

setup your git to git push without a password
https://confluence.atlassian.com/pages/viewpage.action?pageId=270827678
(Brian I need to configure your server for this -Ray 1/18)

******FILES
---build.properties
Includes the username, password and package fields. Currently using this
file does not work [1/17, Ray]

This works don't include brackets in the fields.

After entering your username and password specify the package you want to pull
in sf.pkgName. For sf.serverurl use https://login.salesforce.com. All of these
fields will fill out the corosponding fields in build.xml
   (For the password you must have Modify All Data permissions, you can check
    to see if you have this under Administration Setup > Manage Users >
    Profiles)
   (To get a security token go to Personal Setup > Reset My Security Token.
    From there reset your security token and you should get an email that
    gives you your new security token)

---build.xml
To get a package off of a Salesforce org use target: retrievePkg
Username: <YourUserName>
Password: <Your Password With Security Token Appended>
Server URL: https://login.salesforce.com
retrieveTarget: <Name of directory to put package. Create a new directory
                 if getting a new package>
packageNames: <Name of package to pull off Salesforce>

To deploy/upload a package from a local directory use target:deployCode
deployRoot: <Location of local directory that you want to upload to org>
(Note the username and password are going to be for the account you are going
 to upload to). In between the <runTest> tags put the name of the test that
is inside the package.
