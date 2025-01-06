/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package InvoiceTrackingSystem;

import CorePackage.Admin;
import CorePackage.City;
import CorePackage.Interruption;
import CorePackage.Neighborhood;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bayra
 */
public class AdminInterruptionScreen extends javax.swing.JFrame {

    private Admin admin;
    private int customerNationalId;

    public AdminInterruptionScreen() {
        initComponents();
        loadCities();

    }

    public AdminInterruptionScreen(Admin admin) {
        this.admin = admin;
        initComponents();
        loadCities();

    }

    public AdminInterruptionScreen(Admin admin, int customerNationalId) {
        this.admin = admin;
        this.customerNationalId = customerNationalId;
        initComponents();
        loadCustomerNationalId();
        loadCities();
    }

    private void loadCustomerNationalId() {
        if (customerNationalId != -1) {
            userIDTextField.setText(String.valueOf(customerNationalId));
        }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        AdminAnnouncementsPanel = new javax.swing.JPanel();
        NeighbourComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        CityComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        userIDTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        makeInterruptButton = new javax.swing.JButton();
        removeInterruptButton = new javax.swing.JButton();
        searchInterruptsButton = new javax.swing.JButton();
        reasonTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        searchSubscribersButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        subscriptionTypeComboBox = new javax.swing.JComboBox<>();
        SearchByIDButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        AdminAnnouncementsPanel.setBackground(new java.awt.Color(34, 40, 44));
        AdminAnnouncementsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AdminAnnouncementsPanel.add(NeighbourComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(747, 168, 182, -1));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Subscription Type:");
        AdminAnnouncementsPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, 110, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("*Please choose which service will be interrupted?");
        AdminAnnouncementsPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(684, 115, -1, -1));

        jLabel5.setFont(new java.awt.Font("SimSun", 1, 48)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("INTERRUPTION");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AdminAnnouncementsPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 38, 547, -1));

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "National ID", "First Name", "Last Name", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(resultTable);

        AdminAnnouncementsPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 239, 890, 340));

        CityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CityComboBoxActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(CityComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 168, 182, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("City:");
        AdminAnnouncementsPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(406, 173, -1, -1));
        AdminAnnouncementsPanel.add(userIDTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 168, 256, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Customer ID:");
        AdminAnnouncementsPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 173, -1, -1));

        makeInterruptButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        makeInterruptButton.setText("Make Interrupt");
        makeInterruptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeInterruptButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(makeInterruptButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(576, 590, 170, 40));

        removeInterruptButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        removeInterruptButton.setText("Remove Interrupt");
        removeInterruptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeInterruptButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(removeInterruptButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(756, 590, 170, 40));

        searchInterruptsButton.setText("Search Interrupts");
        searchInterruptsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchInterruptsButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(searchInterruptsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 210, 160, -1));
        AdminAnnouncementsPanel.add(reasonTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 530, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Reason:");
        AdminAnnouncementsPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, -1, -1));

        searchSubscribersButton.setText("Search Subscribers");
        searchSubscribersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchSubscribersButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(searchSubscribersButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 210, 150, -1));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Neighbourhood:");
        AdminAnnouncementsPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(654, 173, -1, -1));

        subscriptionTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Water", "Electricity", "Natural Gas" }));
        subscriptionTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subscriptionTypeComboBoxActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(subscriptionTypeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 140, 160, -1));

        SearchByIDButton.setText("Search by ID");
        SearchByIDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchByIDButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(SearchByIDButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 200, 110, -1));

        menuButton.setText("Menu");
        menuButton.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 204, 255), null));
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });
        AdminAnnouncementsPanel.add(menuButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 60, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AdminAnnouncementsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AdminAnnouncementsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void makeInterruptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeInterruptButtonActionPerformed
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a subscriber or neighborhood to create an interruption.");
            return;
        }

        String reason = reasonTextField.getText().trim();
        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide a reason for the interruption.");
            return;
        }

        try {
            String subscriptionTypeName = resultTable.getValueAt(selectedRow, 1).toString();
            int subscriptionTypeId = Interruption.getSubscriptionTypeIdByName(subscriptionTypeName);

            String neighborhood = resultTable.getValueAt(selectedRow, 5).toString();
            int neighborhoodId = Interruption.getNeighborhoodId(neighborhood);

            Timestamp startTime = new Timestamp(System.currentTimeMillis());

            Interruption.createInterruption(subscriptionTypeId, neighborhoodId, reason, startTime);

            Interruption.updateInterruptionStatusByNeighborhood(neighborhoodId, 1);

            JOptionPane.showMessageDialog(this, "Interruption created successfully!");
            reasonTextField.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating interruption: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Subscription Type ID format.");
        }
    }//GEN-LAST:event_makeInterruptButtonActionPerformed

    private void removeInterruptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeInterruptButtonActionPerformed
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an interruption to remove.");
            return;
        }

        try {
            int interruptionId = Integer.parseInt(resultTable.getValueAt(selectedRow, 0).toString());
            String neighborhood = resultTable.getValueAt(selectedRow, 3).toString();

            if (neighborhood == null || neighborhood.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Neighborhood is missing!");
                return;
            }

            int neighborhoodId = Interruption.getNeighborhoodId(neighborhood);

            Interruption.removeInterruption(interruptionId, neighborhoodId);

            searchInterruptsButton.doClick();

            JOptionPane.showMessageDialog(this, "Interruption removed successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing interruption: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid interruption ID format.");
        }
    }//GEN-LAST:event_removeInterruptButtonActionPerformed

    private void searchInterruptsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchInterruptsButtonActionPerformed
        String subscriptionType = subscriptionTypeComboBox.getSelectedItem().toString();
        String city = CityComboBox.getSelectedItem().toString();
        String neighborhood = NeighbourComboBox.getSelectedItem().toString();

        if (!"All".equalsIgnoreCase(subscriptionType)) {
            subscriptionType = String.valueOf(getSubscriptionTypeId(subscriptionType));
        }

        try {
            List<String[]> results = Interruption.searchInterrupts(subscriptionType, city, neighborhood);

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Interruption ID", "Subscription Type", "City Name", "Neighborhood Name", "Start Time", "End Time", "Reason"},
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
    }//GEN-LAST:event_searchInterruptsButtonActionPerformed

    private void searchSubscribersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchSubscribersButtonActionPerformed
        String subscriptionType = subscriptionTypeComboBox.getSelectedItem().toString();
        String city = CityComboBox.getSelectedItem().toString();
        String neighborhood = NeighbourComboBox.getSelectedItem().toString();

        if (!"All".equalsIgnoreCase(subscriptionType)) {
            subscriptionType = String.valueOf(getSubscriptionTypeId(subscriptionType));
        }

        try {
            List<String[]> results = Interruption.searchSubscribers(subscriptionType, city, neighborhood);

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"National ID", "Subscription Type", "First Name", "Last Name", "City", "Neighborhood", "Address", "Interruption Status"},
                    0
            );
            resultTable.setModel(model);

            for (String[] row : results) {
                model.addRow(row);
            }

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No subscribers found for the selected filters.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching subscribers: " + ex.getMessage());
        }
    }//GEN-LAST:event_searchSubscribersButtonActionPerformed

    private void CityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CityComboBoxActionPerformed
        loadNeighborhoods();
    }//GEN-LAST:event_CityComboBoxActionPerformed

    private void SearchByIDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchByIDButtonActionPerformed
        String customerIdText = userIDTextField.getText().trim();
        if (customerIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Customer ID.");
            return;
        }

        try {
            int customerId = Integer.parseInt(customerIdText);
            List<String[]> results = Interruption.searchSubscriberByID(customerId);

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"National ID", "Subscription Type", "First Name", "Last Name", "City", "Neighborhood", "Address", "Interruption Status"},
                    0
            );
            resultTable.setModel(model);

            for (String[] row : results) {
                model.addRow(row);
            }

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No subscribers found with the given ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Customer ID format.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching subscriber: " + ex.getMessage());
        }
    }//GEN-LAST:event_SearchByIDButtonActionPerformed

    private void subscriptionTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subscriptionTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subscriptionTypeComboBoxActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        AdminScreen adminScreen = new AdminScreen(admin);
        adminScreen.setVisible(true);
        dispose();
    }//GEN-LAST:event_menuButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AdminInterruptionScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminInterruptionScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminInterruptionScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminInterruptionScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminInterruptionScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdminAnnouncementsPanel;
    private javax.swing.JComboBox<String> CityComboBox;
    private javax.swing.JComboBox<String> NeighbourComboBox;
    private javax.swing.JButton SearchByIDButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton makeInterruptButton;
    private javax.swing.JButton menuButton;
    private javax.swing.JTextField reasonTextField;
    private javax.swing.JButton removeInterruptButton;
    public static javax.swing.JTable resultTable;
    private javax.swing.JButton searchInterruptsButton;
    private javax.swing.JButton searchSubscribersButton;
    private javax.swing.JComboBox<String> subscriptionTypeComboBox;
    private javax.swing.JTextField userIDTextField;
    // End of variables declaration//GEN-END:variables
}
