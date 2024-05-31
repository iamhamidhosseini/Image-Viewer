import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.awt.image.RescaleOp;

public class ImageViewerGUI extends JFrame implements ActionListener{
    JButton selectFileButton;
    JButton showImageButton;
    JButton resizeButton;
    JButton grayscaleButton;
    JButton brightnessButton;
    JButton closeButton;
    JButton showResizeButton;
    JButton showBrightnessButton;
    JButton backButton;
    JTextField widthTextField;
    JTextField heightTextField;
    JTextField brightnessTextField;
    JLabel intro = new JLabel("Welcome to Hamid's Image Viewer");
    String filePath = "Photos";
    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);
    int h = 900;
    int w = 1200;
    float brightenFactor = 1;
    int response;
    Font font = new Font("Fixedsys Regular",Font.BOLD,20);
    BufferedImage bufferedImage ;
    BufferedImage tempBufferedImage;
    ImageIcon imageIcon;

    ImageViewerGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 600);
        this.setVisible(true);
        this.setResizable(true);

        mainPanel();
    }

    public void mainPanel(){
        // Create main panel for adding to Frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        intro.setBounds(180,50,500,50);intro.setFont(font);


        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));
        buttonsPanel.setBounds(140,200,400,200);

        selectFileButton = new JButton("Select File");selectFileButton.setFocusable(false);
        selectFileButton.addActionListener(this);selectFileButton.setFont(font);
        brightnessButton = new JButton("Brightness");brightnessButton.setFocusable(false);
        brightnessButton.addActionListener(this);brightnessButton.setFont(font);
        resizeButton = new JButton("Set Size");resizeButton.setFocusable(false);
        resizeButton.addActionListener(this);resizeButton.setFont(font);
        grayscaleButton = new JButton("Black and White");grayscaleButton.setFocusable(false);
        grayscaleButton.addActionListener(this);grayscaleButton.setFont(font);
        showImageButton = new JButton("Standard Image");showImageButton.setFocusable(false);
        showImageButton.addActionListener(this);showImageButton.setFont(font);
        closeButton = new JButton("Close");closeButton.setFocusable(false);
        closeButton.addActionListener(this);closeButton.setFont(font);

        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);
        mainPanel.add(this.intro);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);

        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel(){
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);
        resizePanel.setSize(700,600);

        // labels
        JLabel Width = new JLabel("Width:");Width.setFont(font);Width.setBounds(150,180,100,30);
        JLabel Height = new JLabel("Height:");Height.setFont(font);Height.setBounds(150,252,100,30);
        JLabel text = new JLabel("Resize Section");text.setFont(font);text.setBounds(305,100,200,50);
        resizePanel.add(Width);resizePanel.add(Height);resizePanel.add(text);

        // text fields
        widthTextField = new JTextField();
        widthTextField.setFont(font);widthTextField.setBounds(270,180,200,40);
        heightTextField = new JTextField();
        heightTextField.setFont(font);heightTextField.setBounds(270,250,200,40);
        resizePanel.add(widthTextField);resizePanel.add(heightTextField);

        // buttons
        backButton = new JButton("Back");backButton.setFocusable(false);
        backButton.setFont(font);backButton.setBounds(30,420,200,40);
        backButton.addActionListener(this);
        showResizeButton = new JButton("Show");showResizeButton.setFocusable(false);
        showResizeButton.setFont(font);showResizeButton.setBounds(450,420,200,40);
        showResizeButton.addActionListener(this);
        resizePanel.add(showResizeButton);resizePanel.add(backButton);

        this.getContentPane().removeAll();
        this.add(resizePanel);
        this.revalidate();
        this.repaint();
    }
    public void brightnessPanel(){
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(null);
        brightnessPanel.setSize(700,600);

        JLabel jLabel = new JLabel("Please enter a number between 0 and 1 : ");
        jLabel.setBounds(10,200,400,40);jLabel.setFont(font);
        brightnessTextField = new JTextField();brightnessTextField.setFont(font);
        brightnessTextField.setBounds(450,175,80,80);
        brightnessPanel.add(brightnessTextField);
        brightnessPanel.add(jLabel);

        // set buttons
        showBrightnessButton = new JButton("Show");showBrightnessButton.setFocusable(false);
        showBrightnessButton.setFont(font);showBrightnessButton.setBounds(450,400,200,40);
        brightnessPanel.add(showBrightnessButton);showBrightnessButton.addActionListener(this);

        backButton = new JButton("Back");backButton.setFocusable(false);
        backButton.setFont(font);backButton.setBounds(30,400,200,40);
        brightnessPanel.add(backButton);backButton.addActionListener(this);

        this.getContentPane().removeAll();
        this.add(brightnessPanel);
        this.revalidate();
        this.repaint();
    }

    public void chooseFileImage(){
        try {
            response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION) {
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                bufferedImage = ImageIO.read(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void showOriginalImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        imageIcon = new ImageIcon(bufferedImage);
        tempPanel.setLayout(new FlowLayout());
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        tempPanel.add(jLabel);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void grayScaleImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        //set a temp buffer to make image gray
        try {
            tempBufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // get image's width and height
        int width = tempBufferedImage.getWidth();
        int height = tempBufferedImage.getHeight();
        int[] pixels = tempBufferedImage.getRGB(0, 0, width, height, null, 0, width);
        // convert to grayscale
        for (int i = 0; i < pixels.length; i++) {

            // Here i denotes the index of array of pixels
            // for modifying the pixel value.
            int p = pixels[i];

            int a = (p >> 24) & 0xff;
            int r = (p >> 16) & 0xff;
            int g = (p >> 8) & 0xff;
            int b = p & 0xff;

            // calculate average
            int avg = (r + g + b) / 3;

            // replace RGB value with avg
            p = (a << 24) | (avg << 16) | (avg << 8) | avg;

            pixels[i] = p;
        }
        tempBufferedImage.setRGB(0, 0, width, height, pixels, 0, width);
        imageIcon = new ImageIcon(tempBufferedImage);
        JLabel jLabel = new JLabel(imageIcon);
        tempPanel.add(jLabel);


        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void showResizeImage(int w, int h){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();


        Image newImage = bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT);
        imageIcon = new ImageIcon(newImage);
        JLabel jLabel = new JLabel(imageIcon);
        tempPanel.add(jLabel);


        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void showBrightnessImage(float f){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        int r=0, g=0, b=0, rgb=0, p=0;
        int amount = (int)(f * 255); // rgb scale is 0-255, so 255 is 100%
        BufferedImage newImage = new BufferedImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y=0; y<bufferedImage.getHeight(); y+=1) {
            for (int x=0; x<bufferedImage.getWidth(); x+=1) {
                rgb = bufferedImage.getRGB(x, y);
                r = ((rgb >> 16) & 0xFF) + amount;
                g = ((rgb >> 8) & 0xFF) + amount;
                b = (rgb & 0xFF) + amount;
                if (r>255) r=255;
                if (g>255) g=255;
                if (b>255) b=255;
                p = (255<<24) | (r<<16) | (g<<8) | b;
                newImage.setRGB(x,y,p);
            }
        }
        ImageIcon imageIcon2 = new ImageIcon(newImage);
        JLabel jLabel = new JLabel(imageIcon2);
        tempPanel.add(jLabel);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==resizeButton){
            resizePanel();
        }
        else if(e.getSource()== showImageButton){
            showOriginalImage();
        }
        else if(e.getSource()==grayscaleButton){
            grayScaleImage();

        }else if(e.getSource()== showResizeButton){
            int x = Integer.parseInt(widthTextField.getText());
            int y = Integer.parseInt((heightTextField.getText()));
            showResizeImage(x,y);
        }
        else if(e.getSource()==brightnessButton){
            brightnessPanel();
        }
        else if(e.getSource()== showBrightnessButton){
            float d = Float.parseFloat(brightnessTextField.getText());
            showBrightnessImage(d);
        }
        else if(e.getSource()== selectFileButton){
            chooseFileImage();
        }
        else if(e.getSource()==closeButton){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        else if(e.getSource()==backButton){
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        }
    }
}
