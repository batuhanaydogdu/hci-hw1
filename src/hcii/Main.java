package hcii;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author batuhan
 */
public class Main extends JFrame {

    Graphics g = getGraphics();
    JButton brec = new JButton("draw Rectangle"), bline = new JButton("draw Line"), bcircle = new JButton("draw Circle"), bmove = new JButton("choseToMove"), bclear = new JButton("clearPanel");
    static String selectedShape;
    static int xStart, xEnd, yStart, yEnd;
    JPanel p = new JPanel(new BorderLayout());
    ArrayList<Component> listOfComp = new ArrayList();
    Component cForMove = null;

    public Main() {
        this.setSize(800, 800);
        this.setTitle("my paint");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        brec.setBounds(40, 40, 200, 50);
        p.setBounds(0, 100, 800, 700);
        p.setBackground(Color.yellow);
        this.setLayout(null);

        brec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                selectedShape = "rec";
            }
        });
        bline.setBounds(250, 40, 200, 50);
        bline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                selectedShape = "line";
            }
        });

        bcircle.setBounds(460, 40, 200, 50);
        bcircle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                selectedShape = "circle";

            }
        });
        bmove.setBounds(670, 40, 113, 50);
        bmove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                selectedShape = "forMove";

            }
        });
        bclear.setBounds(0, 0, 100, 30);
        bclear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                p.repaint();
                selectedShape = "";
            }
        });

        this.add(bclear);
        this.add(bmove);
        this.add(bcircle);
        this.add(brec);
        this.add(bline);
        p.setFocusable(true);
        this.add(p);
        this.setVisible(true);

        p.addMouseListener(new MouseAdapter() {
            boolean b = false;

            @Override
            public void mousePressed(MouseEvent e) {

                xStart = e.getX();
                yStart = e.getY();
                System.out.println(yStart + " " + xStart);

                if (selectedShape.equals("forMove")) {
                    for (int i = 0; i < listOfComp.size(); i++) {//for taking shapes
                        int getx = (int) listOfComp.get(i).getLocation().getX();
                        int width = listOfComp.get(i).getWidth();
                        cForMove = listOfComp.get(i);
                        if (getx < xStart && width + getx > xStart) {
                            int gety = (int) listOfComp.get(i).getLocation().getY();
                            int heigth = listOfComp.get(i).getHeight();
                            if (gety < yStart && heigth + gety > yStart) {
                                b = true;
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!b) {
                    xEnd = e.getX();
                    yEnd = e.getY();
                    listOfComp.add(new custompaintComponent());
                    p.add(listOfComp.get(listOfComp.size() - 1));
                    p.repaint(xStart, yStart, xEnd, yEnd);
                } else {
                    cForMove.setBounds(xEnd, yEnd, cForMove.getWidth(), cForMove.getHeight());
                }
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }

    static class custompaintComponent extends Component {

        @Override
        public void paint(Graphics g) {

            g.setColor(Color.black);
            if (xStart > xEnd) {
                int a = xStart;
                xStart = xEnd;
                xEnd = a;
            }
            if (yStart > yEnd) {
                int a = yStart;
                yStart = yEnd;
                yEnd = a;
            }
            switch (selectedShape) {

                case "circle":

                    g.drawOval(xStart, yStart, xEnd - xStart, yEnd - yStart);

                    break;
                case "rec":

                    g.drawRect(xStart, yStart, xEnd - xStart, yEnd - yStart);

                    break;
                case "line":

                    g.drawLine(xStart, yStart, xEnd, yEnd);
                    break;
            }
        }
    }
}
