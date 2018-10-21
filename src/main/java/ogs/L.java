package ogs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author w7-uno
 */
public class L {
    public static final int INFO=1;
    public static final int WARN=2;
    public static final int DEBUG=3;
    public static final int ERROR=4;
    public static final int FATAL=5;

    private static final Log logger = LogFactory.getLog(L.class);

    public static void L(String l) {
        logger.info(l);

    }
    public static void L(int typeLogging,String l) {
             if(typeLogging==INFO)
                logger.info(getCallerClassMethodName()+":"+l);
        else if(typeLogging==WARN)
                logger.warn(getCallerClassMethodName()+":"+l);
        else if(typeLogging==DEBUG)
                logger.debug(getCallerClassMethodName()+":"+l);
        else if(typeLogging==ERROR)
                logger.error(getCallerClassMethodName()+":"+l);
        else if(typeLogging==FATAL)
                logger.fatal(getCallerClassMethodName()+":"+l);

    }
//https://stackoverflow.com/questions/11306811/how-to-get-the-caller-class-in-java
    
    public static String getCallerClassMethodName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(L.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                return ste.getClassName()+":"+ste.getMethodName();
            }
        }
        return null;
    }

}
