package juanolek.gui.awt;

import juanolek.client.IMessageReceiver;
import juanolek.client.Message;
import juanolek.gui.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AwtLogin extends ReceiverFrame {

    TextField host_name_tf,gate_tf;
    Button confirm;
    GuiManager guiManager;

    public AwtLogin(GuiManager guiManager) {
        this.guiManager = guiManager;

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
        Label gate = new Label("Gate", Label.CENTER);

        Title.setFont(new Font("TimesRoman",Font.PLAIN,16));
        host_name_tf = new TextField(20);
        gate_tf = new TextField(20);
        confirm = new Button("Log In");
        confirm.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                guiManager.connect("localhost", 6666);
            }
        });

        add(Title);
        add(host_name);
        add(host_name_tf);
        add(gate);
        add(gate_tf);
        add(confirm);

        Title.setBounds(140,50,120,40);
        host_name.setBounds(90, 90, 90, 60);
        gate.setBounds(90, 130, 90, 60);
        host_name_tf.setBounds(190, 110, 90, 20);
        gate_tf.setBounds(190, 150, 90, 20);
        confirm.setBounds(150, 220, 70, 40);

    }

    @Override
    public void receive(Message message) {
        System.out.println("Received useless message");
    }
}