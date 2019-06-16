package com.rkylin.crawler.engine.flood.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;

public class SystemUtil {

    private static final transient Logger logger = Logger.getLogger(SystemUtil.class);

    public static String getLocalHostName() {
        String hostName = null;

        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return hostName;
    }

    public static List<String> getAllIP() {
        List<String> ipList = new ArrayList<>();
        try {
            String hostName = getLocalHostName();
            if (hostName != null) {
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if (addrs.length > 0) {
                    for (int i = 0; i < addrs.length; i++) {
                        String ip = addrs[i].getHostAddress();
                        if (StringUtil.isIP(ip)) {
                            ipList.add(ip);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return ipList;
    }

    public static String getIP() {
        String ip = null;

        List<String> ipList = getAllIP();
        if (ipList.size() > 0) {
            ip = ipList.get(0);
        }

        return ip;
    }

    /**
     * 取得本机一个非回环地址IPv4 IP
     * 
     * @param preferIpv4
     * @param preferIPv6
     * @return
     * @throws SocketException
     */
    public static InetAddress getFirstNonLoopbackAddress(boolean preferIpv4, boolean preferIPv6) throws SocketException {
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface ni = en.nextElement();
            for (Enumeration<InetAddress> en2 = ni.getInetAddresses(); en2.hasMoreElements();) {
                InetAddress addr = en2.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        if (preferIPv6) {
                            continue;
                        }
                        return addr;
                    }
                    if (addr instanceof Inet6Address) {
                        if (preferIpv4) {
                            continue;
                        }
                        return addr;
                    }
                }
            }
        }
        return null;
    }
}
