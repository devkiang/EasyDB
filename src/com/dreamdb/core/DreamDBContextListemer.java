// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DreamDBContextListener.java

package com.dreamdb.core;

import com.dreamdb.util.DreamDBLog;
import com.dreamdb.util.XMLUtil;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// Referenced classes of package com.dreamdb.core:
//            DreamDBLog

public class DreamDBContextListemer
    implements ServletContextListener
{

    public DreamDBContextListemer()
    {
    }

    public void contextDestroyed(ServletContextEvent servletcontextevent)
    {
    }

    public void contextInitialized(ServletContextEvent arg0)
    {
        XMLUtil util = XMLUtil.getInstance();
        util.xmlName = "Dream";
        util.parseCoreXML();
        String configPath = (new StringBuilder(String.valueOf(getClass().getClassLoader().getResource("").getPath()))).append(XMLUtil.getInstance().getEntityPath().replace(".", "/")).toString();
        String entityPath = (new StringBuilder()).append(configPath).toString();
        String entityPathDot = XMLUtil.getInstance().getEntityPath().replace(".", "/").replace("/", ".");
        File file = new File(entityPath);
        File files[] = file.listFiles();
        File afile[];
        int j = (afile = files).length;
        for(int i = 0; i < j; i++)
        {
            File subFile = afile[i];
            int dot = subFile.getName().lastIndexOf(".");
            String hz = subFile.getName().substring(dot, subFile.getName().length());
            String name = subFile.getName().substring(0, dot);
            try
            {
                if(hz.equals(".class") || hz.equals(".java"))
                {
                    EntityBase o = (EntityBase)Class.forName((new StringBuilder(String.valueOf(entityPathDot))).append(".").append(name).toString()).newInstance();
                    o.autoCreateTable();
                    System.out.println((new StringBuilder()).append(o.getClass()).append(" \uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD").toString());
                }
            }
            catch(InstantiationException e)
            {
                DreamDBLog.error("\u013F\274\uFFFD\u0430\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\u02B5\uFFFD\uFFFD\uFFFD\uFFFD");
                e.printStackTrace();
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch(ClassCastException e)
            {
                DreamDBLog.error((new StringBuilder("\u013F\274\uFFFD\u0430\uFFFD\u01F7\uFFFD\uFFFD\uFFFD\u02B5\uFFFD\uFFFD\uFFFD\uFFFD:")).append(entityPathDot).append(".").append(name).append(",Dream\u02B5\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\u0333\uFFFD\uFFFD\uFFFDEntityBase\uFFFD\uFFFD").toString());
            }
            catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }

    }
}
