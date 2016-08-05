package net.callofdroidy.demoxposed;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.graphics.Color;
import android.os.PowerManager;
import android.widget.TextView;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Created by admin on 25/07/16.
 */
public class Tutorial implements IXposedHookLoadPackage{

    @Override
    public void handleLoadPackage(final LoadPackageParam loadPackageParam) throws Throwable{
        if (!loadPackageParam.packageName.equals("android.media"))
            return;
        XposedBridge.log("now in android.media");
        Class<?> audioManager = XposedHelpers.findClass("android.media.AudioManager", loadPackageParam.classLoader);
        XposedBridge.hookAllMethods(audioManager, "handleKeyDown", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Context context = AndroidAppHelper.currentApplication();
                //Context context = (Context)param.getResult();
                PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                if(!powerManager.isScreenOn()){
                    PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");

                    wl.acquire(10000);
                    PowerManager.WakeLock wl_cpu = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");

                    wl_cpu.acquire(10000);
                }
            }
        });

    }
}
