
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JRootPane;

public class CurrencyConverterGUI {
    public static void main(String[] args)
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        int screenWidth = (int)(tk.getScreenSize().width)/3;
        int screenHeight = (int)tk.getScreenSize().height/3;
        CurrencyConverter Frame = new CurrencyConverter();
        Frame.setTitle("Currency Converter");
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setResizable(false);
        Frame.setSize(screenWidth, screenHeight);
        Frame.setLocationRelativeTo(null);
        Frame.getContentPane().setBackground(new Color(250,250,200));
        Frame.setLayout(new BorderLayout());
        Frame.setUndecorated(true);
        Frame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        Frame.setVisible(true);
    }
}
