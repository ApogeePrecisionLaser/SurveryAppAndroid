package com.apogee.fleetsurvey.scanmodule;

public class CommonActivityUtil {


    public static String BUNDLEIDENTIFIER="finalValue";

    public static CommonActivityUtil commonActivityUtilInstance;
    public CommonActivityUtil() {
    }


    public static synchronized CommonActivityUtil getSingletonObject()
    {
        if (commonActivityUtilInstance == null)
        {
            commonActivityUtilInstance = new CommonActivityUtil();
        }
        return commonActivityUtilInstance;
    }
    public Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }
    public void clear()
    {
        commonActivityUtilInstance = null;
    }
    public CommonActivityUtil(Class classname) {
        this.classname = classname;
    }

    public Class getClassname() {
        return classname;
    }

    public void setClassname(Class classname) {
        this.classname = classname;
    }

    Class classname;


}
