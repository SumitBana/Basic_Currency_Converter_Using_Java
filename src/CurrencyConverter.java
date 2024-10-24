
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CurrencyConverter extends JFrame{

    private JComboBox cmbbasecurr,cmbtargetcurr;
    private JTextField txtbase, txttarget;
    private JButton btnConvert,btnReset;
    private String[] basecurr;
    private String[] targetcurr;
    private String basecode,targetcode;
    String apiKey = "320a9bafa7b64c5e9a94f4924cd1b847";
    
    public JLabel makeLabel(String s,int x,int y,int w,int h,int mode)
    {
        JLabel temp = new JLabel(s);
        temp.setBounds( x, y, w, h);
	if(mode == 1)
	{
	    Border br1 = BorderFactory.createLineBorder(Color.RED, 2);
	    Border br2 = BorderFactory.createLineBorder(Color.WHITE, 2);
	    Border br3 = BorderFactory.createCompoundBorder(br1, br2);
	    temp.setOpaque( true);
	    temp.setBackground(Color.BLUE);
	    temp.setForeground(Color.WHITE);
	    temp.setBorder(br3);
	    temp.setFont(new Font("Segoe UI",1,30));
	    temp.setHorizontalAlignment(JLabel.CENTER);
	}
	else if(mode == 2)
	{
	    temp.setFont(new Font("Courier New",1,18));
	}
	else if(mode == 3)
	{
            temp.setForeground(Color.red);
	    temp.setFont(new Font("Courier New",3,15));
	}
        add(temp);
        return(temp);
    }
    public JTextField makeTextField(int x,int y,int w,int h)
    {
        JTextField temp = null;
        temp = new JTextField();
        temp.setBounds( x, y, w, h);
	temp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        temp.setFont(new Font("Courier New",1,18));
      temp.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
            if (!Character.isDigit(c)&&c!='.') {
                e.consume(); // Ignore non-digit characters
            }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(txtbase.getText().length()==0) {
                btnConvert.setEnabled(false); // Disable if no text
            } else {
                btnConvert.setEnabled(true); // Enable if text is present
            }
            }
         
        
    });
        add(temp);
        return(temp);
    }
    private JComboBox makeComboBox(String s[],int x,int y,int w,int h)
    {
        JComboBox temp = new JComboBox(s);
        temp.setBounds(x, y, w, h);
        temp.setFont(new Font("Consolas",1,15));
        temp.setForeground(Color.BLACK);
        temp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(temp);
        return temp;
    }
    
    private void convert()
    {
        try
        {
            
            String apiUrl = "https://openexchangerates.org/api/latest.json?app_id=" + apiKey + "&base="+basecode;
            URL url = new URL(apiUrl);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            int responseCode = connect.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine())!= null) {
                    response.append(inputLine);
                }
                in.close();

                // Manual JSON Parsing for the target currency rate
                String jsonString = response.toString();
                String ratesStart = "\"rates\":{";
                int startIndex = jsonString.indexOf(ratesStart) + ratesStart.length();
                String ratesString = jsonString.substring(startIndex);
                String[] currencies = ratesString.split(",");
                double exchangeRate = 0.0;
                for (String currency : currencies) {
                currency = currency.trim(); // Add this to remove leading/trailing whitespace
                if (currency.startsWith("\"" + targetcode + "\"")) {
                    String[] parts = currency.split(":");
                    if (parts.length > 1) {
                        String rateString = parts[1].trim();
                        rateString = rateString.replaceAll("[^\\d\\.]", ""); // Remove non-digit, non-dot characters
                        exchangeRate = Double.parseDouble(rateString);
                        break;
                    }
                }
                }
            
                double convertedAmount = Double.parseDouble(txtbase.getText()) * exchangeRate;
                BigDecimal bd = BigDecimal.valueOf(convertedAmount);
                bd = bd.setScale(3, RoundingMode.HALF_UP);
                convertedAmount = bd.doubleValue();
                txttarget.setText(String.valueOf(convertedAmount));
                txttarget.setHorizontalAlignment(JTextField.CENTER);
                connect.disconnect();

        }
        }
        catch(Exception ex)
        {
            System.out.println("Failed 1. HTTP Error Code: " + ex);
            JOptionPane.showMessageDialog(null,"Failed 1. HTTP Error Code: " + ex,"Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    
    public JButton makeButton (String s,int x,int y,int w,int h)
    {
        JButton temp = new JButton(s);
        temp.setBounds( x, y, w, h);
        temp.setOpaque(true);
        temp.setFont(new Font("Courier New",1,16));
	temp.setMargin(new Insets(0,0,0,0));
	temp.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e)
	    {
		Object ob = e.getSource();
                if(ob==btnConvert)
            {
                String baseSelectedItem = (String)cmbbasecurr.getSelectedItem();
                String targetSelectedItem = (String)cmbtargetcurr.getSelectedItem();

                if(baseSelectedItem!= null && targetSelectedItem!= null)
                {
                    int baseStartIndex = baseSelectedItem.indexOf('(');
                    int baseEndIndex = baseSelectedItem.indexOf(')');
                    int targetStartIndex = targetSelectedItem.indexOf('(');
                    int targetEndIndex = targetSelectedItem.indexOf(')');

                    if(baseStartIndex!= -1 && baseEndIndex!= -1 && targetStartIndex!= -1 && targetEndIndex!= -1)
                    {
                        basecode = baseSelectedItem.substring(baseStartIndex + 1, baseEndIndex);
                        targetcode = targetSelectedItem.substring(targetStartIndex + 1, targetEndIndex);
                    }
                }
                convert();
                
            }
            
                if(ob==btnReset)
                {
                    txtbase.setText("");
                    txttarget.setText("");
                    cmbtargetcurr.setSelectedIndex(0);
                }
            
            }
	});
        
        
        add(temp);
        return(temp);
    }
    
    public void currlist()
    {
        try{
            
        String apiUrl = "https://openexchangerates.org/api/currencies.json?app_id=" + apiKey;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine())!= null) {
                response.append(inputLine);
            }
            in.close();

            // Assuming the response is in the format: {"<currency_code>": "<currency_name>",...}
            String jsonString = response.toString();
            jsonString = jsonString.replace("{", "").replace("}", ""); // Simplify to key-value pairs
            String[] currencyPairs = jsonString.split(",");
            
            int count=0;
            int count2=0;
            ArrayList<String> currencyList = new ArrayList<>(); // To store the formatted country currency strings
            for (String pair : currencyPairs) {
                if (!pair.isEmpty()) { 
                    String[] parts = pair.split(":");
                    if (parts.length > 1) {
                        String code = parts[0].trim().replaceAll("\"", ""); // Currency Code
                        if(code.equals("USD"))count2=count;
                        String name = parts[1].trim().replaceAll("\"", ""); // Currency Name
                        String formattedString = name + " (" + code + ")";
                        currencyList.add(formattedString);
                    }
                }
                count++;
            }

            basecurr = currencyList.toArray(new String[0]);
            String temp = basecurr[0];
            basecurr[0] = basecurr[count2];
            basecurr[count2]=temp;
            
            targetcurr = basecurr.clone();
        } 
        else 
        {
            connection.disconnect();
            throw new Exception(String.valueOf(responseCode));
        }
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(null,"Failed 2. HTTP Error Code: " + ex,"Error",JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
    }
    
    
    public CurrencyConverter()
    {
           currlist();
           makeLabel("Currency Converter",10,10,610,60,1 );
           makeLabel("*DUE TO FREE PLAN OF API KEY, BASE CURRENCY IS USD(DOLLARS $)*",40,80,600,40,3);
           cmbbasecurr = makeComboBox(basecurr,320,135,280,40);
           cmbbasecurr.disable();
           cmbtargetcurr = makeComboBox(targetcurr,320,190,280,40);
           txtbase = makeTextField(50,135,250,40);
           txtbase.setHorizontalAlignment(JTextField.CENTER);
           txttarget = makeTextField(50,190,250,40);
           txttarget.setEditable(false);
           btnConvert = makeButton("Convert", 50,250,260,40);
           btnConvert.setEnabled(false); 
           btnReset = makeButton("Reset",330,250,260,40);
    }
}


