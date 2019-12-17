package juanolek.gui.awt;

import juanolek.client.Message;
import juanolek.gui.GuiManager;

import javax.swing.*;
import java.awt.*;

public class AwtLogin extends ReceiverFrame {

    private final TextField host_name_tf;
    private final TextField gate_tf;

    public AwtLogin(GuiManager guiManager) {

        setSize(400,400);
        setBackground(Color.ORANGE);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new FlowLayout());
        setLayout(null);

        Label Title = new Label("Welcome Player", Label.CENTER);
        Label host_name = new Label("Host Name", Label.CENTER);
        Label port = new Label("Port", Label.CENTER);

        Title.setFont(new Font("TimesRoman",Font.PLAIN,16));
        host_name_tf = new TextField(30);
        host_name_tf.setText("localhost");
        gate_tf = new TextField(20);
        gate_tf.setText("6666");

        Button confirm = new Button("Log In");
        confirm.addActionListener(e -> {
            try{
                int port1 = Integer.parseInt(gate_tf.getText());
                guiManager.connect(host_name_tf.getText(), port1);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Incorrect port!");
            }
        });

        add(Title);
        add(host_name);
        add(host_name_tf);
        add(port);
        add(gate_tf);
        add(confirm);

        Title.setBounds(140,50,120,40);
        host_name.setBounds(90, 90, 90, 60);
        port.setBounds(90, 130, 90, 60);
        host_name_tf.setBounds(190, 110, 90, 20);
        gate_tf.setBounds(190, 150, 90, 20);
        confirm.setBounds(150, 220, 70, 40);

    }

    @Override
    public void receive(Message message) {
        System.out.println("Received useless message");
    }
}