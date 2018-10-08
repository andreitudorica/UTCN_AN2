import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Andrei on 17/01/2017.
 */
public class app {
    private JPanel panel1;
    private JButton button1;
    private JLabel lbl1;
    private JTextField txt1;

    public app()
    {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lbl1.setText(txt1.getText());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("frameu mostru");
        frame.setContentPane(new app().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
