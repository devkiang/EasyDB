package com.easydb.core;

import com.easydb.util.DreamDBLog;


public class DAOImpSingleton {
	private static EntityDAOMySQLImplement mySQLImplement;
	public static EntityDAOMySQLImplement getMySQLImplement(){
		if(mySQLImplement==null){
			mySQLImplement=new EntityDAOMySQLImplement();
			DreamDBLog.debug("EntityDAOMySQLImplement启动完毕");
		}
		return mySQLImplement;
	}
}
