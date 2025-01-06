package CorePackage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bill {

    private int invoiceId;
    private String subscriptionTypeName;
    private Date issueDate;
    private Date dueDate;
    private double totalAmount;
    private String payStatus;

    public Bill() {
    }

    public Bill(int invoiceId, String subscriptionTypeName, Date issueDate, Date dueDate, double totalAmount, String payStatus) {
        this.invoiceId = invoiceId;
        this.subscriptionTypeName = subscriptionTypeName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.payStatus = payStatus;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getSubscriptionTypeName() {
        return subscriptionTypeName;
    }

    public void setSubscriptionTypeName(String subscriptionTypeName) {
        this.subscriptionTypeName = subscriptionTypeName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public static List<Bill> getFilteredInvoices(String payStatus, String subscriptionType) {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT i.invoice_id, st.subscription_type_name, i.issue_date, i.due_date, "
                + "i.total_amount, i.payment_status FROM dbo.Invoice i "
                + "JOIN dbo.Subscription_Type st ON i.subscription_id = st.subscription_type_id "
                + "WHERE (i.payment_status = ? OR ? = 'Choose Status') "
                + "AND (st.subscription_type_name = ? OR ? = 'Choose Subscription')";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, payStatus);
            stmt.setString(2, payStatus);
            stmt.setString(3, subscriptionType);
            stmt.setString(4, subscriptionType);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bills.add(new Bill(
                        rs.getInt("invoice_id"),
                        rs.getString("subscription_type_name"),
                        rs.getDate("issue_date"),
                        rs.getDate("due_date"),
                        rs.getDouble("total_amount"),
                        rs.getString("payment_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public static List<Bill> fetchInvoicesForCustomer(int customerId) {
        List<Bill> bills = new ArrayList<>();
        String query = """
            SELECT 
                i.invoice_id, 
                st.subscription_type_name, 
                i.issue_date, 
                i.due_date, 
                i.total_amount, 
                i.payment_status
            FROM 
                dbo.Invoice i
            JOIN 
                dbo.Subscription s ON i.subscription_id = s.subscription_id
            JOIN 
                dbo.Subscriber sub ON s.subscriber_id = sub.subscriber_id
            JOIN 
                dbo.Subscription_Type st ON s.subscription_type_id = st.subscription_type_id
            JOIN 
                dbo.Customer c ON sub.National_id = c.idnum
            WHERE 
                c.idnum = ?;
            """;

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Bill bill = new Bill(
                            rs.getInt("invoice_id"),
                            rs.getString("subscription_type_name"),
                            rs.getDate("issue_date"),
                            rs.getDate("due_date"),
                            rs.getDouble("total_amount"),
                            rs.getString("payment_status")
                    );
                    bills.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public static void testFetchInvoicesForCustomer(int customerId) {
        List<Bill> invoices = fetchInvoicesForCustomer(customerId);
        if (invoices.isEmpty()) {
            System.out.println("No invoices found for customer ID: " + customerId);
        } else {
            System.out.println("Invoices for customer ID: " + customerId);
            printInvoices(invoices);
        }
    }

    public static boolean updatePaymentStatus(int invoiceId, String newStatus) {
        String query = "UPDATE dbo.Invoice SET payment_status = ? WHERE invoice_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, invoiceId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean payInvoice(int invoiceId) {
        return updatePaymentStatus(invoiceId, "Paid");
    }

    public static void printInvoices(List<Bill> bills) {
        for (Bill bill : bills) {
            System.out.println("Invoice ID: " + bill.getInvoiceId()
                    + ", Subscription Type: " + bill.getSubscriptionTypeName()
                    + ", Issue Date: " + bill.getIssueDate()
                    + ", Due Date: " + bill.getDueDate()
                    + ", Total Amount: " + bill.getTotalAmount()
                    + ", Pay Status: " + bill.getPayStatus());
        }
    }

}
