//package declaration
package view.forms.tabs;

//import declarations
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import model.settings.Constants;
import model.settings.Status;
import model.settings.TextFactory;
import model.settings.ViewSettings;
import control.ControlPainting;
import control.tabs.CPaintStatus;
import control.tabs.CPaintVisualEffects;
import control.tabs.CPaintSelection;
import view.util.Item1Menu;
import view.util.Item1PenSelection;
import view.util.VColorPanel;
import view.util.VLabel;
import view.util.Item1Button;


/**
 * Paint tab.
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
@SuppressWarnings("serial")
public final class Paint extends JPanel {


    /**
     * The menu - item for creating own colors.
     */
    private Item1Menu it_color;
    
	/**
	 * pens for selection in Items it_stift1 and it_stift2.
	 */
	private Item1PenSelection sa_br1, sa_br2, sa_bn1, sa_bn2, sa_bp1, sa_bp2,
	sa_fr1, sa_fr2, sa_fn1, sa_fn2, sa_fp1, sa_fp2, sa_kr1, sa_kr2, sa_kn1,
	sa_kn2, sa_kp1, sa_kp2, sa_mr1, sa_mr2, sa_mn1, sa_mn2, sa_mp1, sa_mp2,
	sa_pr1, sa_pr2, sa_pn1, sa_pn2, sa_pp1, sa_pp2;

	/**
	 * array of colors to change the first or the second color.
	 */
	private JButton [] jbtn_colors;

	/**
	 * Buttons for the second and the first color.
	 */
	private Item1Button tb_color1, tb_color2;
	
	/**
	 * utility buttons.
	 */
	private Item1Button tb_save, tb_zoomOut, tb_zoomIn, tb_copy, tb_paste,
	tb_cut, tb_new, tb_load, tb_prev, tb_next, tb_turnNormal, tb_turnMirror;

	/**
	 * item for the second and the first pen.
	 */		
	private Item1Menu it_stift1, it_stift2;
	
	/**
	 * JLabels for the separation, linked with information.
	 */
	private JLabel [] jlbl_separation;
	
	/**
	 * buttons.
	 */
	private Item1Button tb_pipette, tb_fill, tb_move, tb_erase, 
	tb_selectionLine, tb_selectionMagic, tb_selectionCurve, tb_saveAs;
	/**
	 * JLabels for the information text, linked with separation line.
	 */
	private VLabel [] jlbl_information;
	
	/**
	 * Selection menu.
	 */
	private Item1Menu it_selection;
	
	/**
	 * the only instance of this class.
	 */
	private static Paint instance;
	
	/**
	 * constants.
	 */
    private final int fifety = 50,  hundredFourtyFife = 145;
    
	/**
	 * Constructor of Paint.
	 */
	private Paint() {

		//initialize JPanel and alter settings
		super();
		super.setOpaque(false);
		super.setLayout(null);
		
		//initialize items 
		final int amountOfSeparations = 6;
        jlbl_separation = new JLabel[amountOfSeparations];
        jlbl_information = new VLabel[amountOfSeparations];
		
		int x = initializeClipboard(0, true);
        x = initializeHistory(x, true);
		x = initializePagePens(x, true);
        x = initializePageColors(x, true);
		x = initializeZoom(x, true);
        x = initializeFileOperations(x, true);
		

        //activate first color and set colors.
        tb_color1.setActivated(true);
		tb_color1.setBackground(Color.black);
		tb_color2.setBackground(Color.white);

		//set size
		super.setSize((int) Toolkit.getDefaultToolkit().getScreenSize()
		        .getWidth(), ViewSettings.VIEW_HEIGHT_TB);

		//set standard values (is going to be put into a new settings class)
		it_stift1.setIcon(sa_pn1.getImagePath());
		it_stift2.setIcon(sa_bn2.getImagePath());
	}

	/**
	 * initialize buttons such as 
	 * paste, copy, cut, zoomIn, zoomOut, save.
	 * 
	 * @param _x the starting x coordinate
	 * @param _paint whether to paint or just to resize.
	 * @return the x coordinate for following items.
	 */
	private int initializeClipboard(final int _x, final boolean _paint) {
		
	    if (_paint) {

	        //paste
	        tb_paste = new Item1Button(tb_paste);
	        tb_paste.setSize(ViewSettings.ITEM_WIDTH, ViewSettings.ITEM_HEIGHT);
	        tb_paste.setBorder(false);
	        tb_paste.addMouseListener(ControlPainting.getInstance());
	        initializeTextButton(tb_paste,
	                TextFactory.getInstance().getTextViewTb_paste(),
	                Constants.VIEW_TB_PASTE_PATH, 0);
	        tb_paste.setActivable(false);

	        //copy
	        tb_copy = new Item1Button(tb_copy);
	        tb_copy.setSize(ViewSettings.ITEM_WIDTH, ViewSettings.ITEM_HEIGHT);
	        tb_copy.setBorder(false);
	        tb_copy.addMouseListener(ControlPainting.getInstance());
	        initializeTextButton(tb_copy,
	                TextFactory.getInstance().getTextViewTb_copy(),
	                Constants.VIEW_TB_COPY_PATH, 0);
	        tb_copy.setActivable(false);

	        //cut
	        tb_cut = new Item1Button(tb_cut);
	        tb_cut.setSize(ViewSettings.ITEM_WIDTH, ViewSettings.ITEM_HEIGHT);
	        tb_cut.setBorder(false);
	        tb_cut.addMouseListener(ControlPainting.getInstance());
	        initializeTextButton(tb_cut,
	                TextFactory.getInstance().getTextViewTb_cut(),
	                Constants.VIEW_TB_CUT_PATH, 0);
	        tb_cut.setActivable(false);

	    }
	    
	    if (Status.isNormalRotation()) {

	        tb_paste.setLocation(_x + ViewSettings.DISTANCE_BETWEEN_ITEMS,
	                ViewSettings.DISTANCE_BETWEEN_ITEMS);
	        tb_copy.setLocation(tb_paste.getX() 
	                + ViewSettings.DISTANCE_BETWEEN_ITEMS 
	                + tb_paste.getWidth(), ViewSettings.DISTANCE_BETWEEN_ITEMS);
	        tb_cut.setLocation(tb_copy.getX(), tb_copy.getHeight() 
	                + ViewSettings.DISTANCE_BETWEEN_ITEMS + tb_copy.getY());
	        insertTrennung(tb_cut.getWidth() + tb_cut.getX() 
	                + ViewSettings.DISTANCE_BEFORE_LINE, tb_copy.getY(), 0, 
	                _paint);
	    } else {

            tb_paste.setLocation(
                    ViewSettings.VIEW_WIDTH_TB 
                    - _x - ViewSettings.DISTANCE_BETWEEN_ITEMS
                    - ViewSettings.ITEM_WIDTH, 
                    ViewSettings.VIEW_HEIGHT_TB 
                    - ViewSettings.DISTANCE_BETWEEN_ITEMS 
                    - ViewSettings.ITEM_HEIGHT - fifety);
           
            tb_copy.setLocation(tb_paste.getX()
                    - ViewSettings.DISTANCE_BETWEEN_ITEMS 
                    - tb_paste.getWidth(), tb_paste.getY());
            tb_cut.setLocation(tb_copy.getX(), tb_copy.getY()
                    - tb_copy.getHeight() 
                    - ViewSettings.DISTANCE_BETWEEN_ITEMS);
            insertTrennung(tb_cut.getX() - ViewSettings.DISTANCE_BEFORE_LINE, 
                    tb_copy.getY() + tb_copy.getHeight() - hundredFourtyFife, 
                    0, _paint);
         }
	    
	    
	    insertInformation("Zwischenablage", jlbl_separation[0].getX(), 
	            jlbl_separation[0].getX() + jlbl_separation[0].getWidth(), 
	            0, _paint);
	       
        tb_paste.flip(Status.isNormalRotation());
        tb_copy.flip(Status.isNormalRotation());
        tb_cut.flip(Status.isNormalRotation());
        
        
	    
		return jlbl_separation[0].getX() + ViewSettings.DISTANCE_AFTER_LINE;
	}
	
	
	
	/**
	 * initialize the history buttons.
	 * 
	 * @param _x the x coordinate where to continue
	 * @param _paint whether to paint or not
	 * 
	 * @return the new x coordinate
	 */
	private int initializeHistory(final int _x, final boolean _paint) {


	    if (_paint) {

	        tb_prev = new Item1Button(null);
	        tb_prev.setSize(ViewSettings.ITEM_WIDTH, ViewSettings.ITEM_HEIGHT);
	        tb_prev.setBorder(false);
	        initializeTextButton(tb_prev,
	                "previous",
	                Constants.VIEW_TB_PREV_PATH, 0);

	        tb_next = new Item1Button(null);
	        tb_next.setSize(ViewSettings.ITEM_WIDTH, ViewSettings.ITEM_HEIGHT);
	        tb_next.setBorder(false);
	        initializeTextButton(tb_next,
	                "next",
	                Constants.VIEW_TB_NEXT_PATH, 0);

	    
	    }
	    
	    if (Status.isNormalRotation()) {

	        tb_prev.setLocation(_x, ViewSettings.DISTANCE_BETWEEN_ITEMS);
	        tb_next.setLocation(
	                tb_prev.getX(), tb_prev.getY() + tb_prev.getHeight());
	        insertTrennung(tb_prev.getWidth() + tb_prev.getX() 
	                + ViewSettings.DISTANCE_BEFORE_LINE, tb_prev.getY(), 
	                1, _paint);
	        insertInformation("history", _x, jlbl_separation[1].getX(), 1, 
	                _paint);
  
	    } else  {

	        tb_prev.setLocation(_x - tb_prev.getWidth() 
	                - ViewSettings.DISTANCE_AFTER_LINE, 
	                ViewSettings.VIEW_HEIGHT_TB 
                    - ViewSettings.DISTANCE_BETWEEN_ITEMS 
                    - ViewSettings.ITEM_HEIGHT - fifety);
	        tb_next.setLocation(tb_prev.getX(), tb_prev.getY() 
	                - ViewSettings.DISTANCE_BETWEEN_ITEMS 
	                - tb_prev.getHeight());
            insertTrennung(tb_prev.getX() 
                    - ViewSettings.DISTANCE_BEFORE_LINE, tb_prev.getY()
                    + tb_prev.getHeight() - hundredFourtyFife, 1, _paint);
	        insertInformation("history", _x, jlbl_separation[1].getX(), 
	                1, _paint);
  
	    }
        return jlbl_separation[1].getX() + ViewSettings.DISTANCE_AFTER_LINE;

	}


	/**
	 * initialize page pen buttons.
	 * 
     * @param _x the x coordinate where to continue
     * @param _paint whether to paint or not
     * 
     * @return the new x coordinate
	 */
	private int initializePagePens(final int _x, final boolean _paint) {
        final Dimension sizeIT = new Dimension(550, 550);
        final Dimension sizeIT_selection = new Dimension(350, 370);
//        = new Dimension(350, 270);//for my laptop
        final int sizeHeight = 110;
        
        if (_paint) {
        	it_stift1 = new Item1Menu();
        	it_stift1.setBorder(null);
        	it_stift1.setText("Stift 1");
            it_stift1.setItemsInRow((byte) 1);
            it_stift1.setActivable();
            it_stift1.setSize(sizeIT);
            it_stift1.setSizeHeight(fifety);
        	it_stift1.setBorder(false);
        	super.add(it_stift1);
        
        	it_stift2 = new Item1Menu();
        	it_stift2.setText("Stift 2");
        	it_stift2.setSize(sizeIT);
        	it_stift2.setActivable();
        	it_stift2.setItemsInRow((byte) 1);
        	it_stift2.setSizeHeight(fifety);
        	it_stift2.setBorder(false);
        	super.add(it_stift2);
        	addPens();
        	
            it_selection = new Item1Menu();
            it_selection.setText("Auswahl");
            it_selection.setBorder(false);
            it_selection.setActivable();
            it_selection.setSize(sizeIT_selection);
            it_selection.setIcon(Constants.VIEW_TB_SELECT_LINE_PATH);
            it_selection.setItemsInRow((byte) (2 + 1));
            it_selection.setSizeHeight(sizeHeight);
            it_selection.removeScroll();
        
            tb_selectionLine = new Item1Button(null);
            tb_selectionLine.setBorder(false);
            it_selection.add(tb_selectionLine);
            initializeTextButtonOhneAdd(tb_selectionLine,
                    "line",
                    Constants.VIEW_TB_SELECT_LINE_PATH);
            tb_selectionLine.setSize(tb_selectionLine.getWidth(), 
                    tb_selectionLine.getHeight());
            tb_selectionLine.setOpaque(false);
        
            tb_selectionCurve = new Item1Button(null);
            tb_selectionCurve.setBorder(false);
            it_selection.add(tb_selectionCurve);
            initializeTextButtonOhneAdd(tb_selectionCurve, "curve",
                    Constants.VIEW_TB_SELECT_CURVE_PATH);
            tb_selectionCurve.setSize(tb_selectionCurve.getWidth(), 
                    tb_selectionCurve.getHeight());
            tb_selectionCurve.setOpaque(false);
        
            tb_selectionMagic = new Item1Button(null);
            tb_selectionMagic.setBorder(false);
            it_selection.add(tb_selectionMagic);
            initializeTextButtonOhneAdd(tb_selectionMagic, "magic",
                    Constants.VIEW_TB_SELECT_MAGIC_PATH);
            tb_selectionMagic.setSize(tb_selectionMagic.getWidth(), 
                    tb_selectionMagic.getHeight());
            tb_selectionMagic.setOpaque(false);
        
            JCheckBox jcb_whole = new JCheckBox("whole");
            jcb_whole.setSelected(true);
            jcb_whole.setVerticalAlignment(SwingConstants.TOP);
            jcb_whole.setFocusable(false);
            it_selection.add(jcb_whole);
        
            JCheckBox jcb_separated = new JCheckBox("separated");
            jcb_separated.setVerticalAlignment(SwingConstants.TOP);
            jcb_separated.setFocusable(false);
            it_selection.add(jcb_separated);
        
            JCheckBox jcb_image = new JCheckBox("image");
            jcb_image.setFocusable(false);
            jcb_image.setVerticalAlignment(SwingConstants.TOP);
            it_selection.add(jcb_image);
            //initialize MouesListiener
            CPaintSelection.getInstance(
                    jcb_whole, jcb_separated, jcb_image,
                    tb_selectionLine, tb_selectionCurve, tb_selectionMagic);
            it_selection.setBorder(false);
            super.add(it_selection);

            tb_pipette = new Item1Button(null);
            tb_pipette.setSize(tb_copy.getWidth(), tb_copy.getHeight());
            tb_pipette.setBorder(false);
            initializeTextButton(tb_pipette, "pipette",
                    Constants.VIEW_TB_PIPETTE_PATH, 0);
            tb_pipette.setActivable(true);
    
            tb_fill = new Item1Button(null);
            tb_fill.setActivable(true);
            tb_fill.setSize(tb_copy.getWidth(), tb_copy.getHeight());
            tb_fill.setBorder(false);
            initializeTextButton(tb_fill, "fuellen",
                    Constants.VIEW_TB_FILL_PATH, 0);
            tb_fill.setActivable(true);
            
            tb_move = new Item1Button(null);
            tb_move.setSize(tb_copy.getWidth(), tb_copy.getHeight());
            tb_move.setBorder(false);
            initializeTextButton(tb_move, "nothing",
                    Constants.VIEW_TB_PIPETTE_PATH, 0);
            tb_move.setActivable(true);
    
            tb_erase = new Item1Button(null);
            tb_erase.setActivable(true);
            tb_erase.setSize(tb_copy.getWidth(), tb_copy.getHeight());
            tb_erase.setBorder(false);
            initializeTextButton(tb_erase, "Erase",
                    Constants.VIEW_TB_PIPETTE_PATH, 0);
            tb_erase.setActivable(true);
        }
        
        if (Status.isNormalRotation()) {

            it_stift1.setLocation(_x, ViewSettings.DISTANCE_BETWEEN_ITEMS);
            it_stift2.setLocation(it_stift1.getX() + it_stift1.getWidth() 
                    + ViewSettings.DISTANCE_BETWEEN_ITEMS,
                    ViewSettings.DISTANCE_BETWEEN_ITEMS);
            it_selection.setLocation(it_stift2.getWidth() + it_stift2.getX() 
                    + ViewSettings.DISTANCE_BETWEEN_ITEMS, 
                    ViewSettings.DISTANCE_BETWEEN_ITEMS);
            tb_pipette.setLocation(it_selection.getX() + it_selection.getWidth()
                    + ViewSettings.DISTANCE_BETWEEN_ITEMS,
                    it_stift2.getY());
            tb_fill.setLocation(tb_pipette.getX() 
                    + ViewSettings.DISTANCE_BETWEEN_ITEMS,
                    tb_pipette.getY() + tb_pipette.getHeight() 
                    + ViewSettings.DISTANCE_BETWEEN_ITEMS);
            
            tb_move.setLocation(tb_pipette.getX() + tb_pipette.getWidth()
                    + ViewSettings.DISTANCE_BETWEEN_ITEMS,
                    tb_pipette.getY());
            tb_erase.setLocation(tb_move.getX() 
                    + ViewSettings.DISTANCE_BETWEEN_ITEMS,
                    tb_move.getY() + tb_move.getHeight() 
                    + ViewSettings.DISTANCE_BETWEEN_ITEMS);
        }
        insertTrennung(tb_move.getWidth() + tb_move.getX() 
                + ViewSettings.DISTANCE_BEFORE_LINE, 
                tb_move.getY(), 2, _paint);
        insertInformation("Stifte", _x, jlbl_separation[2].getX(), 2, _paint);
    	
    	return jlbl_separation[2].getX() + ViewSettings.DISTANCE_AFTER_LINE;
    }

	/**
	 * initialize the colors of the page.
	 * @param _x the x coordinate
	 * @param _paint whether to paint
	 * @return the new position.
	 */
    private int initializePageColors(final int _x, final boolean _paint) {
    
    	//the first color for the first pen
    	tb_color1 = new Item1Button(null);
    	tb_color1.setOpaque(true);
    	tb_color1.addMouseListener(CPaintStatus.getInstance());
    	tb_color1.setBorder(BorderFactory.createCompoundBorder(
    	        new LineBorder(Color.black), new LineBorder(Color.white)));
    	tb_color1.setLocation(_x, ViewSettings.DISTANCE_BETWEEN_ITEMS);
    	tb_color1.setSize(
    	        ViewSettings.ITEM_MENU1_WIDTH, ViewSettings.ITEM_MENU1_HEIGHT);
    	tb_color1.setText("Farbe 1");
    	super.add(tb_color1);
    
    	//the second color for the second pen
    	tb_color2 = new Item1Button(null);
    	tb_color2.setOpaque(true);
    	tb_color2.addMouseListener(CPaintStatus.getInstance());
    	tb_color2.setBorder(BorderFactory.createCompoundBorder(
    	        new LineBorder(Color.black), new LineBorder(Color.white)));
    	tb_color2.setLocation(tb_color1.getWidth() + tb_color1.getX() 
    	        + 2 + 2 + 2 + 1, ViewSettings.DISTANCE_BETWEEN_ITEMS);
    	tb_color2.setSize(tb_color1.getWidth(), tb_color1.getHeight());
    	tb_color2.setText("Farbe 2");
    	super.add(tb_color2);
    
    	final int distanceBetweenColors = 2;
    	int width = (2 + 2 + 1) * (2 + 2 + 1) - 2 - 2;
    	int height = width + 2 + 1 + 2 * (2 + 1);
    	int anzInR = 2 + 2 + 2 + 1;
    	jbtn_colors = new JButton[anzInR * (2 + 2)];
    	for (int i = 0; i < jbtn_colors.length; i++) {
    		jbtn_colors[i] = new JButton();
    		jbtn_colors[i].setBounds(tb_color2.getX() + tb_color2.getWidth() 
    		        + distanceBetweenColors + (i % anzInR) 
    		        * (width + distanceBetweenColors), 
    		        distanceBetweenColors + (i / anzInR)
    		        * (height + distanceBetweenColors), width, height);
    		jbtn_colors[i].setOpaque(true);
    		jbtn_colors[i].addMouseListener(
    		        CPaintStatus.getInstance());
    		jbtn_colors[i].addMouseListener(CPaintVisualEffects.getInstance());
    		jbtn_colors[i].setBorder(BorderFactory.createCompoundBorder(
    		        new LineBorder(Color.black), new LineBorder(Color.white)));
    		super.add(jbtn_colors[i]);
    	}
    	int i = 0;
    	final int three = 3;
    	final Color 
    	        c1n0 = Color.black,
    	        c1n1 = new Color(80, 80, 80),
    	        c1n2 = new Color(160, 160, 160),
    	        c1n3 = Color.white,
    	        
    	        c2n0 = new Color(23, 32, 164),
    	        c2n1 = new Color(63, 72, 204), 
    	        c2n2 = new Color(103, 112, 244),
    	        c2n3 = new Color(153, 162, 255), 

    	        c3n0 = new Color(180, 10, 10),
    	        c3n1 = new Color(200, 20, 20), 
    	        c3n2 = new Color(250, 75, 75),
    	        c3n3 = new Color(255, 100, 100), 

    	        c4n0 = new Color(24, 157, 45),
    	        c4n1 = new Color(34, 177, 67), 
    	        c4n2 = new Color(64, 197, 97),
    	        c4n3 = new Color(104, 255, 147), 
    	                
    	        c5n0 = new Color(235, 107, 73),
    	        c5n1 = new Color(255, 127, 93), 
    	        c5n2 = new Color(255, 147, 113),
    	        c5n3 = new Color(255, 187, 153), 

    	        c6n0 = new Color(133, 33, 134),
    	        c6n1 = new Color(163, 73, 164), 
    	        c6n2 = new Color(193, 103, 194),
    	        c6n3 = new Color(255, 153, 254),
    	                
    	        c7n0 = new Color(112, 146, 190),
    	        c7n1 = new Color(200, 191, 231), 
    	        c7n2 = new Color(255, 201, 14),
    	        c7n3 = new Color(120, 74, 50);
    
    	//schwarz bis grau
    	jbtn_colors[i + anzInR * 0].setBackground(c1n0);
    	jbtn_colors[i + anzInR * 1].setBackground(c1n1);
    	jbtn_colors[i + anzInR * 2].setBackground(c1n2);
    	jbtn_colors[i + anzInR * three].setBackground(c1n3);
    	//blue
    	i++;
    	jbtn_colors[i + anzInR * 0].setBackground(c2n0);
    	jbtn_colors[i + anzInR * 1].setBackground(c2n1);
    	jbtn_colors[i + anzInR * 2].setBackground(c2n2);
    	jbtn_colors[i + anzInR * three].setBackground(c2n3);
    
    	//red
    	i++;
    	jbtn_colors[i + anzInR * 0].setBackground(c3n0);
    	jbtn_colors[i + anzInR * 1].setBackground(c3n1);
    	jbtn_colors[i + anzInR * 2].setBackground(c3n2);
    	jbtn_colors[i + anzInR * three].setBackground(c3n3);
    
    	//green
    	i++;
    	jbtn_colors[i + anzInR * 0].setBackground(c4n0);
    	jbtn_colors[i + anzInR * 1].setBackground(c4n1);
    	jbtn_colors[i + anzInR * 2].setBackground(c4n2);
    	jbtn_colors[i + anzInR * three].setBackground(c4n3);
    
    	//orange
    	i++;
    	jbtn_colors[i + anzInR * 0].setBackground(c5n0);
    	jbtn_colors[i + anzInR * 1].setBackground(c5n1);
    	jbtn_colors[i + anzInR * 2].setBackground(c5n2);
    	jbtn_colors[i + anzInR * three].setBackground(c5n3);
    
    	//pink
    	i++;
    	jbtn_colors[i + anzInR * 0].setBackground(c6n0);
    	jbtn_colors[i + anzInR * 1].setBackground(c6n1);
    	jbtn_colors[i + anzInR * 2].setBackground(c6n2);
    	jbtn_colors[i + anzInR * three].setBackground(c6n3);
    
    	i++;
    	jbtn_colors[i + anzInR * 0].setBackground(c7n0);
    	jbtn_colors[i + anzInR * 1].setBackground(c7n1);
    	jbtn_colors[i + anzInR * 2].setBackground(c7n2);
    	jbtn_colors[i + anzInR * three].setBackground(c7n3);
    
    	//
    	it_color = new Item1Menu();
        it_color.setSize(ViewSettings.PAINT_SIZE);
    	it_color.setBorder(false);
    	it_color.setText("+ Farben");
    	it_color.setLocation(jbtn_colors[jbtn_colors.length - 1].getX() 
    	        + ViewSettings.DISTANCE_BETWEEN_ITEMS 
    	        + jbtn_colors[jbtn_colors.length - 1].getWidth(), 
    	        ViewSettings.DISTANCE_BETWEEN_ITEMS);
        it_color.getJPanel().add(new VColorPanel(jbtn_colors));
        it_color.setBorder(false);
        it_color.setIcon("icon/palette.png");
        super.add(it_color);
        
    	insertTrennung(it_color.getWidth() + it_color.getX() + ViewSettings
    	        .DISTANCE_BEFORE_LINE, it_color.getY(), 2 + 1, _paint);
    	insertInformation("Farben", _x, jlbl_separation[2 + 1].getX(), 2 + 1, 
    	        _paint);
    	return jlbl_separation[2 + 1].getX() + ViewSettings.DISTANCE_AFTER_LINE;
    }

    /**
	 * initialize buttons such as 
	 * paste, copy, cut, zoomIn, zoomOut, save.
	 * 
     * @param _x the x coordinate where to continue
     * @param _paint whether to paint or not
     * 
     * @return the new x coordinate
	 */
	private int initializeZoom(final int _x, final boolean _paint) {

		//zoom in
		tb_zoomIn = new Item1Button(null);
		tb_zoomIn.setSize(tb_copy.getWidth(), tb_copy.getHeight());
		tb_zoomIn.setLocation(tb_paste.getX(), 
		        tb_paste.getHeight() + 2 + 2 + 2 + 1);
		tb_zoomIn.setLocation(_x , ViewSettings.DISTANCE_BETWEEN_ITEMS);
		tb_zoomIn.setBorder(false);
		initializeTextButton(tb_zoomIn,
				TextFactory.getInstance().getTextViewTb_zoomIn(),
				Constants.VIEW_TB_ZOOM_IN_PATH, 0);
		tb_zoomIn.setActivable(true);

		//zoom out
		tb_zoomOut = new Item1Button(tb_zoomOut);
		tb_zoomOut.setSize(tb_copy.getWidth(), tb_copy.getHeight());
		tb_zoomOut.setLocation(tb_zoomIn.getX(),
				tb_zoomIn.getY() + ViewSettings.DISTANCE_BETWEEN_ITEMS 
				+ tb_zoomIn.getHeight());
		tb_zoomOut.addMouseListener(ControlPainting.getInstance());
		tb_zoomOut.setBorder(false);
		initializeTextButton(tb_zoomOut,
				TextFactory.getInstance().getTextViewTb_zoomOut(),
				Constants.VIEW_TB_ZOOM_OUT_PATH, 0);
		tb_zoomOut.setActivable(false);

		insertTrennung(tb_zoomIn.getWidth() + tb_zoomIn.getX() 
		        + ViewSettings.DISTANCE_BEFORE_LINE, 
		        tb_zoomIn.getY(), 2 + 2, _paint);
		insertInformation("Zoom", _x, jlbl_separation[2 + 2].getX(), 
		        2 + 2, true);

        return jlbl_separation[2 + 2].getX() 
                + ViewSettings.DISTANCE_AFTER_LINE;
	}
	
	/**
	 * 
     * @param _x the x coordinate where to continue
     * @param _paint whether to paint or not
     * 
     * @return the new x coordinate
	 */
	private int initializeFileOperations(final int _x, final boolean _paint) {

        //save
        tb_save = new Item1Button(tb_save);
        tb_save.setSize(tb_copy.getWidth(), tb_copy.getHeight());
        tb_save.setLocation(_x + ViewSettings.DISTANCE_AFTER_LINE,
                tb_zoomIn.getY());
        tb_save.setBorder(false);
        tb_save.addMouseListener(ControlPainting.getInstance());
        initializeTextButton(tb_save,
                TextFactory.getInstance().getTextViewTb_save(),
                Constants.VIEW_TB_SAVE_PATH, 0);
        tb_save.setActivable(false);

        //save as
        tb_saveAs = new Item1Button(tb_saveAs);
        tb_saveAs.setSize(tb_copy.getWidth(), tb_copy.getHeight());
        tb_saveAs.setLocation(tb_save.getX(),
                tb_save.getY() + tb_save.getHeight() 
                + ViewSettings.DISTANCE_BETWEEN_ITEMS);
        tb_saveAs.setBorder(false);
        tb_saveAs.addMouseListener(ControlPainting.getInstance());
        initializeTextButton(tb_saveAs,
                TextFactory.getInstance().getTextViewTb_save() + " as",
                Constants.VIEW_TB_SAVE_PATH, 0);
        tb_saveAs.setActivable(false);

        //save
		tb_load = new Item1Button(tb_load);
		tb_load.setSize(tb_copy.getWidth(), tb_copy.getHeight());
		tb_load.setLocation(tb_save.getWidth() + tb_save.getX()
                + ViewSettings.DISTANCE_BETWEEN_ITEMS, tb_save.getY());
		tb_load.setBorder(false);
		tb_load.addMouseListener(ControlPainting.getInstance());
		initializeTextButton(tb_load,
				"load",
				Constants.VIEW_TB_LOAD_PATH, 0);
		tb_load.setActivable(false);

        //cut
        tb_new = new Item1Button(null);
        tb_new.setSize(tb_copy.getWidth(), tb_copy.getHeight());
        tb_new.setLocation(tb_saveAs.getWidth() + tb_saveAs.getX()
                + ViewSettings.DISTANCE_BETWEEN_ITEMS, tb_saveAs.getY());
        tb_new.setBorder(false);
        tb_new.addMouseListener(ControlPainting.getInstance());
        initializeTextButton(tb_new,
                "new",
                Constants.VIEW_TB_NEW_PATH, 0);
        tb_new.setActivable(false);

        //cut
        tb_turnMirror = new Item1Button(null);
        tb_turnMirror.setSize(tb_load.getWidth(), tb_load.getHeight());
        tb_turnMirror.setLocation(tb_load.getWidth() + tb_load.getX() 
                + ViewSettings.DISTANCE_BETWEEN_ITEMS, tb_load.getY());
        tb_turnMirror.setBorder(false);
        tb_turnMirror.addActionListener(ControlPainting.getInstance());
        initializeTextButton(tb_turnMirror,
                "Spiegelung",
                Constants.VIEW_TB_DOWN_PATH, 0);
        tb_turnMirror.setActivable(false);

        tb_turnNormal = new Item1Button(null);
        tb_turnNormal.setSize(tb_turnMirror.getWidth(),
                tb_turnMirror.getHeight());
        tb_turnNormal.setLocation(tb_turnMirror.getX(), tb_zoomOut.getY());
        tb_turnNormal.setBorder(false);
        tb_turnNormal.addActionListener(ControlPainting.getInstance());
        initializeTextButton(tb_turnNormal,
                "Spiegelung normal",
                Constants.VIEW_TB_UP_PATH, 0);
        tb_turnNormal.setActivable(false);

		insertTrennung(tb_turnMirror.getWidth() + tb_turnMirror.getX() 
		        + ViewSettings.DISTANCE_BEFORE_LINE, tb_turnMirror.getY(), 
		        2 + 2 + 1, _paint);
		insertInformation("Dateioperationen", jlbl_separation[2 + 2].getX(), 
		        jlbl_separation[2 + 2 + 1].getX(), 2 + 2 + 1, _paint);


        return jlbl_separation[2 + 2 + 1].getX() 
                + ViewSettings.DISTANCE_AFTER_LINE;
	}
	
	
	
	/**
	 * add pens .
	 */
	private void addPens() {

        sa_br1 = new Item1PenSelection(
                "Bleistift rund", "pen/bleiMaths.png", 1);
        sa_bn1 = new Item1PenSelection(
                "Bleistift Normal", "pen/bleiNormal.png", 1);
        sa_bp1 = new Item1PenSelection(
                "Bleistift Punkte", "pen/bleiPoint.png", 1);
    
        sa_fr1 = (new Item1PenSelection(
                "Fueller rund", "pen/fuellerMaths.png", 1));
        sa_fn1 = (new Item1PenSelection(
                "Fueller Normal", "pen/fuellerNormal.png", 1));
        sa_fp1 = (new Item1PenSelection(
                "Fueller Punkte", "pen/fuellerPoint.png", 1));
    
        sa_kr1 = (new Item1PenSelection(
                "Kuli rund", "pen/kuliMaths.png", 1));
        sa_kn1 = (new Item1PenSelection(
                "Kuli Normal", "pen/kuliNormal.png", 1));
        sa_kp1 = (new Item1PenSelection(
                "Kuli Punkte", "pen/kuliPoint.png", 1));
    
        sa_mr1 = (new Item1PenSelection(
                "Marker rund", "pen/markerMaths.png", 1));
        sa_mn1 = (new Item1PenSelection(
                "Marker Normal", "pen/markerNormal.png", 1));
        sa_mp1 = (new Item1PenSelection(
                "Marker Punkte", "pen/markerPoint.png", 1));
    
        sa_pr1 = (new Item1PenSelection(
                "Pinsel rund", "pen/pinselMaths.png", 1));
        sa_pn1 = (new Item1PenSelection(
                "Pinsel Normal", "pen/pinselNormal.png", 1));
        sa_pp1 = (new Item1PenSelection(
                "Pinsel Punkte", "pen/pinselPoint.png", 1));
    
        sa_br2 = (new Item1PenSelection(
                "Bleistift rund", "pen/bleiMaths.png", 2));
        sa_bn2 = (new Item1PenSelection(
                "Bleistift Normal", "pen/bleiNormal.png", 2));
        sa_bp2 = (new Item1PenSelection(
                "Bleistift Punkte", "pen/bleiPoint.png", 2));
    
        sa_fr2 = (new Item1PenSelection(
                "Fueller rund", "pen/fuellerMaths.png", 2));
        sa_fn2 = (new Item1PenSelection(
                "Fueller Normal", "pen/fuellerNormal.png", 2));
        sa_fp2 = (new Item1PenSelection(
                "Fueller Punkte", "pen/fuellerPoint.png", 2));
    
        sa_kr2 = (new Item1PenSelection(
                "Kuli rund", "pen/kuliMaths.png", 2));
        sa_kn2 = (new Item1PenSelection(
                "Kuli Normal", "pen/kuliNormal.png", 2));
        sa_kp2 = (new Item1PenSelection(
                "Kuli Punkte", "pen/kuliPoint.png", 2));
    
        sa_mr2 = (new Item1PenSelection(
                "Marker rund", "pen/markerMaths.png", 2));
        sa_mn2 = (new Item1PenSelection(
                "Marker Normal", "pen/markerNormal.png", 2));
        sa_mp2 = (new Item1PenSelection(
                "Marker Punkte", "pen/markerPoint.png", 2));
    
        sa_pr2 = (new Item1PenSelection(
                "Pinsel rund", "pen/pinselMaths.png", 2));
        sa_pn2 = (new Item1PenSelection(
                "Pinsel Normal", "pen/pinselNormal.png", 2));
        sa_pp2 = (new Item1PenSelection(
                "Pinsel Punkte", "pen/pinselPoint.png", 2));
        
        //add bleistift to both panels
        it_stift1.add(sa_br1);
        it_stift1.add(sa_bn1);
        it_stift1.add(sa_bp1);
    
        it_stift2.add(sa_br2);
        it_stift2.add(sa_bn2);
        it_stift2.add(sa_bp2);
    
        //add fueller to both panels
        it_stift1.add(sa_fr1);
        it_stift1.add(sa_fn1);
        it_stift1.add(sa_fp1);
    
        it_stift2.add(sa_fr2);
        it_stift2.add(sa_fn2);
        it_stift2.add(sa_fp2);
    
        //add kuli to both panels
        it_stift1.add(sa_kr1);
        it_stift1.add(sa_kn1);
        it_stift1.add(sa_kp1);
    
        it_stift2.add(sa_kr2);
        it_stift2.add(sa_kn2);
        it_stift2.add(sa_kp2);
    
        //add marker to both panels
        it_stift1.add(sa_mr1);
        it_stift1.add(sa_mn1);
        it_stift1.add(sa_mp1);
    
        it_stift2.add(sa_mr2);
        it_stift2.add(sa_mn2);
        it_stift2.add(sa_mp2);
    
        //add pinsel to both panels
        it_stift1.add(sa_pr1);
        it_stift1.add(sa_pn1);
        it_stift1.add(sa_pp1);
    
        it_stift2.add(sa_pr2);
        it_stift2.add(sa_pn2);
        it_stift2.add(sa_pp2);
        //save
    
	}

    
    /**
     * initialize a text button.
     * 
     * @param _tb the textButton
     * @param _text the text of the button
     * @param _path the path of the image
     * @param _insertIndex the index of the insertion array
     */
    private void initializeTextButton(
            final Item1Button _tb, final String _text, final String _path, 
            final int _insertIndex) {
        
       initializeTextButtonOhneAdd(_tb, _text, _path);
        super.add(_tb);
    }
    
    
    /**
     * initialize a text button.
     * 
     * @param _tb the textButton
     * @param _text the text of the button
     * @param _path the path of the image
     */
    public static void initializeTextButtonOhneAdd(
            final Item1Button _tb, 
            final String _text, final String _path) {
        
        //alter settings of TextButton
        _tb.setOpaque(true);
        _tb.setText(_text);
        _tb.addMouseListener(CPaintStatus.getInstance());
        _tb.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.black),
                new LineBorder(Color.white)));
        _tb.setActivable(true);
        _tb.setIcon(_path);
    }
	
	/**
	 * 
	 * @param _x the x coordinate in pX
	 * @param _y the y coordinate in pX
	 * @param _locInArray the location in array
	 * @param _add whether to add or not
	 */
	private void insertTrennung(final int _x, final int _y, 
	        final int _locInArray, final boolean _add) {
	    
	    //if new initialization is demanded
	    if (_add) {
	        this.jlbl_separation[_locInArray] = new JLabel();
	        this.jlbl_separation[_locInArray].setBorder(
	                BorderFactory.createLineBorder(
	                        ViewSettings.CLR_BACKGROUND_DARK_XX));
	        super.add(this.jlbl_separation[_locInArray]);
	        
	    }
	    final int twenty = 20;
	    final int number = tb_cut.getHeight() + tb_cut.getY() + twenty;
        this.jlbl_separation[_locInArray].setBounds(_x, _y, 1, number);
	}
	
	/**
	 * insert information text.
	 * @param _text the printed text
	 * @param _x1 first x coordinate (is between first and second coordinate)
	 * @param _x2 second x coordinate (is between first and second coordinate)
	 * @param _locationInArray the location in information array
	 * @param _insert whether to insert the item or just to change bounds
	 */
	private void insertInformation(final String _text, 
	        final int _x1, final int _x2, final int _locationInArray, 
	        final boolean _insert) {

		if (_insert) {

	        //final value for foreground for JLabel
	        final int rgb = 190;
	        
	        jlbl_information[_locationInArray] = new VLabel();
	        jlbl_information[_locationInArray].setFont(
	                ViewSettings.TP_FONT_INFORMATION);
	        jlbl_information[_locationInArray].setForeground(
	                new Color(rgb, rgb, rgb));
	        jlbl_information[_locationInArray].setHorizontalAlignment(
	                SwingConstants.CENTER);
	        jlbl_information[_locationInArray].setText(_text);
	        super.add(jlbl_information[_locationInArray]);
	        
		}

		if (Status.isNormalRotation()) {

		    final int distance = 5;
            final int number = tb_cut.getHeight() + tb_cut.getY() + distance;
            final int number2 = 15;
	        jlbl_information[_locationInArray].setBounds(
	                _x1, number, _x2 - _x1, number2);
		} else {
		    jlbl_information[_locationInArray].setOpaque(true);
            jlbl_information[_locationInArray].setBounds(-1, -1, -1, -1);
		}

	}
	
	
	/**
	 * flip.
	 */
	public void flip() {

	    int x = initializeClipboard(0, false);
        x = initializeHistory(x, false);
        x = initializePagePens(x, false);
//        x = initializePageColors(x, false);
//        x = initializeZoom(x, false);
//        x = initializeFileOperations(x, false);
	}

    
    /**
     * return the only instance of this class.
     * @return the only instance of this class.
     */
    public static Paint getInstance() {
      
        //if has not been initialized yet
        if (instance == null) {

            instance = new Paint();
        }
        
        //return the only instance
        return instance;
    }

	/**
	 * @return the sa_br1
	 */
	public Item1PenSelection getSa_br1() {
		return sa_br1;
	}


	/**
	 * @return the sa_br2
	 */
	public Item1PenSelection getSa_br2() {
		return sa_br2;
	}


	/**
	 * @return the sa_bn1
	 */
	public Item1PenSelection getSa_bn1() {
		return sa_bn1;
	}


	/**
	 * @return the sa_bn2
	 */
	public Item1PenSelection getSa_bn2() {
		return sa_bn2;
	}


	/**
	 * @return the sa_bp1
	 */
	public Item1PenSelection getSa_bp1() {
		return sa_bp1;
	}


	/**
	 * @return the sa_bp2
	 */
	public Item1PenSelection getSa_bp2() {
		return sa_bp2;
	}


	/**
	 * @return the sa_fr1
	 */
	public Item1PenSelection getSa_fr1() {
		return sa_fr1;
	}


	/**
	 * @return the sa_fr2
	 */
	public Item1PenSelection getSa_fr2() {
		return sa_fr2;
	}


	/**
	 * @return the sa_fn1
	 */
	public Item1PenSelection getSa_fn1() {
		return sa_fn1;
	}



	/**
	 * @return the sa_fn2
	 */
	public Item1PenSelection getSa_fn2() {
		return sa_fn2;
	}

	
	/**
	 * @return the sa_fp1
	 */
	public Item1PenSelection getSa_fp1() {
		return sa_fp1;
	}


	/**
	 * @return the sa_fp2
	 */
	public Item1PenSelection getSa_fp2() {
		return sa_fp2;
	}


	/**
	 * @return the sa_kr1
	 */
	public Item1PenSelection getSa_kr1() {
		return sa_kr1;
	}


	/**
	 * @return the sa_kr2
	 */
	public Item1PenSelection getSa_kr2() {
		return sa_kr2;
	}

	/**
	 * @return the sa_kn1
	 */
	public Item1PenSelection getSa_kn1() {
		return sa_kn1;
	}


	/**
	 * @return the sa_kn2
	 */
	public Item1PenSelection getSa_kn2() {
		return sa_kn2;
	}



	/**
	 * @return the sa_kp1
	 */
	public Item1PenSelection getSa_kp1() {
		return sa_kp1;
	}



	/**
	 * @return the sa_kp2
	 */
	public Item1PenSelection getSa_kp2() {
		return sa_kp2;
	}


	/**
	 * @return the sa_mr1
	 */
	public Item1PenSelection getSa_mr1() {
		return sa_mr1;
	}


	/**
	 * @return the sa_mr2
	 */
	public Item1PenSelection getSa_mr2() {
		return sa_mr2;
	}


	/**
	 * @return the sa_mn1
	 */
	public Item1PenSelection getSa_mn1() {
		return sa_mn1;
	}


	/**
	 * @return the sa_mn2
	 */
	public Item1PenSelection getSa_mn2() {
		return sa_mn2;
	}


	/**
	 * @return the sa_mp1
	 */
	public Item1PenSelection getSa_mp1() {
		return sa_mp1;
	}


	/**
	 * @return the sa_mp2
	 */
	public Item1PenSelection getSa_mp2() {
		return sa_mp2;
	}


	/**
	 * @return the sa_pr1
	 */
	public Item1PenSelection getSa_pr1() {
		return sa_pr1;
	}


	/**
	 * @return the sa_pr2
	 */
	public Item1PenSelection getSa_pr2() {
		return sa_pr2;
	}


	/**
	 * @return the sa_pn1
	 */
	public Item1PenSelection getSa_pn1() {
		return sa_pn1;
	}

	/**
	 * @return the sa_pn2
	 */
	public Item1PenSelection getSa_pn2() {
		return sa_pn2;
	}

	/**
	 * @return the sa_pp1
	 */
	public Item1PenSelection getSa_pp1() {
		return sa_pp1;
	}

	/**
	 * @return the sa_pp2
	 */
	public Item1PenSelection getSa_pp2() {
		return sa_pp2;
	}


	/**
	 * @return the it_stift1
	 */
	public Item1Menu getIt_stift1() {
		return it_stift1;
	}

	/**
	 * @return the it_stift2
	 */
	public Item1Menu getIt_stift2() {
		return it_stift2;
	}

	/**
	 * @return the jbtn_colors
	 */
	public JButton [] getJbtn_colors() {
		return jbtn_colors;
	}


	/**
	 * @return the jbtn_color2
	 */
	public Item1Button getJbtn_color2() {
		return tb_color2;
	}


	/**
	 * @return the jbtn_color1
	 */
	public Item1Button getJbtn_color1() {
		return tb_color1;
	}

    /**
     * @return the getTp_buttons
     */
    public Item1Button getTb_turnNormal() {
        return tb_turnNormal;
    }

    /**
     * @return the tb_turnMirror
     */
    public Item1Button getTb_turnMirror() {
        return tb_turnMirror;
    }

    /**
     * @param _it_stift1 the it_stift1 to set
     */
    public void setIt_stift1(final Item1Menu _it_stift1) {
        this.it_stift1 = _it_stift1;
    }

    /**
     * @return the it_selection
     */
    public Item1Menu getIt_selection() {
        return it_selection;
    }

    /**
     * @param _it_selection the it_selection to set
     */
    public void setIt_selection(final Item1Menu _it_selection) {
        this.it_selection = _it_selection;
    }

    /**
     * @return the tb_selectionMagic
     */
    public Item1Button getTb_selectionMagic() {
        return tb_selectionMagic;
    }

    /**
     * @param _tb_selectionMagic the tb_selectionMagic to set
     */
    public void setTb_selectionMagic(final Item1Button _tb_selectionMagic) {
        this.tb_selectionMagic = _tb_selectionMagic;
    }

    /**
     * @return the tb_selectionLine
     */
    public Item1Button getTb_selectionLine() {
        return tb_selectionLine;
    }

    /**
     * @return the tb_selectionCurve
     */
    public Item1Button getTb_selectionCurve() {
        return tb_selectionCurve;
    }

    /**
     * @return the tb_color1
     */
    public Item1Button getTb_color1() {
        return tb_color1;
    }

    /**
     * @return the tb_color2
     */
    public Item1Button getTb_color2() {
        return tb_color2;
    }

    /**
     * @return the tb_fill
     */
    public Item1Button getTb_fill() {
        return tb_fill;
    }

    /**
     * @return the tb_pipette
     */
    public Item1Button getTb_pipette() {
        return tb_pipette;
    }

    /**
     * @return the tb_zoomOut
     */
    public Item1Button getTb_zoomOut() {
        return tb_zoomOut;
    }

    /**
     * @return the tb_zoomIn
     */
    public Item1Button getTb_zoomIn() {
        return tb_zoomIn;
    }

    /**
     * @return the tb_new
     */
    public Item1Button getTb_new() {
        return tb_new;
    }

    /**
     * @return the tb_load
     */
    public Item1Button getTb_load() {
        return tb_load;
    }

    /**
     * @return the tb_save
     */
    public Item1Button getTb_save() {
        return tb_save;
    }

    /**
     * @return the tb_copy
     */
    public Item1Button getTb_copy() {
        return tb_copy;
    }

    /**
     * @return the tb_paste
     */
    public Item1Button getTb_paste() {
        return tb_paste;
    }

    /**
     * @return the tb_cut
     */
    public Item1Button getTb_cut() {
        return tb_cut;
    }

    /**
     * @return the it_color
     */
    public Item1Menu getIt_color() {
        return it_color;
    }

    /**
     * @return the tb_prev
     */
    public Item1Button getTb_prev() {
        return tb_prev;
    }


    /**
     * @return the tb_next
     */
    public Item1Button getTb_next() {
        return tb_next;
    }

    /**
     * @return the tb_move
     */
    public Item1Button getTb_move() {
        return tb_move;
    }

    /**
     * @return the tb_erase
     */
    public Item1Button getTb_erase() {
        return tb_erase;
    }

}
