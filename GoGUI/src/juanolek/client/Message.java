package juanolek.client;

public class Message {

    private final String header;
    private final String value;

    public Message(String header, String value){
        this.header = header.toLowerCase();
        this.value = value;
    }

    public String getHeader(){
        return header;
    }
    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return header + '\t' + value;
    }
}
