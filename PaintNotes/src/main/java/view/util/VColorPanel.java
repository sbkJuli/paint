//package declaration
package view.util;

//import declarations
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import view.util.mega.MButton;
import view.util.mega.MLabel;
import view.util.mega.MPanel;
import model.settings.TextFactory;
import model.settings.ViewSettings;
import control.util.CColorPanel;


/**
 * Panel for the selection of a new color.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
@SuppressWarnings("serial") public class VColorPanel extends MPanel {

	/**
	 * JTextFields for entering special color.
	 */
	private JTextField jtf_r, jtf_g, jtf_b;
	
	/**
	 * JLabel for selected color and RGB - titles.
	 */
	private MLabel jlbl_selectedColor, 
	jlbl_r , jlbl_g, jlbl_b;

	/**
	 * MButton for applying the changes.
	 */
	private MButton jbtn_applyCanges;
	
	/**
	 * Array of buttons which 
	 *     are already there and have to be updated
	 *     have to be added to the colorPanel.
	 */
	private MButton [] jbtn_colorsUpdate, jbtn_colorsPanel;
	
	
	/**
	 * the controller class.
	 */
	private CColorPanel control;

	/*
	 * final values used for computing the size of stuff
	 */
	
	/**
	 * distance between single components in this class.
	 */
	private final int distanceItems = 10;

	/**
	 * amount of items in row in MButton array for colors.
	 */
    private final int amountInRows = 7;
    
    /**
     * The size of the big color.
     */
    private final int sizeBigColor = 50;
    
	/**
	 * Constructor: initializes things.
	 * 
	 * @param _jbtn_toUpdate the MButtons which are being updated.
	 */
	public VColorPanel(final MButton[] _jbtn_toUpdate) {

		//initialize JPanel and alter settings
		super();
		super.setOpaque(false);
		super.setLayout(null);
		super.setBackground(ViewSettings.GENERAL_CLR_BACKGROUND);
		super.setVisible(false);

        //save the MButton 
        this.jbtn_colorsPanel = _jbtn_toUpdate;
        control = new CColorPanel(this);

        //add components
		int height = addComponents();
		
		jlbl_selectedColor.setBounds(
		        jbtn_colorsUpdate[jbtn_colorsUpdate.length - 1].getX()
		        + distanceItems / 2
		        + jbtn_colorsUpdate[jbtn_colorsUpdate.length - 1].getWidth(), 
		        
		        jbtn_colorsUpdate[amountInRows - 1].getY(), 
		        sizeBigColor + (1 + 2) * (2 + 2), 
		        height * (2) + distanceItems / 2);
		
		jbtn_applyCanges.setBounds(jlbl_selectedColor.getX(), 
		        jlbl_selectedColor.getY() + jlbl_selectedColor.getHeight() 
		        + distanceItems / 2,
		        jlbl_selectedColor.getWidth(), (2 + 2 + 2) * (2 + 2 + 1));
		
		/*
		 * add listeners
		 */
		//keyListener for changing current color by changing the RGB values
		jtf_r.addKeyListener(control);
		jtf_g.addKeyListener(control);
		jtf_b.addKeyListener(control);
		
		//mouse and -motionListener for changing by clicking at color area
		for (int currentButton = 0; 
		        currentButton < getJbtn_colorsPanel().length;
		        currentButton++) {

		    jbtn_colorsUpdate[currentButton]
		            .addMouseMotionListener(control); 
		    jbtn_colorsUpdate[currentButton]
		            .addMouseListener(control);
		}
	        
		//MButton for applying changes
		getJbtn_applyCanges().addMouseListener(control);

		//set location
		jlbl_r.setLocation(distanceItems,
		        jbtn_colorsUpdate[jbtn_colorsUpdate.length - 1].getY() 
		        + jbtn_colorsUpdate[0].getHeight() + distanceItems / 2);
		jtf_r.setLocation(jlbl_r.getX() + jlbl_r.getWidth(), jlbl_r.getY());
	        
	        
		jlbl_g.setLocation(jtf_r.getX() + jtf_r.getWidth() 
		        + distanceItems, jlbl_r.getY());

		jtf_g.setLocation(jlbl_g.getX() + jlbl_g.getWidth(), jlbl_g.getY());
		jlbl_b.setLocation(jtf_g.getX() + jtf_g.getWidth() 
		        + distanceItems, jlbl_g.getY());
		jtf_b.setLocation(jlbl_b.getX() + jlbl_b.getWidth(), jlbl_b.getY());
	        
		super.setSize(ViewSettings.getSIZE_PNL_CLR());
		super.setVisible(true);
	}
	
	
	/**
	 * add components to JPanel and return height.
	 * @return the height
	 */
	private int addComponents() {

        //save final values that are rather unimportant.
        final Dimension dim_JLabel = new Dimension(40, 16);
        final Dimension dim_JTextField = new Dimension(30, 16);
        final int widthTotal = 500;
        final int currentHeight = 150;


        //JLabel for the selected Color
        jlbl_selectedColor = new MLabel();
        jlbl_selectedColor.setSize(sizeBigColor, sizeBigColor);
        jlbl_selectedColor.setBackground(Color.pink);
        jlbl_selectedColor.setOpaque(true);
        jlbl_selectedColor.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.black), new LineBorder(Color.white)));
        super.add(jlbl_selectedColor);

        /*
         * JLabels and JTextFields for color RGB Values
         */
        jlbl_r = new MLabel(TextFactory.getInstance().getTextColorPanel_red());
        jlbl_r.setSize(dim_JLabel);
        jlbl_r.setOpaque(false);
        jlbl_r.setFont(ViewSettings.GENERAL_FONT_ITEM);
        jlbl_r.setBorder(null);
        super.add(jlbl_r);

        jtf_r  = new JTextField();
        jtf_r.setOpaque(false);
        jtf_r.setFont(ViewSettings.GENERAL_FONT_ITEM);
        jtf_r.setSize(dim_JTextField);
        jtf_r.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        super.add(jtf_r);

        jlbl_g = new MLabel(
                TextFactory.getInstance().getTextColorPanel_green());
        jlbl_g.setSize(dim_JLabel);
        jlbl_g.setOpaque(false);
        jlbl_g.setBorder(null);
        jlbl_g.setFont(ViewSettings.GENERAL_FONT_ITEM);
        super.add(jlbl_g);

        jtf_g  = new JTextField();
        jtf_g.setOpaque(false);
        jtf_g.setSize(dim_JTextField);
        jtf_g.setFont(ViewSettings.GENERAL_FONT_ITEM);
        jtf_g.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        super.add(jtf_g);
        
        jlbl_b = new MLabel(
                TextFactory.getInstance().getTextColorPanel_blue());
        jlbl_b.setSize(dim_JLabel);
        jlbl_b.setOpaque(false);
        jlbl_b.setBorder(null);
        jlbl_b.setFont(ViewSettings.GENERAL_FONT_ITEM);
        super.add(jlbl_b);

        jtf_b  = new JTextField();
        jtf_b.setOpaque(false);
        jtf_b.setSize(dim_JTextField);
        jtf_b.setFont(ViewSettings.GENERAL_FONT_ITEM);
        jtf_b.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        jtf_b.setFocusable(true);
        super.add(jtf_b);

        /*
         * MButton for inserting.
         */
        jbtn_applyCanges = new MButton(
                TextFactory.getInstance().getTextColorPanel_submit());
        jbtn_applyCanges.setContentAreaFilled(false);
        jbtn_applyCanges.setFont(ViewSettings.GENERAL_FONT_ITEM);
        jbtn_applyCanges.setOpaque(false);
        jbtn_applyCanges.setFocusable(false);
        super.add(jbtn_applyCanges);

        //add controller for this class.

        this.insertColorPanel(widthTotal, currentHeight);
        
        int width = sizeBigColor / 2;
        int height = sizeBigColor / (2 + 1);
        
        jbtn_colorsUpdate = new MButton[jbtn_colorsPanel.length];
        for (int i = 0; i < jbtn_colorsUpdate.length; i++) {
            jbtn_colorsUpdate[i] = new MButton();
            jbtn_colorsUpdate[i].setBounds(distanceItems 
                    + (i % amountInRows) * (width + 2), -distanceItems 
                    + currentHeight + (i / amountInRows) * (height + 2),
                    width, height);
            jbtn_colorsUpdate[i].setBackground(
                    jbtn_colorsPanel[i].getBackground());
            jbtn_colorsUpdate[i].setOpaque(true);
            
            if (i == control.getSelectedPosition()) {
                jbtn_colorsUpdate[i].setBorder(BorderFactory.createLineBorder(
                        Color.red, 2));
            } else {
                jbtn_colorsUpdate[i].setBorder(BorderFactory
                        .createCompoundBorder(
                                new LineBorder(Color.black), 
                                new LineBorder(Color.white)));
            }
            super.add(jbtn_colorsUpdate[i]);
        }
        
        //return the current height
        return height;
	}
	
	/**
	 * insert the color panel to the ColorPanel.
	 * 
	 * @param _width the width of the panel
	 * @param _height the height of the panel
	 * 
	 * @return the height of the ColorPanel.
	 */
	private int insertColorPanel(final int _width, final int _height) {

        /**
         * first and second colorPanels
         */
        final Item1Menu it_color1, it_color2;
        final byte itemsInRow = (byte) 32;
        final int abstand = 8, abstand2 = 8, maxRGB = 255, komisch = 10, 
                heightPanel = 70, yThing2 = 73;
        final Dimension dim_it_color = new Dimension(_width, _height / 2 
                - komisch);
        
        //initialize them
        it_color1 = new Item1Menu(false);
        it_color1.setOrderHeight(true);
        it_color1.setSize(new Dimension(1, heightPanel));
        it_color1.setItemsInRow(itemsInRow);
        
        it_color2 = new Item1Menu(false);
        it_color2.setOrderHeight(true);
        it_color2.setSize(new Dimension(1, heightPanel));
        it_color2.setItemsInRow(itemsInRow);

        /*
         * gray (to red)
         */
        for (int gb = maxRGB; gb >= 0; gb -= abstand2) {
            for (int r = maxRGB; r >= 0; r -= abstand) {

                //update.
                int tempGb = gb;
                if (tempGb > r) {
                    tempGb = r;
                }
                //insert color to panel
                insertPanel(it_color1, new Color(r, tempGb, tempGb));
            }
        }   
        
        /*
         * red (to green)
         */
        for (int g = 0; g <= maxRGB; g += abstand2) {
            for (int r = maxRGB; r >= 0; r -= abstand) {

                //update.
                int tempGb = g;
                if (tempGb > r) {
                    tempGb = r;   
                }
                //insert color to panel
                insertPanel(it_color1, new Color(r, tempGb, 0));
            }
        }   
        for (int i = maxRGB; i >= 0; i -= abstand2) {
            for (int r = maxRGB; r >= 0; r -= abstand) {

                //update.
                int tempi = i;
                if (tempi > r) {
                    tempi = r;
                }
                //insert color to panel
                insertPanel(it_color1, new Color(tempi, r, 0));
            }
        }
        
        /*
         * green (to blue)
         */
        for (int blue = 0; blue <= maxRGB; blue += abstand2) {
            for (int r = maxRGB; r >= 0; r -= abstand) {

                //update.
                int tempGb = blue;
                if (tempGb > r) {
                    tempGb = r;
                }
                //insert color to panel
                insertPanel(it_color1, new Color(0, r, tempGb));
            }
        }   
        for (int blue = 0; blue <= maxRGB; blue += abstand2) {
            for (int r = 0; r <= maxRGB; r += abstand) {

                //update.
                int tempGb = blue;
                if (tempGb > r) {
                    tempGb = r;
                }
                //insert color to panel
                insertPanel(it_color2, new Color(0, r, tempGb));
            }
        }   
        for (int i = maxRGB; i >= 0; i -= abstand2) {
            for (int r = 0; r <= maxRGB; r += abstand) {

                //update.
                int tempi = i;
                if (tempi > r) {
                    tempi = r;
                }
                //insert color to panel
                insertPanel(it_color2, new Color(0, tempi, r));
            }
        }
        /*
         * blue (to red)
         */
        for (int blue = 0; blue <= maxRGB; blue += abstand2) {
            for (int r = 0; r <= maxRGB; r += abstand) {

                //update .
                int tempGb = blue;
                if (tempGb > r) {
                    tempGb = r;
                }
                //insert color to panel
                insertPanel(it_color2, new Color(tempGb, 0, r));
            }
        }   
        for (int i = maxRGB; i >= 0; i -= abstand2) {
            for (int r = 0; r <= maxRGB; r += abstand) {

                //update.
                int tempi = i;
                if (tempi > r) {
                    tempi = r;
                }
                //insert color to panel
                insertPanel(it_color2, new Color(r, 0, tempi));
            }
        }

        it_color1.getMPanel().setVisible(true);
        it_color1.getMPanel().setLocation(distanceItems, distanceItems);
        it_color1.getMPanel().setSize(dim_it_color);

        it_color2.getMPanel().setVisible(true);
        it_color2.getMPanel().setLocation(it_color1.getMPanel().getX(),
                yThing2);
        it_color2.getMPanel().setSize(dim_it_color);
        super.add(it_color2.getMPanel());
        super.add(it_color1.getMPanel());
        
        return it_color1.getMPanel().getHeight();
	}

	
	/**
	 * insert JPanel to Item1Menu for color panel.
	 * @param _it_color the panel where to add
	 * @param _color the color of JPanel
	 */
	private void insertPanel(final Item1Menu _it_color, 
	        final Color _color) {
	    MPanel jpnl_currentColor = new MPanel();
        _it_color.add(jpnl_currentColor);
        jpnl_currentColor.addMouseMotionListener(control); 
        jpnl_currentColor.addMouseListener(control);
        jpnl_currentColor.setOpaque(true);
        jpnl_currentColor.setBackground(_color);
        jpnl_currentColor.setBorder(null);
	}
	/*
	 * getter and setter methods.
	 */

	/**
	 * @return the jtf_r
	 */
	public final JTextField getJtf_r() {
		return jtf_r;
	}


	/**
	 * @return the jtf_g
	 */
	public final JTextField getJtf_g() {
		return jtf_g;
	}


	/**
	 * @return the jtf_b
	 */
	public final JTextField getJtf_b() {
		return jtf_b;
	}


	/**
	 * @return the jlbl_selectedColor
	 */
	public final MLabel getJlbl_selectedColor() {
		return jlbl_selectedColor;
	}


	/**
	 * @return the jbtn_schonDa
	 */
	public final MButton [] getJbtn_colorsUpdate() {
		return jbtn_colorsUpdate;
	}


	/**
	 * @return the jbtn_copy
	 */
	public final MButton [] getJbtn_colorsPanel() {
		return jbtn_colorsPanel;
	}


	/**
	 * @return the jbtn_applyCanges
	 */
	public final MButton getJbtn_applyCanges() {
		return jbtn_applyCanges;
	}
}