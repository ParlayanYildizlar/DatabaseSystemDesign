/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package InvoiceTrackingSystem;

import CorePackage.Admin;
import CorePackage.Announcement;
import CorePackage.City;
import CorePackage.Neighborhood;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author merye
 */
public class AdminAnnouncementsScreen extends javax.swing.JFrame {

    private Admin admin;

    public AdminAnnouncementsScreen() {
        initComponents();

    }

    public AdminAnnouncementsScreen(Admin admin) {
        this.admin = admin;
        initComponents();
        loadCities();
    }

    private void loadCities() {
        CityComboBox.removeAllItems();
        List<City> cities = City.getAllCities();
        for (City city : cities) {
            CityComboBox.addItem(city.getCityName());
        }
    }

    private void loadNeighborhoods() {
        NeighbourComboBox.removeAllItems();
        String selectedCity = (String) CityComboBox.getSelectedItem();
        if (selectedCity != null) {
            List<Neighborhood> neighborhoods = Neighborhood.getNeighborhoodsByCityName(selectedCity);
            for (Neighborhood neighborhood : neighborhoods) {
                NeighbourComboBox.addItem(neighborhood.getNeighborhoodName());
            }
        }
    }

    private int getSubscriptionTypeId(String typeName) {
        switch (typeName) {
            case "Water":
                return 1;
            case "Electricity":
                return 2;
            case "Natural Gas":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid subscription type: " + typeName);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AdminAnnouncementsPanel = new javax.swing.JPanel();
        CityComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        SendButton = new javax.swing.JButton();
        MenuButton = new javax.swing.JButton();
        NeighbourComboBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        SearchButton = new javax.swing.JButton();
        InterruptionIDTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        SearchByIDButton = new javax.swing.JButton();
        announcementMessageTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        AdminAnnouncementsPanel.setBackground(new java.awt.Color(34, 40, 44));
        AdminAnnouncementsPanel.setLayout(null);

        CityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CityComboBoxActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(CityComboBox);
        CityComboBox.setBounds(100, 170, 150, 30);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Interruption ID:");
        AdminAnnouncementsPanel.add(jLabel3);
        jLabel3.setBounds(570, 170, 110, 30);

        jLabel5.setFont(new java.awt.Font("SimSun-ExtG", 1, 48)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ANNOUNCEMENTS");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AdminAnnouncementsPanel.add(jLabel5);
        jLabel5.setBounds(255, 52, 427, 53);

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Interruption ID", "Subscription Type", "City Name", "Neighbourhood Name", "Start Time", "Reason"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(resultTable);

        AdminAnnouncementsPanel.add(jScrollPane2);
        jScrollPane2.setBounds(40, 252, 860, 330);

        SendButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SendButton.setText("Send");
        SendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(SendButton);
        SendButton.setBounds(790, 610, 116, 35);

        MenuButton.setText("Menu");
        MenuButton.setPreferredSize(new java.awt.Dimension(36, 36));
        MenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(MenuButton);
        MenuButton.setBounds(6, 7, 70, 36);

        AdminAnnouncementsPanel.add(NeighbourComboBox);
        NeighbourComboBox.setBounds(350, 170, 200, 30);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("City:");
        AdminAnnouncementsPanel.add(jLabel7);
        jLabel7.setBounds(60, 170, 40, 30);

        SearchButton.setText("Search");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(SearchButton);
        SearchButton.setBounds(430, 210, 120, 30);
        AdminAnnouncementsPanel.add(InterruptionIDTextField);
        InterruptionIDTextField.setBounds(680, 170, 210, 22);

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Announcement Message:");
        AdminAnnouncementsPanel.add(jLabel8);
        jLabel8.setBounds(40, 580, 160, 30);

        SearchByIDButton.setText("Search By ID");
        SearchByIDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchByIDButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(SearchByIDButton);
        SearchByIDButton.setBounds(770, 210, 120, 30);
        AdminAnnouncementsPanel.add(announcementMessageTextField);
        announcementMessageTextField.setBounds(40, 610, 740, 40);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Neighbour:");
        AdminAnnouncementsPanel.add(jLabel9);
        jLabel9.setBounds(270, 170, 80, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AdminAnnouncementsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 966, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AdminAnnouncementsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an interruption to send an announcement!");
            return;
        }

        String message = announcementMessageTextField.getText().trim();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an announcement message!");
            return;
        }

        try {
            int interruptionId = Integer.parseInt(resultTable.getValueAt(selectedRow, 0).toString());

            List<Integer> affectedCustomers = Announcement.getAffectedCustomers(interruptionId);

            if (affectedCustomers.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No customers are affected by this interruption!");
                return;
            }

            for (int customerId : affectedCustomers) {
                Announcement.createAnnouncement(customerId, interruptionId, message);
            }

            JOptionPane.showMessageDialog(this, "Announcement sent successfully!");
            announcementMessageTextField.setText("");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error sending announcement: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid interruption ID format!");
        }
    }//GEN-LAST:event_SendButtonActionPerformed

    private void MenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuButtonActionPerformed
        AdminScreen adminScreen = new AdminScreen(admin);
        adminScreen.setVisible(true);
        dispose();
    }//GEN-LAST:event_MenuButtonActionPerformed

    private void CityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CityComboBoxActionPerformed
        loadNeighborhoods();
    }//GEN-LAST:event_CityComboBoxActionPerformed

    private void SearchByIDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchByIDButtonActionPerformed
        String interruptionIdText = InterruptionIDTextField.getText().trim();
        if (interruptionIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Interruption ID.");
            return;
        }

        try {
            int interruptionId = Integer.parseInt(interruptionIdText);
            List<String[]> results = Announcement.searchInterruptByIDWithMessage(interruptionId);

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Interruption ID", "Subscription Type", "City Name", "Neighborhood Name", "Start Time", "End Time", "Announcement Content"},
                    0
            );
            resultTable.setModel(model);

            for (String[] row : results) {
                model.addRow(row);
            }

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No interruptions found with the given ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Interruption ID format.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching interruptions: " + ex.getMessage());
        }
    }//GEN-LAST:event_SearchByIDButtonActionPerformed

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        String subscriptionType = "All";
        String city = CityComboBox.getSelectedItem().toString();
        String neighborhood = NeighbourComboBox.getSelectedItem().toString();

        if (!"All".equalsIgnoreCase(subscriptionType)) {
            subscriptionType = String.valueOf(getSubscriptionTypeId(subscriptionType));
        }

        try {
            List<String[]> results = Announcement.searchInterruptsWithMessage(subscriptionType, city, neighborhood);

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Interruption ID", "Subscription Type", "City Name", "Neighborhood Name", "Start Time", "End Time", "Announcement Content"},
                    0
            );
            resultTable.setModel(model);

            for (String[] row : results) {
                model.addRow(row);
            }

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No interruptions found for the selected filters.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching interruptions: " + ex.getMessage());
        }
    }//GEN-LAST:event_SearchButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminAnnouncementsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminAnnouncementsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminAnnouncementsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminAnnouncementsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminAnnouncementsScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdminAnnouncementsPanel;
    private javax.swing.JComboBox<String> CityComboBox;
    private javax.swing.JTextField InterruptionIDTextField;
    private javax.swing.JButton MenuButton;
    private javax.swing.JComboBox<String> NeighbourComboBox;
    private javax.swing.JButton SearchButton;
    private javax.swing.JButton SearchByIDButton;
    private javax.swing.JButton SendButton;
    private javax.swing.JTextField announcementMessageTextField;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable resultTable;
    // End of variables declaration//GEN-END:variables
}
