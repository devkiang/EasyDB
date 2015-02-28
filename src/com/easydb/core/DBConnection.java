package com.easydb.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class DBConnection {
    //这是主连接，每个操作第一次所启动的就是主连接
    private Connection conn;
    private static int i = 0;
    private String pk;
    private long openTime;
    private long closeTime = -1;
    private String connKey;

    private Map<String, Connection> conns;

    public DBConnection(Connection conn) {
        i++;
        openTime = System.currentTimeMillis();
        pk = openTime + "-" + i;
        this.conn = conn;
    }

    public int getHandleConnCount() {
        if (conns == null) {
            return 1;
        } else {
            return conns.size();
        }
    }

    public Connection getConn() {
        return conn;
    }

    public Connection getConn(String connKey) {
        return (Connection) conns.get(connKey);
    }

    public String getPk() {
        return pk;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void setConnKey(String connKey) {
        this.connKey = connKey;
    }

    public String getConnKey() {
        return connKey;
    }

    public boolean isHasConn(String connKey) {
        if (conns == null) {
            return false;
        }
        return conns.containsKey(connKey);
    }

    /**
     * 当一个EntityConnection所掌管的连接多于一个
     *
     * @param key
     * @param c
     */
    public void addConn(String key, Connection c) {       
        if (conns == null) {
            conns = new HashMap<String, Connection>();
            //先将原来的加到对象里
            conns.put(connKey, conn);
        }
        conns.put(key, c);
        //当增加一个时原连接的主则变为当前这个连接
        connKey = key;
        conn = c;
    }

    /**
     * 设置当前活跃的连接
     *
     * @param connKey
     */
    public void setCurrentConn(String connKey) {
        this.connKey = connKey;
        conn = getConn(connKey);
    }

    /**
     * 获得所管理的全部连接
     *
     * @return
     */
    public Collection<Connection> getAllConns() {
        return conns.values();
    }


    /**
     * 回滚事务
     */
    public void rollback() {

        if (getHandleConnCount() == 1) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Collection<?> alls = getAllConns();
            for (Iterator<?> iterator = alls.iterator(); iterator.hasNext();) {
                Connection c = (Connection) iterator.next();
                try {
                    c.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        c.setAutoCommit(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 提交事务
     */
    public void commit() {

        if (getHandleConnCount() == 1) {
            try {
                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Collection<?> alls = getAllConns();
            for (Iterator<?> iterator = alls.iterator(); iterator.hasNext();) {
                Connection c = (Connection) iterator.next();
                try {
                    c.commit();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        c.setAutoCommit(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}