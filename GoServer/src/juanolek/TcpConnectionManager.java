package juanolek;

import java.io.*;
import java.net.Socket;

public class TcpConnectionManager implements IConnectionManager {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private TcpListener tcpListener = null;

    public TcpConnectionManager(Socket socket){
        this.socket = socket;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TcpListener extends Thread{

        public boolean exitFlag = false;
        InputStream inputStream;
        IMessageReceiver receiver;

        public TcpListener(InputStream inputStream, IMessageReceiver receiver){
            this.inputStream = inputStream;
            this.receiver = receiver;
        }

        @Override
        public void start(){

            BufferedReader inBuf = new BufferedReader( new InputStreamReader(inputStream) );
            while(!exitFlag){
                try {
                    String line = inBuf.readLine();
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
        String value = "tesst";
        try {
            outputStream.write(value.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startListening(IMessageReceiver receiver) {
        tcpListener = new TcpListener(inputStream, receiver);
        tcpListener.start();
    }
}
