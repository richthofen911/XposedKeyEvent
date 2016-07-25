package net.callofdroidy.demoxposed;

import android.graphics.Color;
import android.widget.TextView;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Created by admin on 25/07/16.
 */
public class Tutorial implements IXposedHookLoadPackage{
    public void handleLoadPackage(final LoadPackageParam loadPackageParam) throws Throwable{
        if(!loadPackageParam.packageName.equals("com.android.systemui"))
            return;

        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", loadPackageParam.classLoader,
                "updateClock", new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        TextView tv = (TextView) param.thisObject;
                        String text = tv.getText().toString();
                        text = text + " :)";
                        tv.setText(text);
                        tv.setTextColor(Color.RED);
                    }
                });
        XposedBridge.log("We are in SystemUI!");
    }
}
