# ProgArk-Survive

## How to run

The project can be run in multiple ways. You can run it via Gradle, Intellij or Android Studio. The easiest way is to load the project into Intellij or
Android Studio and run it on an emulated device. This should be as simple as selecting the device, and clicking run in the toolbar of your IDE. You will not
have access to the high score list and may be prometed with an error code. This is because the Google Play Game Services project is setup only for testing 
and you most likely do not have the required access. 


To be able to run multiplayer you'll have to run two devices on the same network. If these devices are emulated you'll need to setup port redirection on the 
device acting as the game host. This can be done by telneting into the emulator with a command like this ```telnet localhost 5554``` (your port may be different.
You can list devices with the command ```adb devices```). When you are inside the emulator you'll be provided with instructions on how to authenticate. When this
is done you can setup the redirection by running these two commands. 

```
redir add tcp:54555:54555
redir add udp:54777:54777
```
One will not be able to view or add highscores when running the game trough an IDE becauseof wrong SHA1-key used in the build.  To have this functionality you will have follow thise steps. 
You  may  choose  to  opt  for  either  downloading  the  game  trough  Google  Play  or  downloading  the ProgArk-Survive.apk

Google Play
•Navigate to https://play.google.com/store/apps/details?id=com.mygdx.progarksurvive.
•Download the game.•Accept any verification popups that might appear.  If you choose not to do this you will notbe able to view highscores or add a new highscore.•After  installation  you  should  be  able  to  run  the  game  like  any  other  app  on  your  androiddevice.

ProgArk-Survive.apk
•Download the APK fromhttps://drive.google.com/file/d/1t6KKtW9GPVNHWiDq_R23SOh9wyS9GCQU/view?usp=sharingon to your phone.
•Run the installation process.
•Accept any verification popups that might appear.  If you choose not to do this you will notbe able to view highscores or add a new highscore.
•After  installation  you  should  be  able  to  run  the  game  like  any  other  app  on  your  androiddevice
