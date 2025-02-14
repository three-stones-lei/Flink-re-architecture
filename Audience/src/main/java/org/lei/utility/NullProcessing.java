package org.lei.utility;

public class NullProcessing {
    public static Object nullProcess(Object t){
        if(t == null){
            return "";
        }
        return t;
    }

}
