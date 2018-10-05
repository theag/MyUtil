/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.test;

import myutil.NeoCitiesConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Base64;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author nbp184
 */
public class TestMain {
    
    private static final boolean USING_PROXY = true;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if(USING_PROXY) {
            System.setProperty("https.proxyHost", "isasrv02.nbpower.com");
            System.setProperty("https.proxyPort", "80");
            System.setProperty("https.proxyUser", "nbp184");
            System.setProperty("https.proxyPassword", "Ott@wa2016");
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    if (getRequestorType() == Authenticator.RequestorType.PROXY) {
                        String prot = getRequestingProtocol().toLowerCase();
                        String host = System.getProperty(prot + ".proxyHost", "");
                        String port = System.getProperty(prot + ".proxyPort", "80");
                        String user = System.getProperty(prot + ".proxyUser", "");
                        String password = System.getProperty(prot + ".proxyPassword", "");

                        if (getRequestingHost().equalsIgnoreCase(host)) {
                            if (Integer.parseInt(port) == getRequestingPort()) {
                                // Seems to be OK.
                                return new PasswordAuthentication(user, password.toCharArray());  
                            }
                        }
                    }
                    return null;
                }  
            });
        }
        
        NeoCitiesConnection con = new NeoCitiesConnection("filetest", "farscape");
        con.getFileList();
        
    }
    
    private static void deleteInfo() throws IOException {
        URL url = new URL("https://neocities.org/api/delete");
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(("filetest:farscape").getBytes()));
        
        String boundary = Long.toHexString(System.currentTimeMillis());
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        
        String charset = "UTF-8";
        String CRLF = "\r\n";
        
        System.out.println("sending output");
        
        // Send post request
        con.setDoOutput(true);
       try (
            OutputStream output = con.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
        ) {
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"filenames[]\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append("file.txt").append(CRLF).flush();


            writer.append("--" + boundary + "--").append(CRLF).flush();
        }


        
        System.out.println("Sending 'POST' request to URL : " + url);
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
    }
    
    private static void postInfo() throws IOException {
        URL url = new URL("https://neocities.org/api/upload");
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(("filetest:farscape").getBytes()));
        
        String boundary = Long.toHexString(System.currentTimeMillis());
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        File file = new File("file.txt");
        if(!file.exists()) {
            System.out.println("file doesn't exist");
            return;
        }
        
        String charset = "UTF-8";
        String param = "file.txt";
        String CRLF = "\r\n";
        
        System.out.println("sending output");
        
        // Send post request
        con.setDoOutput(true);
       try (
            OutputStream output = con.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
        ) {
            // Send text file.
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"file.txt\"; filename=\"" + file.getName() + "\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
            writer.append(CRLF).flush();
            Files.copy(file.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

            writer.append("--" + boundary + "--").append(CRLF).flush();
        }


        
        System.out.println("Sending 'POST' request to URL : " + url);
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
    }
    
    private static void getInfo() throws IOException {
        URL url = new URL("https://neocities.org/api/info");
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        String userpass = "filetest" + ":" + "farscape";
        con.setRequestProperty("Authorization", "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes()));
        System.out.println("Sending 'GET' request to URL : " + url);
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        
        if(responseCode == 403) {
            return;
        }
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }
    
}
