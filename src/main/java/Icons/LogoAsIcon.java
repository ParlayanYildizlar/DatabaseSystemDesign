package Icons;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author sare
 */
public class LogoAsIcon extends JFrame{
    
      public LogoAsIcon() {
        setTitle("Pencere İkonu");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Pencere simgesini ayarla
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
        setIconImage(logoIcon.getImage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LogoAsIcon().setVisible(true);
        });
    }
    
}
