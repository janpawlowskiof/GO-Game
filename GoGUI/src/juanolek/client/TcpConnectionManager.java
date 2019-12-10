package juanolek.client;

import java.io.*;
import java.net.Socket;

public class TcpConnectionManager implements IConnectionManager {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private TcpListener tcpListener = null;

    public TcpConnectionManager(Socket socket){
        this.socket = socket;
        try {
            bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream()) );
            bufferedWriter = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream()) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TcpListener extends Thread{

        public boolean exitFlag = false;
        IMessageReceiver receiver;

        public TcpListener(IMessageReceiver receiver){
            this.receiver = receiver;
        }
        @Override
        public void run(){
            while(!exitFlag){
                try {
                    String line = bufferedReader.readLine();
                    if(line == null)
                        return;
                    int separatorIndex = line.indexOf('\t');

                    Message message;
                    if(separatorIndex < 0 || separatorIndex >= line.length())
                        message = new Message(line, "");
                    else
                        message = new Message(line.substring(0, separatorIndex), line.substring(separatorIndex + 1, line.length()));

                    receiver.receive(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void disconnect() throws IOException {
        socket.close();
    }

    @Override
    public void sendMessage(Message message) {
        System.out.println("Got here!");
        try {
            bufferedWriter.write(message.getHeader() + '\t' + message.getValue());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startListening(IMessageReceiver receiver) {
        tcpListener = new TcpListener(receiver);
        tcpListener.start();
    }
}
