package InvoiceTrackingSystem;

import CorePackage.Customer;
import CorePackage.DBConnection;
import CorePackage.Request;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CustomerRequestScreen extends javax.swing.JFrame {

    private Customer customer;

    public CustomerRequestScreen() {
        initComponents();

    }

    public CustomerRequestScreen(Customer customer) {
        this.customer = customer;
        initComponents();
        populateRequestTypeComboBox();
        populateAddressComboBox();
        populateSubscriptionTypeComboBox();
    }

    private void populateRequestTypeComboBox() {
        try ( Connection connection = DBConnection.getConnection()) {
            List<String> requestTypes = Request.getRequestTypes(connection);
            RequestTypeComboBox.removeAllItems();
            for (String type : requestTypes) {
                RequestTypeComboBox.addItem(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateAddressComboBox() {
        try ( Connection connection = DBConnection.getConnection()) {
            List<String> addresses = Request.getAddressesByUsername(connection, customer.getUsername());
            AddressComboBox.removeAllItems();
            if (addresses.isEmpty()) {
                System.out.println("No addresses found for username: " + customer.getUsername());
            } else {
                for (String address : addresses) {
                    System.out.println("Adding address: " + address);
                    AddressComboBox.addItem(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateSubscriptionTypeComboBox() {
        try ( Connection connection = DBConnection.getConnection()) {
            List<String> subscriptionTypes = Request.getSubscriptionTypes(connection);
            SubscriptionTypeComboBox.removeAllItems();
            for (String type : subscriptionTypes) {
                SubscriptionTypeComboBox.addItem(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        RequestTypeComboBox = new javax.swing.JComboBox<>();
        AddressComboBox = new javax.swing.JComboBox<>();
        SubscriptionTypeComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        MessageTextField = new javax.swing.JTextField();
        SendBttn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        RequestStatus = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        SearchBttn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        MenuButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(34, 40, 44));
        jPanel1.setPreferredSize(new java.awt.Dimension(965, 657));

        jLabel1.setFont(new java.awt.Font("SimSun-ExtG", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REQUESTS");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("SimSun-ExtG", 1, 18)); // NOI18N
        jLabel2.setText("New Request");

        jLabel3.setFont(new java.awt.Font("SimSun-ExtG", 1, 16)); // NOI18N
        jLabel3.setText("Request Type:");

        jLabel4.setFont(new java.awt.Font("SimSun-ExtG", 1, 16)); // NOI18N
        jLabel4.setText("Address:");

        jLabel5.setFont(new java.awt.Font("SimSun-ExtG", 1, 16)); // NOI18N
        jLabel5.setText("Subscription Type:");

        RequestTypeComboBox.setFont(new java.awt.Font("SimSun-ExtG", 0, 12)); // NOI18N
        RequestTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        AddressComboBox.setFont(new java.awt.Font("SimSun-ExtG", 0, 12)); // NOI18N
        AddressComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        SubscriptionTypeComboBox.setFont(new java.awt.Font("SimSun-ExtG", 0, 12)); // NOI18N
        SubscriptionTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("SimSun-ExtG", 1, 16)); // NOI18N
        jLabel6.setText("Message:");

        MessageTextField.setFont(new java.awt.Font("SimSun-ExtG", 0, 12)); // NOI18N

        SendBttn.setFont(new java.awt.Font("SimSun-ExtG", 0, 12)); // NOI18N
        SendBttn.setText("Send Request");
        SendBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendBttnActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Request_ID", "Subscription_Name", "Request_Type_Name", "Message", "Status", "Created_at", "Update_at", "Answer"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        RequestStatus.setFont(new java.awt.Font("SimSun-ExtG", 0, 12)); // NOI18N
        RequestStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Answered" }));

        jLabel7.setFont(new java.awt.Font("SimSun-ExtG", 1, 16)); // NOI18N
        jLabel7.setText("Request Status:");

        SearchBttn.setFont(new java.awt.Font("SimSun-ExtG", 0, 12)); // NOI18N
        SearchBttn.setText("Search");
        SearchBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBttnActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SimSun-ExtG", 1, 18)); // NOI18N
        jLabel8.setText("Requests");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(279, 279, 279))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(229, 229, 229))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(SubscriptionTypeComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AddressComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(RequestTypeComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(MessageTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addComponent(SendBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(RequestStatus, 0, 128, Short.MAX_VALUE)
                        .addGap(207, 207, 207)
                        .addComponent(SearchBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(RequestTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AddressComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SubscriptionTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(MessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SendBttn))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(RequestStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SearchBttn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        MenuButton1.setText("Menu");
        MenuButton1.setPreferredSize(new java.awt.Dimension(36, 36));
        MenuButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MenuButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(167, 167, 167))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(MenuButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuButton1ActionPerformed
        CustomerScreen customerScreen = new CustomerScreen(customer);
        customerScreen.setVisible(true);
        dispose();
    }//GEN-LAST:event_MenuButton1ActionPerformed

    private void SendBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendBttnActionPerformed
        String requestType = (String) RequestTypeComboBox.getSelectedItem();
        String addressName = (String) AddressComboBox.getSelectedItem();
        String subscriptionType = (String) SubscriptionTypeComboBox.getSelectedItem();
        String message = MessageTextField.getText();

        String username = customer.getUsername();
        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username is missing or invalid!");
            return;
        }

        if (addressName == null || addressName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a valid address!");
            return;
        }

        try {
            int nationalId = customer.getNationalIdByUsername(username);
            if (nationalId == -1) {
                JOptionPane.showMessageDialog(this, "National ID could not be found!");
                return;
            }

            if ("New Subscription".equals(requestType)) {
                boolean existingSubscription = Request.hasExistingSubscription(nationalId, addressName, subscriptionType);
                if (existingSubscription) {
                    JOptionPane.showMessageDialog(this, "This subscription already exists for the selected address!");
                    return;
                }
            } else if ("Others".equals(requestType)) {
                boolean existingSubscription = Request.hasExistingSubscription(nationalId, addressName, subscriptionType);
                if (!existingSubscription) {
                    JOptionPane.showMessageDialog(this, "You can only send 'Others' request for existing subscriptions!");
                    return;
                }
            }

            boolean success = Request.addRequest(nationalId, requestType, addressName, subscriptionType, message);
            if (success) {
                JOptionPane.showMessageDialog(this, "Request sent successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to send request.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while sending the request.");
            e.printStackTrace();
        }

    }

    /*

private void SearchBttnActionPerformed(java.awt.event.ActionEvent evt) {
    
    }//GEN-LAST:event_SendBttnActionPerformed

    */

    private void SearchBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBttnActionPerformed
        String status = (String) RequestStatus.getSelectedItem();
        if (status.equals("Answered")) {
            status = "Resolved";
        }

        try ( Connection connection = DBConnection.getConnection()) {
            String username = customer.getUsername();
            if (username == null || username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username is missing or invalid!");
                return;
            }

            int customerId = customer.getIdNumByUsername(connection, username);
            if (customerId == -1) {
                JOptionPane.showMessageDialog(this, "Customer ID could not be found!");
                return;
            }

            List<Request> requests = Request.getDetailedRequests(connection, String.valueOf(customerId), status);
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            for (Request request : requests) {
                model.addRow(new Object[]{
                    request.getRequestId(),
                    request.getSubscriptionType(),
                    request.getRequestType(),
                    request.getMessage(),
                    request.getStatus(),
                    request.getCreatedAt() != null ? request.getCreatedAt().toString() : "N/A",
                    request.getUpdatedAt() != null ? request.getUpdatedAt().toString() : "N/A",
                    request.getAnswer() != null ? request.getAnswer() : "N/A"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while fetching requests.");
        }
    }//GEN-LAST:event_SearchBttnActionPerformed

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
            java.util.logging.Logger.getLogger(CustomerRequestScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerRequestScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerRequestScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerRequestScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerRequestScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AddressComboBox;
    private javax.swing.JButton MenuButton1;
    private javax.swing.JTextField MessageTextField;
    private javax.swing.JComboBox<String> RequestStatus;
    private javax.swing.JComboBox<String> RequestTypeComboBox;
    private javax.swing.JButton SearchBttn;
    private javax.swing.JButton SendBttn;
    private javax.swing.JComboBox<String> SubscriptionTypeComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
