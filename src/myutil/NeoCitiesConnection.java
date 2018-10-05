/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author nbp184
 */
public class NeoCitiesConnection {
    
    private String sitename;
    private String password;
    
    public NeoCitiesConnection(String sitename, String password) {
        this.sitename = sitename;
        this.password = password;
    }
    
    public int post(String filename, String[] data) throws IOException {
        URL url = new URL("https://neocities.org/api/upload");
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary((sitename +":" +password).getBytes()));
        
        String boundary = Long.toHexString(System.currentTimeMillis());
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        
        String charset = "UTF-8";
        String param = "file.txt";
        String CRLF = "\r\n";
        
        // Send post request
        con.setDoOutput(true);
       try (
            OutputStream output = con.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
        ) {
            // Send text file.
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"" +filename +"\"; filename=\"" +filename +"\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
            writer.append(CRLF).flush();
            for(String line : data) {
                writer.append(line);
                writer.append(CRLF);
            }
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

            writer.append("--" + boundary + "--").append(CRLF).flush();
        }

        return con.getResponseCode();
    }
    
    public int getFileList() throws IOException {
        URL url = new URL("https://neocities.org/api/list");
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        String userpass = "filetest" + ":" + "farscape";
        con.setRequestProperty("Authorization", "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes()));
        int responseCode = con.getResponseCode();
        if(responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String inputLine = in.readLine();
            while (inputLine != null) {
                buffer.append(inputLine +"\n");
                inputLine = in.readLine();
            }
            in.close();
            System.out.println(buffer.toString());
        }
        
        return responseCode;
    }
    
}
