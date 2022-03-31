/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
        import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class GiaiPhapDiemDanh {

    public static void main(String[] args) {
        
        // execute cmd bang code runtime process
        
            Runtime rt = Runtime.getRuntime();
    try {
        Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 192.168.1.11");
//rt.exec(new String[]{"cmd.exe","/c","ping -n 1 192.168.1.31"});
        int returnVal = p1.waitFor();
    if(returnVal == 0){
        System.out.println("\nPing duoc ");
    }
    else{
        System.out.println("\ndeo duoc roi ");
    }
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }   catch (InterruptedException ex) {
            Logger.getLogger(GiaiPhapDiemDanh.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
        // execute cmd bang code processBuilder
        
        
        ProcessBuilder processBuilder = new ProcessBuilder();
        // Windows
        //processBuilder.command("cmd.exe", "/c", "ping -n 3 google.com");
          processBuilder.command("cmd.exe", "/c", "FOR /L %i in (1,1,255) do "
                  + "@ping -n 1 192.168.1.%i | find \"Reply\"");
          int dem = 0;
        try {

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                // neu line ton tai dong Interface: 192.168.120.1 thi trong luc doc lien tuc
                // neu line tiep theo co ton tai dynamic thi
                // lay MAC add tai line do, doi chieu voi MAC add cua toan bo SV dang hoc BuoiHoc
                // Neu giong nhau thi Vang = co mat, auto gui thong bao SV da diem danh
                if(dem == 10){
                    process.destroy();
                }
                System.out.println("dong " + dem + line);
                dem++;
                
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        /// tim HOST ID cua adapter mang dang ket noi internet


    try(final DatagramSocket socket = new DatagramSocket()){
   socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
   String ip = socket.getLocalAddress().getHostAddress();
    System.out.println("dia chi HOST ID: " + ip);
}       catch (SocketException ex) {
            Logger.getLogger(GiaiPhapDiemDanh.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(GiaiPhapDiemDanh.class.getName()).log(Level.SEVERE, null, ex);
        }
;
        
        
        

    }

}