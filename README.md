
XposedKeyEvent
Use Xposed to replace my power button event with others, the button is hard to press now <p>

consider adding a hook on method android.media.AudioManager handleKeyDown(), after the method is executed, <p>
call PowerManager to light up the screen

