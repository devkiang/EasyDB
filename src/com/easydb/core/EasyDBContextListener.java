// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DreamDBContextListener.java

package com.easydb.core;

import com.easydb.util.EasyDBLog;
import com.easydb.util.XMLUtil;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// Referenced classes of package com.dreamdb.core:
//            DreamDBLog

public class EasyDBContextListener
    implements ServletContextListener
{

    public EasyDBContextListener()
    {
    }

    public void contextDestroyed(ServletContextEvent servletcontextevent)
    {
    }

    public void contextInitialized(ServletContextEvent arg0)
    {
        XMLUtil util = XMLUtil.getInstance();
        util.xmlName = "Easy";
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
                    System.out.println((new StringBuilder()).append(o.getClass()).append("加载完毕").toString());
                }
            }
            catch(InstantiationException e)
            {
                EasyDBLog.error("目录中包含错误的实体类");
                e.printStackTrace();
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch(ClassCastException e)
            {
                EasyDBLog.error((new StringBuilder("目录中包含非法的实体类:")).append(entityPathDot).append(".").append(name).append(",Dream实体类必须继承自EntityBase类").toString());
            }
            catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }

    }
}
