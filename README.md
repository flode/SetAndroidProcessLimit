Set Android background process limit on boot.


1. Build with Android Studio.
2. **Copy to /system/priv-app/ as a priviledged system app.**
3. Reboot
4. Start the app and set the number of background processes
5. Done!
 
The process limit will also automatically be set when the device is restarted.


Caution: Only tested on Android 4.4.4, should work from 4.0 onward


# Alternative ways without installing the app
## Commandline
It can be done with root access with a single command.

    service call activity 51 i32 x

Where x is the number of background processes you want.

If the API changes in the future, it's easy to look it up again.
http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.4.4_r1/android/app/IActivityManager.java?av=f

Look for the line with SET_PROCESS_LIMIT_TRANSACTION

Then just extrapolate that info to http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/DESIRED_ANDROID_VERSION/android/app/IActivityManager.java?av=f

Replacing DESIRED_ANDROID_VERSION with the version you're interested in.


## Tasker

For Tasker users, another app-based mechanism to set this value is via Secure Settings, which does work for me on all of Android 2.3 ... 4.4 as of this writing (I have no 5.x devices to test):

https://play.google.com/store/apps/details?id=com.intangibleobject.securesettings.plugin

The function is Root Actions -> Background App Processes and offers the usual set of default/none/1/2/3/4 options. Secure Settings can be used without Tasker by creating a shortcut, but to run at boot, you need Tasker (or Locale or similar) to use Secure Settings as a plugin, or some other app capable of launching shortcuts at boot to trigger a Secure Settings shortcut action.

(Tasker's profile context for boot-complete is Event -> System -> System Boot. The task it executes should contain an action Plugins -> Secure Settings using the above Secure Settings function.)


# Licence

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org/>
