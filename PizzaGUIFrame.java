import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {

    private JRadioButton ThinCrust, RegularCrust, DeepDishCrust;
    private JComboBox<String> SizeBox;
    private JCheckBox[] ToppingsCheckBoxes;
    private JTextArea RecieptArea;
    private JButton OrderButton, ClearButton, QuitButton;

    public PizzaGUIFrame() {

        //TITLE AND LAYOUT
        setTitle("Pizza Order Form");
        setLayout(new BorderLayout());

        //PANELS
        JPanel CrustPanel = createCrustPanel();
        JPanel SizePanel = createSizePanel();
        JPanel ToppingsPanel = createToppingsPanel();
        JPanel RecieptPanel = createRecieptPanel();
        JPanel ButtonPanel = createButtonPanel();

        add(CrustPanel, BorderLayout.WEST);
        add(SizePanel, BorderLayout.CENTER);
        add(ToppingsPanel, BorderLayout.EAST);
        add(RecieptPanel, BorderLayout.NORTH);
        add(ButtonPanel, BorderLayout.SOUTH);

        //FRAME SIZE
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    //CRUST
    private JPanel createCrustPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Crust"));

        ThinCrust = new JRadioButton("Thin Crust");
        RegularCrust = new JRadioButton("Regular Crust");
        DeepDishCrust = new JRadioButton("Deep Dish Crust");

        ButtonGroup CrustGroup = new ButtonGroup();
        CrustGroup.add(ThinCrust);
        CrustGroup.add(RegularCrust);
        CrustGroup.add(DeepDishCrust);

        panel.add(ThinCrust);
        panel.add(RegularCrust);
        panel.add(DeepDishCrust);
        return panel;
    }

    //SIZE
    private JPanel createSizePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Size"));

        String[] sizes = {"Small - $8.00", "Medium - $12.00", "Large - $16.00", "Super - $20.00"};
        SizeBox = new JComboBox<>(sizes);

        panel.add(SizeBox);
        return panel;
    }

    //TOPPINGS
    private JPanel createToppingsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Toppings ($1.00 each)"));

       String[] ToppingNames = {"Pepperoni", "Mushrooms", "Olives", "Pineapple", "Onion", "Sausage", "Bacon", "Extra Cheese", "Spinach", "No Cheese"};
       ToppingsCheckBoxes = new JCheckBox[ToppingNames.length];

       panel.setLayout(new GridLayout(ToppingNames.length, 1));
       for (int i = 0; i < ToppingNames.length; i++) {
           ToppingsCheckBoxes[i] = new JCheckBox(ToppingNames[i]);
           panel.add(ToppingsCheckBoxes[i]);
       }
       return panel;
    }
    //RECIEPT
    private JPanel createRecieptPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Reciept"));

        RecieptArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(RecieptArea);

        panel.add(scrollPane);
        return panel;
    }
    //BUTTONS
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        OrderButton = new JButton("Order");
        ClearButton = new JButton("Clear");
        QuitButton = new JButton("Quit");

        OrderButton.addActionListener(new OrderButtonListener());
        ClearButton.addActionListener(new ClearButtonListener());
        QuitButton.addActionListener(new QuitButtonListener());

        panel.add(OrderButton);
        panel.add(ClearButton);
        panel.add(QuitButton);

        return panel;
    }

    //ORDER BUTTON
    private class OrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String Crust = "";
            if (ThinCrust.isSelected()) Crust = "Thin Crust";
            else if (RegularCrust.isSelected()) Crust = "Regular Crust";
            else if (DeepDishCrust.isSelected()) Crust = "Deep Dish Crust";

            String Size = (String) SizeBox.getSelectedItem();
            double BasePrice = switch (SizeBox.getSelectedIndex()){
                case 0 -> 8.00;
                case 1 -> 12.00;
                case 2 -> 16.00;
                default -> 20.00;
            };
            StringBuilder Toppings = new StringBuilder();
            int ToppingCount = 0;
            for (JCheckBox ToppingCheckBox : ToppingsCheckBoxes) {
                if (ToppingCheckBox.isSelected()) {
                    Toppings.append(ToppingCheckBox.getText()).append("\n");
                    ToppingCount++;
        }
    }
            double SubTotal = BasePrice + ToppingCount;
            double Tax = SubTotal * 0.07;
            double Total = SubTotal + Tax;

            RecieptArea.setText(
                   "================================================\n" +
                   "Type of Crust: " + Crust + "\n" +
                   "Size: " + Size + "Price: $" + String.format("%.2f", BasePrice) + "\n" +
                   "Toppings:\n" + Toppings +
                   "Sub-Total: $" + String.format("%.2f", SubTotal) + "\n" +
                   "Tax: $" + String.format("%.2f", Tax) + "\n" +
                   "--------------------------------------------------\n" +
                   "Total: $" + String.format("%.2f", Total) + "\n" +
                   "==================================================="
            );
        }
    }
    //CLEAR BUTTON
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThinCrust.setSelected(false);
            RegularCrust.setSelected(false);
            DeepDishCrust.setSelected(false);
            SizeBox.setSelectedIndex(0);
            for (JCheckBox ToppingCheckBox : ToppingsCheckBoxes) { ToppingCheckBox.setSelected(false);
        }
            RecieptArea.setText("");
        }
    }
    //QUIT BUTTON
    private class QuitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(null,"Is this the End of Your Order?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
