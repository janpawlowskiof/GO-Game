package juanolek;

import java.io.*;
import java.net.Socket;

public class Main {



    public static void main(String[] args) {
        System.out.println("Testing");
        Socket clientSocket = null;
        try {
            clientSocket = new Socket("localhost", 6666);

            BufferedReader consoleIn = new BufferedReader( new InputStreamReader(System.in) );
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            BufferedReader bufIn = new BufferedReader( new InputStreamReader( in ) );
            BufferedWriter bufOut = new BufferedWriter( new OutputStreamWriter( out ) );

            while(true){
                String line = consoleIn.readLine();
                if(line.equals("e"))
                    break;
                bufOut.write(line);
                bufOut.newLine();
                bufOut.flush();
            }

            //System.out.println(bufIn.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
