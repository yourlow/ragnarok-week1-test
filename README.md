Hi all,

if you get the game working controls are wasd or you can use mouseclick to move

NOTE before setting up this project would recommend going through at least the first 5 minute of this tutorial 
https://gamefromscratch.com/libgdx-video-tutorial-series/

if you have intellij setup on windows getting this running is hopefully as simple as

1. cloning the repo 

2. in intellij opening the build.gradle file

3. going into the desktop 'module' and right clicking DesktopLauncher and running it

if that doesn't work you could try cloning this repo, then generating your own project and copying the files
from my repo into your newly created project

1.)

go create a libgdx project i followed the intellij tutorial from this
https://gamefromscratch.com/libgdx-video-tutorial-series/
(make sure you add desktop into the project)

![alt text](https://github.com/yourlow/ragnarok-week1-test/blob/master/setup.PNG?raw=true)

2.)

copy and paste these two files into your newly created libgdx project 

    (make sure that 'MyGdxGame.java' name matches what you named your main libgdx Game Class)

    ragnarok-week1-test/core/src/com/mygdx/game/MyGdxGame.java

    ragnarok-week1-test/desktop/src/com/mygdx/game/desktop/Desktoplauncher.java


and this directory into your respective core assets directory

    ragnarok-week1-test/core/assets/glassy
    
