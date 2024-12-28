/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Icons;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author bayra
 */
public class UserIcon extends JFrame{
    
     public UserIcon() {
        setTitle("Kullanıcı İkonu");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Pencere simgesini ayarla
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Icons/UserIcon.png"));
        setIconImage(logoIcon.getImage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LogoAsIcon().setVisible(true);
        });
    }
    
}
