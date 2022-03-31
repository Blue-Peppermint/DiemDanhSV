/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestField;

import java.io.IOException;
import java.net.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class search {
    @SuppressWarnings("empty-statement")
    public static void main(String args[]) throws UnknownHostException{

        // tim dia chia MAC dua vao dia chi IP
        InetAddress localIP = InetAddress.getByName("192.168.1.6");
//NetworkInterface ni = NetworkInterface.getByInetAddress(localIP);
//byte[] macAddress = ni.getHardwareAddress();
        
        
    InetAddress ip = InetAddress.getByName("192.168.1.6");;
    try {
            ip = InetAddress.getLocalHost();
        System.out.println("Current IP address : " + ip.getHostAddress());
        
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

        byte[] mac = network.getHardwareAddress();
            
        System.out.print("Current MAC address : ");
            
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
        }
        System.out.println(sb.toString());
            
    } catch (SocketException e){
    }

        
        //
        Vector<String> Available_Devices=new Vector<>();
        String myip=InetAddress.getLocalHost().getHostAddress();
        
        myip = "192.168.1.13";
        
        String mynetworkips=new String();

        for(int i=myip.length();i>0;--i) {
            if(myip.charAt(i-1)=='.'){ mynetworkips=myip.substring(0,i); break; }
        }

        System.out.println("My Device IP: " + myip+"\n");


        System.out.println("Search log:");
        for(int i=250;i<=254;++i){
            try {
                InetAddress addr=InetAddress.getByName(mynetworkips + new Integer(i).toString());
                if (addr.isReachable(1000)){
                    System.out.println("Available: " + addr.getHostAddress());
                    Available_Devices.add(addr.getHostAddress());
                    ///
//                  NetworkInterface network = NetworkInterface.getByInetAddress(addr.getLocalHost());
//            
//        byte[] mac = network.getHardwareAddress();
//
//                            StringBuilder sb = new StringBuilder();
//                for (int j = 0; j < mac.length; j++) {
//            sb.append(String.format("%02X%s", mac[j], (j < mac.length - 1) ? "-" : ""));		
//        }
//        System.out.println("MAC: " + sb.toString());
                
                }
                else System.out.println("Not available: "+ addr.getHostAddress());

            }catch (IOException ioex){}
        }

        System.out.println("\nAll Connected devices(" + Available_Devices.size() +"):");
        for(int i=0;i<Available_Devices.size();++i) System.out.println(Available_Devices.get(i));
    }
}