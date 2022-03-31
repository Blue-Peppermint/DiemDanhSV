package TestField;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App{
    
   public static void main(String[] args){
      
       // xuat tat ca host id
       
       Enumeration ec = null;
       try {
           ec = NetworkInterface.getNetworkInterfaces();
       } catch (SocketException ex) {
           Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
       }
while(ec.hasMoreElements())
{
    NetworkInterface n = (NetworkInterface) ec.nextElement();
    Enumeration ee = n.getInetAddresses();
    while (ee.hasMoreElements())
    {
        InetAddress i = (InetAddress) ee.nextElement();
        System.out.println(i.getHostAddress());
    }
}
       
       
       
       
       // xuat ra dia chi mac cua dia chi Host IP
    InetAddress ip;
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
            
    } catch (UnknownHostException e) {
        
        e.printStackTrace();
        
    } catch (SocketException e){
            
        e.printStackTrace();
            
    }
        
   }

}