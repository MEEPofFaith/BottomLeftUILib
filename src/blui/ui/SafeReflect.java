package blui.ui;

import arc.util.*;
import mindustry.*;

import java.lang.reflect.*;

/**@author Smolkeys */
public class SafeReflect{
    protected static <T> T get(Class<?> type, Object object, String name){
        try{
            return Reflect.get(type, object, name);
        }catch(Exception e){
            return null;
        }
    }

    protected static Class<?> clazz(String name){
        try{
            return Vars.mods.mainLoader().loadClass(name);
        }catch(Exception e){
            return null;
        }
    }
}
