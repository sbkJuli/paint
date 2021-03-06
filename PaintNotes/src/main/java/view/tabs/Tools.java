//package declaration
package view.tabs;


/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


//import declarations
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import model.objects.pen.Pen;
import model.settings.Constants;
import model.settings.State;
import model.settings.TextFactory;
import model.settings.ViewSettings;
import control.ControlPaint;
import control.forms.CPaintStatus;
import control.forms.tabs.CTabTools;
import control.interfaces.HelpMouseListener;
import control.interfaces.MenuListener;
import control.util.CPaintSelection;
import control.util.CPen;
import view.forms.Help;
import view.util.Item1Menu;
import view.util.Item1PenSelection;
import view.util.VColorPanel;
import view.util.Item1Button;
import view.util.mega.MButton;


/**
 * Paint tab.
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
@SuppressWarnings("serial")
public final class Tools extends Tab {


    /**
     * The menu - item for creating own colors.
     */
    private Item1Menu it_color;
    
	/**
	 * array of colors to change the first or the second color.
	 */
	private MButton [] jbtn_colors;

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
	private Item1Menu it_stift1, it_stift2, it_erase;
	
	/**
	 * buttons.
	 */
	private Item1Button tb_pipette, tb_fill, tb_move, 
	tb_selectionLine, tb_selectionMagic, tb_selectionCurve, tb_saveAs;

	
	/**
	 * Selection menu.
	 */
	private Item1Menu it_selection;
	
	
	/**
	 * Button for erasing (that are contained by the erase menu).
	 */
	private Item1Button tb_eraseAll, tb_eraseDestroy, tb_insertImage;
	
	/**
	 * constants.
	 */
    private final int fifety = 50;
    
	/**
	 * Constructor of Paint.
	 */
	public Tools(final ControlPaint _controlPaint,
			final CTabTools _paint, final MenuListener _ml,
			final CPaintStatus _controlPaintStatus) {

		//initialize JPanel and alter settings
		super((2 + 1) * 2);
		super.setOpaque(false);
		super.setLayout(null);
		
		
		int x = initializeClipboard(0, true, _paint, _controlPaintStatus);
        x = initializeHistory(x, true, _paint, _controlPaintStatus);
		x = initializePagePens(_controlPaint, x, true, _paint, _ml, 
				_controlPaintStatus);
        x = initializePageColors(x, true, _paint, _ml, _controlPaintStatus);
		x = initializeZoom(x, true, _paint, _controlPaintStatus);
        x = initializeFileOperations(x, true, _paint, _controlPaintStatus);

        //disable icons which functionality is not implemented yet.
//        tb_prev.disable();
//        tb_next.disable();
        tb_fill.disable();
        tb_selectionMagic.disable();

        //activate first color and set colors.
        tb_color1.setActivated(true);
		tb_color1.setBackground(State.getPenSelected1().getClr_foreground());
		tb_color2.setBackground(State.getPenSelected2().getClr_foreground());

//		//set size
//		super.setSize((int) Toolkit.getDefaultToolkit().getScreenSize()
//		        .getWidth(), ViewSettings.getView_heightTab());

		//TODO: directly apply to status values.
		//set standard values (is going to be put into a new settings class)
		it_stift1.setIcon(State.getPenSelected1().getIconPath());
		it_stift2.setIcon(State.getPenSelected2().getIconPath());
	}
	

	/**
	 * Apply size for the entire panel and its contents.
	 */
	public void applySize() {
		super.applySize();
		int x = initializeClipboard(0, false, null, null);
        x = initializeHistory(x, false, null, null);
		x = initializePagePens(null, x, false, null, null, null);
        x = initializePageColors(x, false, null, null, null);
		x = initializeZoom(x, false, null, null);
        x = initializeFileOperations(x, false, null, null);

		it_stift1.setIcon(State.getPenSelected1().getIconPath());
		it_stift2.setIcon(State.getPenSelected2().getIconPath());
	}

	/**
	 * initialize buttons such as 
	 * paste, copy, cut, zoomIn, zoomOut, save.
	 * 
	 * @param _x the starting x coordinate
	 * @param _paint whether to paint or just to resize.
	 * @return the x coordinate for following items.
	 */
	private int initializeClipboard(final int _x, final boolean _paint,
			final CTabTools _cp,
			final CPaintStatus _controlPaintStatus) {

        if (_paint) {

            tb_paste = new Item1Button(tb_paste);
            tb_copy = new Item1Button(tb_copy);
            tb_cut = new Item1Button(tb_cut);
        }
	    

        tb_cut.setSize(ViewSettings.getItemWidth(), 
                ViewSettings.getItemHeight());
        tb_paste.setSize(ViewSettings.getItemWidth(), 
                ViewSettings.getItemHeight());
        tb_copy.setSize(ViewSettings.getItemWidth(), 
                ViewSettings.getItemHeight());
        
	    
	    if (_paint) {

	        //paste
	        tb_paste.setBorder(false);
	        tb_paste.addActionListener(_cp);
	        tb_paste.setActivable(false);

	        //copy
	        tb_copy.setBorder(false);
	        tb_copy.addActionListener(_cp);
	        tb_copy.setActivable(false);

	        //cut
	        tb_cut.setBorder(false);
	        tb_cut.addActionListener(_cp);
	        tb_cut.setActivable(false);

	    }
        initializeTextButton(tb_paste,
                TextFactory.getInstance().getTextViewTb_paste(),
                Constants.VIEW_TB_PASTE_PATH, 0, _controlPaintStatus, _paint);
        initializeTextButton(tb_copy,
                TextFactory.getInstance().getTextViewTb_copy(),
                Constants.VIEW_TB_COPY_PATH, 0, _controlPaintStatus, _paint);
        initializeTextButton(tb_cut,
                TextFactory.getInstance().getTextViewTb_cut(),
                Constants.VIEW_TB_CUT_PATH, 0, _controlPaintStatus, _paint);
	    
	    int xLocationSeparation;
	    

	        tb_paste.setLocation(_x + ViewSettings.getDistanceBetweenItems(),
	                ViewSettings.getDistanceBetweenItems());
	        tb_copy.setLocation(tb_paste.getX() 
	                + ViewSettings.getDistanceBetweenItems() 
	                + tb_paste.getWidth(),
	                ViewSettings.getDistanceBetweenItems());
	        tb_cut.setLocation(tb_copy.getX(), tb_copy.getHeight() 
	                + ViewSettings.getDistanceBetweenItems() + tb_copy.getY());

	        xLocationSeparation = tb_cut.getWidth() + tb_cut.getX() 
                    + ViewSettings.getDistanceBeforeLine();
	    

        insertSectionStuff("Zwischenablage", 0, xLocationSeparation, 
                0, _paint);
	       
        tb_paste.flip();
        tb_copy.flip();
        tb_cut.flip();
        
		return xLocationSeparation + ViewSettings.getDistanceAfterLine();
	}
	
	
	
	/**
	 * initialize the history buttons.
	 * 
	 * @param _x the x coordinate where to continue
	 * @param _paint whether to paint or not
	 * 
	 * @return the new x coordinate
	 */
	private int initializeHistory(final int _x, final boolean _paint,
			final CTabTools _ctp,
			final CPaintStatus _controlPaintStatus) {

	    if (_paint) {

            tb_prev = new Item1Button(null);
            tb_next = new Item1Button(null);
            tb_prev.addActionListener(_ctp);
            tb_next.addActionListener(_ctp);
            
            
	    }

        tb_prev.setSize(ViewSettings.getItemWidth(), 
                ViewSettings.getItemHeight());
        tb_next.setSize(ViewSettings.getItemWidth(), 
                ViewSettings.getItemHeight());
	    if (_paint) {

	        tb_prev.setBorder(false);

	        tb_next.setBorder(false);


	        tb_prev.setActivable(false);
	        tb_next.setActivable(false);
	    }

        initializeTextButton(tb_next,
                "next",
                Constants.VIEW_TB_NEXT_PATH, 0, _controlPaintStatus, _paint);

        initializeTextButton(tb_prev,
                "previous",
                Constants.VIEW_TB_PREV_PATH, 0, _controlPaintStatus, _paint);
            int xLocationSeparation;

                tb_prev.setLocation(_x, ViewSettings.getDistanceBetweenItems());


                tb_next.setLocation(

                        tb_prev.getX(), tb_prev.getY() + tb_prev.getHeight());


                xLocationSeparation = tb_prev.getWidth() + tb_prev.getX() 


                    + ViewSettings.getDistanceBeforeLine();





        insertSectionStuff("history", _x, xLocationSeparation, 1, 


                _paint);


        return xLocationSeparation + ViewSettings.getDistanceAfterLine();


	}


	/**
	 * initialize page pen buttons.
	 * 
     * @param _x the x coordinate where to continue
     * @param _paint whether to paint or not
     * 
     * @return the new x coordinate
	 */
	private int initializePagePens(
			final ControlPaint _controlPaint, 
			final int _x, final boolean _paint,
			final CTabTools _cp,
			final MenuListener _ml,
			final CPaintStatus _controlPaintStatus) {
        final Dimension sizeIT = new Dimension(550, 550);
        final Dimension sizeIT_selection = new Dimension(350, 280);
//      = new Dimension(350, 270);//for my laptop
        final Dimension sizeIT_erasae = new Dimension(tb_copy.getWidth() 
        		* 2, tb_copy.getHeight() * 2 + 5);

        final int sizeHeight = 110;
        if (_paint) {
            it_stift1 = new Item1Menu(false);
            it_stift1.removeScroll();
            it_stift1.setMenuListener(_ml);
            it_stift1.addMouseListener(_controlPaintStatus);

            it_erase = new Item1Menu(false);
            it_erase.setMenuListener(_ml);
            it_erase.removeScroll();
            it_erase.addMouseListener(_controlPaintStatus);
            
            tb_move = new Item1Button(null);
            tb_fill = new Item1Button(null);
            tb_pipette = new Item1Button(null);
            tb_selectionMagic = new Item1Button(null);
            tb_selectionCurve = new Item1Button(null);
            tb_selectionLine = new Item1Button(null);
            
            it_selection = new Item1Menu(false);
            it_selection.addMouseListener(_controlPaintStatus);
            it_selection.setMenuListener(_ml);
            it_stift2 = new Item1Menu(false);
            it_stift2.setMenuListener(_ml);
            it_stift2.removeScroll();
            it_stift2.addMouseListener(_controlPaintStatus);
        }
        it_stift1.flip();
        it_stift2.flip();
        it_selection.flip();
        it_stift1.setSize(sizeIT);
        it_stift1.setSizeHeight(fifety);
        it_stift2.setSize(sizeIT);
        it_stift2.setSizeHeight(fifety);
        it_selection.setSize(sizeIT_selection);
        it_selection.setSizeHeight(sizeHeight);

        it_erase.flip();
        it_erase.changeClosedSizes(tb_copy.getWidth(), tb_copy.getHeight());
        it_erase.setIcon(Constants.VIEW_TB_PIPETTE_PATH);
        it_erase.setSize(sizeIT_erasae);
        it_erase.setSizeHeight(tb_copy.getWidth());
        
        tb_move.setSize(tb_copy.getWidth(), tb_copy.getHeight());
        tb_fill.setSize(tb_copy.getWidth(), tb_copy.getHeight());
        tb_pipette.setSize(tb_copy.getWidth(), tb_copy.getHeight());
        tb_selectionLine.setSize(tb_selectionLine.getWidth(), 
                tb_selectionLine.getHeight());
        tb_selectionCurve.setSize(tb_selectionCurve.getWidth(), 
                tb_selectionCurve.getHeight());
        tb_selectionMagic.setSize(tb_selectionMagic.getWidth(), 
                tb_selectionMagic.getHeight());
        if (_paint) {
        	it_stift1.setBorder(null);
        	it_stift1.setText("Stift 1");
            it_stift1.setItemsInRow((byte) 1);
            it_stift1.setActivable();
        	it_stift1.setBorder(false);
        	super.add(it_stift1);

        	it_stift2.setText("Stift 2");
        	it_stift2.setActivable();
        	it_stift2.setItemsInRow((byte) 1);
        	it_stift2.setBorder(false);
        	super.add(it_stift2);
        	addPens(_controlPaint, _cp, _controlPaintStatus);

            it_selection.setText("Auswahl");
            it_selection.setBorder(false);
            it_selection.setActivable();
            it_selection.setItemsInRow((byte) (2 + 1));
            it_selection.removeScroll();

            tb_selectionLine.setBorder(false);
            it_selection.add(tb_selectionLine);
            tb_selectionLine.setOpaque(false);

            tb_selectionCurve.setBorder(false);
            it_selection.add(tb_selectionCurve);
            tb_selectionCurve.setOpaque(false);

            tb_selectionMagic.setBorder(false);
            it_selection.add(tb_selectionMagic);
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
            
            //initialize MouesListiener
            new CPaintSelection(
                    jcb_whole, jcb_separated,
                    tb_selectionLine, tb_selectionCurve, tb_selectionMagic);
            it_selection.setBorder(false);
            super.add(it_selection);

            tb_pipette.setBorder(false);
            tb_pipette.setActivable(true);

            tb_fill.setActivable(true);
            tb_fill.setBorder(false);
            tb_fill.setActivable(true);

            tb_move.setBorder(false);
            tb_move.setActivable(true);
    

            it_erase.setBorder(null);
            it_erase.setText("Erase");
            it_erase.setItemsInRow((byte) 2);
            it_erase.setActivable();
            it_erase.setBorder(false);
        	super.add(it_erase);
        	

            tb_eraseAll = new Item1Button(null);
            tb_eraseAll.addActionListener(_cp);
            tb_eraseAll.setSize(tb_copy.getWidth(), 
            		tb_copy.getHeight());
            tb_eraseAll.setBorder(false);
            it_erase.add(tb_eraseAll);
            tb_eraseAll.setActivable(true);
            tb_eraseAll.setOpaque(false);
            
            tb_eraseDestroy = new Item1Button(null);
            tb_eraseDestroy.addActionListener(_cp);
            tb_eraseDestroy.setSize(tb_copy.getWidth(), 
            		tb_copy.getHeight());
            tb_eraseDestroy.setBorder(false);
            it_erase.add(tb_eraseDestroy);
            tb_eraseDestroy.setActivable(true);
            tb_eraseDestroy.setOpaque(false);

            //Constants.VIEW_TB_PIPETTE_PATH
        }
        it_selection.setIcon(Constants.VIEW_TB_SELECT_LINE_PATH);
        initializeTextButtonOhneAdd(tb_selectionLine,
                "line", Constants.VIEW_TB_SELECT_LINE_PATH, 
                _controlPaintStatus);
        initializeTextButtonOhneAdd(tb_selectionCurve, "curve",
                Constants.VIEW_TB_SELECT_CURVE_PATH, _controlPaintStatus);
        initializeTextButtonOhneAdd(tb_selectionMagic, "magic",
                Constants.VIEW_TB_SELECT_MAGIC_PATH, _controlPaintStatus);
        initializeTextButtonOhneAdd(tb_eraseAll,
                "entire PO", Constants.VIEW_TB_PIPETTE_PATH,
                _controlPaintStatus);
        initializeTextButtonOhneAdd(tb_eraseDestroy,
                "destroy", Constants.VIEW_TB_PIPETTE_PATH, _controlPaintStatus);
        initializeTextButton(tb_pipette, "pipette",
                Constants.VIEW_TB_PIPETTE_PATH, 0, _controlPaintStatus, _paint);
        initializeTextButton(tb_fill, "fuellen",
                Constants.VIEW_TB_FILL_PATH, 0, _controlPaintStatus, _paint);
        initializeTextButton(tb_move, "nothing",
                Constants.VIEW_TB_MOVE_PATH, 0, _controlPaintStatus, _paint);
        

            it_stift1.setLocation(_x, ViewSettings.getDistanceBetweenItems());
            it_stift2.setLocation(it_stift1.getX() + it_stift1.getWidth() 
                    + ViewSettings.getDistanceBetweenItems(),
                    ViewSettings.getDistanceBetweenItems());
            it_selection.setLocation(it_stift2.getWidth() + it_stift2.getX() 
                    + ViewSettings.getDistanceBetweenItems(), 
                    ViewSettings.getDistanceBetweenItems());
            tb_pipette.setLocation(it_selection.getX() + it_selection.getWidth()
                    + ViewSettings.getDistanceBetweenItems(),
                    it_stift2.getY());
            tb_fill.setLocation(tb_pipette.getX(),
                    tb_pipette.getY() + tb_pipette.getHeight() 
                    + ViewSettings.getDistanceBetweenItems());
            
            tb_move.setLocation(tb_pipette.getX() + tb_pipette.getWidth()
                    + ViewSettings.getDistanceBetweenItems(),
                    tb_pipette.getY());
            it_erase.setLocation(tb_move.getX(),
                    tb_move.getY() + tb_move.getHeight() 
                    + ViewSettings.getDistanceBetweenItems());
        int xLocationSeparation = tb_move.getWidth() + tb_move.getX() 
                + ViewSettings.getDistanceBeforeLine();
        insertSectionStuff("Stifte", _x, xLocationSeparation, 2, _paint);
    	return xLocationSeparation + ViewSettings.getDistanceAfterLine();
    }

	/**
	 * initialize the colors of the page.
	 * @param _x the x coordinate
	 * @param _paint whether to paint
	 * @return the new position.
	 */
    private int initializePageColors(final int _x, final boolean _paint, 
    		final CTabTools _cPaint,
    		final MenuListener _ml,
    		final CPaintStatus _controlPaintStatus) {
    	
    	
    	if (_paint) {
    		//the first color for the first pen
        	tb_color1 = new Item1Button(null);
        	tb_color1.setOpaque(true);
        	tb_color1.addMouseListener(_controlPaintStatus);
        	tb_color1.setBorder(BorderFactory.createCompoundBorder(
        	        new LineBorder(Color.black), new LineBorder(Color.white)));

        	tb_color1.setText("Farbe 1");
        	super.add(tb_color1);
        	

        	//the second color for the second pen
        	tb_color2 = new Item1Button(null);
        	tb_color2.setOpaque(true);
        	tb_color2.addMouseListener(_controlPaintStatus);
        	tb_color2.setBorder(BorderFactory.createCompoundBorder(
        	        new LineBorder(Color.black), new LineBorder(Color.white)));
        	tb_color2.setText("Farbe 2");
        	super.add(tb_color2);
    	}
    	
    	tb_color1.setLocation(_x, ViewSettings.getDistanceBetweenItems());
    	tb_color1.setSize(ViewSettings.getItemMenu1Width(), 
    	        ViewSettings.getItemMenu1Height());
    	tb_color2.setLocation(tb_color1.getWidth() + tb_color1.getX() 
    	        + 2 + 2 + 2 + 1, ViewSettings.getDistanceBetweenItems());
    	tb_color2.setSize(tb_color1.getWidth(), tb_color1.getHeight());
    
    	final int distanceBetweenColors = 2;
    	final int width = (2 + 2 + 1) * (2 + 2 + 1) - 2 - 2;
    	final int amountOfItems = 4;
    	final int height = ViewSettings.getItemMenu1Height() / amountOfItems
    	        -  distanceBetweenColors;
    	final int anzInR = 7;
    	
    	if (_paint) {
    		jbtn_colors = new MButton[anzInR * (2 + 2)];

    	}
    	
    	for (int i = 0; i < jbtn_colors.length; i++) {
    		
    		if (_paint) {
    			jbtn_colors[i] = new MButton();
    		}
    		
    		jbtn_colors[i].setBounds(tb_color2.getX() + tb_color2.getWidth() 
    		        + distanceBetweenColors 
    		        + (i % anzInR) * (width + distanceBetweenColors),
    		        distanceBetweenColors + (i / anzInR) 
    		        * (height + distanceBetweenColors), width, height);
    		
    		if (_paint) {

        		jbtn_colors[i].setOpaque(true);
        		jbtn_colors[i].addMouseListener(
        		        _controlPaintStatus);
        		jbtn_colors[i].addMouseListener(_cPaint);
        		jbtn_colors[i].setBorder(BorderFactory.createCompoundBorder(
        		        new LineBorder(Color.black),
        		        new LineBorder(Color.white)));
        		super.add(jbtn_colors[i]);
        	}
    	}
    	
    	if (_paint) {
    	
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

	    	it_color = new Item1Menu(true);
	    	it_color.removeScroll();
	    	it_color.setMenuListener(_ml);
	    	it_color.addMouseListener(_controlPaintStatus);
	    	it_color.setBorder(false);
	    	it_color.setText("+ Farben");
	        it_color.getMPanel().add(new VColorPanel(jbtn_colors, _ml,
	        		_controlPaintStatus));
	        it_color.setBorder(false);
	        super.add(it_color);
    	}
    	
    	//
        it_color.setSize(new Dimension(ViewSettings.getSIZE_PNL_CLR().width 
        		+ 20,
        		ViewSettings.getSIZE_PNL_CLR().height));
    	it_color.setLocation(jbtn_colors[jbtn_colors.length - 1].getX() 
    	        + ViewSettings.getDistanceBetweenItems() 
    	        + jbtn_colors[jbtn_colors.length - 1].getWidth(), 
    	        ViewSettings.getDistanceBetweenItems());
    	

        it_color.setIcon(Constants.VIEW_TAB_PAINT_PALETTE);
        
        int xLocationSeparation = it_color.getWidth() + it_color.getX() 
                + ViewSettings.getDistanceBeforeLine();
    	insertSectionStuff("Farben", _x, xLocationSeparation, 2 + 1, _paint);
    	return xLocationSeparation + ViewSettings.getDistanceAfterLine();
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
	private int initializeZoom(final int _x, final boolean _paint, 
			final CTabTools _controlTabPaint,
			final CPaintStatus _controlPaintStatus) {

		
		if (_paint) {

			tb_zoomIn = new Item1Button(null);
			tb_zoomIn.setBorder(false);
			tb_zoomIn.setActivable(true);
			

			tb_zoomOut = new Item1Button(tb_zoomOut);
			tb_zoomOut.addActionListener(_controlTabPaint);
			tb_zoomOut.setBorder(false);

			tb_zoomOut.setActivable(false);
		}
		//zoom in
		tb_zoomIn.setSize(tb_copy.getWidth(), tb_copy.getHeight());
		tb_zoomIn.setLocation(tb_paste.getX(), 
		        tb_paste.getHeight() + 2 + 2 + 2 + 1);
		tb_zoomIn.setLocation(_x , ViewSettings.getDistanceBetweenItems());

		//zoom out
		tb_zoomOut.setSize(tb_copy.getWidth(), tb_copy.getHeight());
		tb_zoomOut.setLocation(tb_zoomIn.getX(),
				tb_zoomIn.getY() + ViewSettings.getDistanceBetweenItems() 
				+ tb_zoomIn.getHeight());


			initializeTextButton(tb_zoomIn,
					TextFactory.getInstance().getTextViewTb_zoomIn(),
					Constants.VIEW_TB_ZOOM_IN_PATH, 0, _controlPaintStatus, 
					_paint);
			initializeTextButton(tb_zoomOut,
					TextFactory.getInstance().getTextViewTb_zoomOut(),
					Constants.VIEW_TB_ZOOM_OUT_PATH, 0, _controlPaintStatus, 
					_paint);
		
		int xLocationSeparation = tb_zoomIn.getWidth() + tb_zoomIn.getX() 
                + ViewSettings.getDistanceBeforeLine();
		insertSectionStuff("Zoom", _x, xLocationSeparation, 
		        2 + 2, _paint);

        return xLocationSeparation + ViewSettings.getDistanceAfterLine();
	}
	
	/**
	 * 
     * @param _x the x coordinate where to continue
     * @param _controlTabPaint whether to paint or not
     * 
     * @return the new x coordinate
	 */
	private int initializeFileOperations(final int _x, final boolean _paint,
			final CTabTools _controlTabPaint, 
			final CPaintStatus _controlPaintStatus) {

		
		if (_paint) {

	        //save
	        tb_save = new Item1Button(tb_save);
	        tb_save.setBorder(false);
	        tb_save.addActionListener(_controlTabPaint);
	        tb_save.setActivable(false);

	        //save as
	        tb_saveAs = new Item1Button(tb_saveAs);
	        tb_saveAs.setBorder(false);
	        tb_saveAs.addActionListener(_controlTabPaint);
	        tb_saveAs.setActivable(false);
	        
	        tb_load = new Item1Button(tb_load);
			tb_load.setBorder(false);
			tb_load.addActionListener(_controlTabPaint);
			tb_load.setActivable(false);

	        tb_new = new Item1Button(null);
	        tb_new.setBorder(false);
	        tb_new.addActionListener(_controlTabPaint);
	        tb_new.setActivable(false);

	        //cut
	        tb_turnMirror = new Item1Button(null);
	        tb_turnMirror.setBorder(false);
	        tb_turnMirror.addActionListener(_controlTabPaint);
	        tb_turnMirror.setActivable(false);

	        tb_turnNormal = new Item1Button(null);
	        tb_turnNormal.setBorder(false);
	        tb_turnNormal.addActionListener(_controlTabPaint);
	        tb_turnNormal.setActivable(false);

	        //cut
	        tb_insertImage = new Item1Button(null);
	        tb_insertImage.setBorder(false);
	        tb_insertImage.addActionListener(_controlTabPaint);
	        tb_insertImage.setActivable(false);
	        
		}
        tb_save.setSize(tb_copy.getWidth(), tb_copy.getHeight());
        tb_save.setLocation(_x + ViewSettings.getDistanceAfterLine(),
                tb_zoomIn.getY());
        tb_saveAs.setSize(tb_copy.getWidth(), tb_copy.getHeight());
        tb_saveAs.setLocation(tb_save.getX(),
                tb_save.getY() + tb_save.getHeight() 
                + ViewSettings.getDistanceBetweenItems());
        

        //save
		
		tb_load.setSize(tb_copy.getWidth(), tb_copy.getHeight());
		tb_load.setLocation(tb_save.getWidth() + tb_save.getX()
                + ViewSettings.getDistanceBetweenItems(), tb_save.getY());

        //cut
        tb_new.setSize(tb_copy.getWidth(), tb_copy.getHeight());
        tb_new.setLocation(tb_saveAs.getWidth() + tb_saveAs.getX()
                + ViewSettings.getDistanceBetweenItems(), tb_saveAs.getY());

        tb_turnMirror.setSize(tb_load.getWidth(), tb_load.getHeight());
        tb_turnMirror.setLocation(tb_load.getWidth() + tb_load.getX() 
                + ViewSettings.getDistanceBetweenItems(), tb_load.getY());

        tb_turnNormal.setSize(tb_turnMirror.getWidth(),
                tb_turnMirror.getHeight());
        tb_turnNormal.setLocation(tb_turnMirror.getX(), tb_zoomOut.getY());


        tb_insertImage.setSize(tb_turnMirror.getWidth(), tb_turnMirror.getHeight());
        tb_insertImage.setLocation(tb_turnMirror.getWidth() + tb_turnMirror.getX() 
                + ViewSettings.getDistanceBetweenItems(), tb_turnMirror.getY());
        
        
	        initializeTextButton(tb_save,
	                TextFactory.getInstance().getTextViewTb_save(),
	                Constants.VIEW_TB_SAVE_PATH, 0,
	                _controlPaintStatus, _paint);
	        initializeTextButton(tb_saveAs,
	                TextFactory.getInstance().getTextViewTb_save() + " as",
	                Constants.VIEW_TB_SAVE_PATH, 0,
	                _controlPaintStatus, _paint);
			initializeTextButton(tb_load,
					"load",
					Constants.VIEW_TB_LOAD_PATH, 0,
					_controlPaintStatus, _paint);
	        initializeTextButton(tb_new,
	                "new",
	                Constants.VIEW_TB_NEW_PATH, 0,
	                _controlPaintStatus, _paint);
	        initializeTextButton(tb_turnMirror,
	                "Spiegelung",
	                Constants.VIEW_TB_DOWN_PATH, 0,
	                _controlPaintStatus, _paint);
	        initializeTextButton(tb_turnNormal,
	                "Spiegelung normal",
	                Constants.VIEW_TB_UP_PATH, 0,
	                _controlPaintStatus, _paint);
	        initializeTextButton(tb_insertImage,
	                "Bild einfügen",
	                Constants.VIEW_TB_FILL_PATH, 0,
	                _controlPaintStatus, _paint);

	        if (_paint) {

	            tb_insertImage.setActivable(false);
	        }

        int xLocationSeparation = tb_insertImage.getWidth() 
                + tb_insertImage.getX() 
                + ViewSettings.getDistanceBeforeLine();
		insertSectionStuff("Dateioperationen", _x, xLocationSeparation,
		        2 + 2 + 1, _paint);


        return xLocationSeparation + ViewSettings.getDistanceAfterLine();
	}
	
	
	
	/**
	 * add pens .
	 */
	private void addPens(final ControlPaint _controlPaint, 
			final CTabTools _cp,
			final CPaintStatus _controlPaintStatus) {


		
		for (Pen pen_available : State.getPen_available()) {
			
			/*
			 * The pen which is inserted into the first penMenu
			 */
			Item1PenSelection i1 = new Item1PenSelection(
					pen_available.getName(),
					pen_available.getIconPath(),
					pen_available,
					1);
			
			//mouse listener and changeListener
			i1.addMouseListener(_cp);
			CPen cpen = new CPen(_controlPaint, _cp, i1, it_stift1, 
					Pen.clonePen(pen_available), 1, _controlPaintStatus);
			i1.addChangeListener(cpen);
			i1.addMouseListener(cpen);

			//add to first pen
	        it_stift1.add(i1);
	        

			/*
			 * The pen which is inserted into the second penMenu
			 */
			Item1PenSelection i2 = new Item1PenSelection(
					pen_available.getName(),
					pen_available.getIconPath(),
					pen_available,
					2);
			
			//mouse listener and changeListener
			i2.addMouseListener(_cp);
			CPen cpen2 = new CPen(_controlPaint, _cp, i2, it_stift2,
					Pen.clonePen(pen_available), 2, _controlPaintStatus);
			i2.addChangeListener(cpen2);
			i2.addMouseListener(cpen2);

			//add to first pen
	        it_stift2.add(i2);
		}
    
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
            final int _insertIndex,
            final CPaintStatus _controlPaintStatus,
            final boolean _paint) {
        
    	if (_paint) {

    	       initializeTextButtonOhneAdd(_tb, _text, _path, 
    	    		   _controlPaintStatus);
    	        super.add(_tb);
    	} else {

 	       initializeTextButtonOhneAdd(_tb, _text, _path, null);
    	}
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
            final String _text, final String _path,
            final CPaintStatus _controlPaintStatus) {
        
        //alter settings of TextButton
        _tb.setOpaque(true);
        _tb.setText(_text);
        _tb.addMouseListener(_controlPaintStatus);
        _tb.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.black),
                new LineBorder(Color.white)));
        _tb.setActivable(true);
        _tb.setIcon(_path);
    }
	

    
    /**
     * {@inheritDoc}
     */
    public void initializeHelpListeners(
    		final JFrame _jf,
    		final Help _help) {
    	
    	//clipboard
        tb_copy.addMouseListener(new HelpMouseListener(
        		"Hiermit wird die aktuelle Markierung sowohl in eine interne,"
        		+ " als auch in die systemweite Zwischenablage kopiert.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_copy, 
        		null));

        tb_paste.addMouseListener(new HelpMouseListener(
        		"Hiermit wird die aktuelle programm-interne (falls "
        		+ "leer: die systemweite) Zwischenablage"
        		+ " in das Bild eingefügt.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_paste, 
        		null));

        tb_cut.addMouseListener(new HelpMouseListener(
        		"Schneidet die aktuelle Markierung aus.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_cut, 
        		null));
        
        //history
        tb_prev.addMouseListener(new HelpMouseListener(
        		"Macht letzte Aktion rückgängig.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_prev, 
        		null));

        tb_next.addMouseListener(new HelpMouseListener(
        		"Stellt den letzten rückgängig gemachten Schritt wieder her.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_next, 
        		null));
        
        //pens and colors
        it_stift1.addMouseListener(new HelpMouseListener(
        		"Stift - Auswahl nummer 1.\n\n"
        		+ "Durch Klick mit der rechten Maustaste auf das Bild kann die "
        		+ " Stiftreihenfolge schnell geändert werden.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		it_stift1, 
        		null));

        it_stift2.addMouseListener(new HelpMouseListener(
        		"Stift - Auswahl nummer 2.\n\n"
        		+ "Durch Klick mit der rechten Maustaste auf das Bild kann die "
        		+ " Stiftreihenfolge schnell geändert werden.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		it_stift2, 
        		null));

        it_selection.addMouseListener(new HelpMouseListener(
        		"Durch Klick auf den Pfeil kann entschieden werden, wie "
        		+ "das Auswahlwerkzeug aussehen soll:\n"
        		+ "(1) Rechteckauswahl\n"
        		+ "(2) Freihandauswahl\n"
        		+ "(3) Farbauswahl\n    (nicht implementiert)\n"
        		+ "\nund wie die einzelnen "
        		+ "Objekte ausgewählt werden sollen:\n"
        		+ "(A) gesamtes Zeichen-objekt\n"
        		+ "(B) Zeichen-Objekt aufteilen und nur die "
        		+ "Markierung auswählen\n\n"
        		+ "Standard: (1), (A)",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		it_selection, 
        		null));


        tb_pipette.addMouseListener(new HelpMouseListener(
        		"Mithilfe der Pipette kann die Farbe von bereits Gezeichnetem"
        		+ " wieder aufgegriffen und wiederverwendet werden.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_pipette, 
        		null));

        tb_fill.addMouseListener(new HelpMouseListener(
        		"Farbfläche füllen: noch nicht implementiert!",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_fill, 
        		null));
        tb_move.addMouseListener(new HelpMouseListener(
        		"Zum durch das Bild scrollen",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_move, 
        		null));

        it_erase.addMouseListener(new HelpMouseListener(
        		"Durch Klick auf den Pfeil kann entschieden werden, wie "
        		+ "die einzelnen Objekte gelöscht werden sollen:\n"
        		+ "(A) gesamtes Zeichen-objekt\n"
        		+ "(B) Zeichen-Objekt aufteilen und nur teilweise löschen\n\n"
        		+ "Standard: (A)",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		it_erase, 
        		null));
        
        
        // zoom 

        tb_zoomIn.addMouseListener(new HelpMouseListener(
        		"Bildausschnitt vergrößern.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_zoomIn, 
        		null));

        tb_zoomOut.addMouseListener(new HelpMouseListener(
        		"Bildausschnitt verkleinern.",
        		HelpMouseListener.HELP_ID_LOW,
        		_jf, _help, 
        		tb_zoomOut, 
        		null));

        //save load

        tb_save.addMouseListener(new HelpMouseListener(
        		"Speichert das aktuelle Bild. "
        		+ "Wenn noch kein Speicherpfad angegeben wurde, öffnet sich"
        		+ " ein Dateibrowser. Andernfalls wird die Datei unter dem "
        		+ "zuletzt angegebenen Pfad gespeichert.\n\n"
        		+ "Die Speichereinstellungen können im Tab \"Export\""
        		+ " geändert werden."
        		+ " Gespeichert wird je einmal im angegebenen Dateiformat"
        		+ " und im eigenen Vektorgrafikformat [PIC].",
        		HelpMouseListener.HELP_ID_ALWAYS,
        		_jf, _help, 
        		tb_save, 
        		null));
        tb_saveAs.addMouseListener(new HelpMouseListener(
        		"Speichern unter.\n\n"
        		+ "Die Speichereinstellungen können im Tab \"Export\""
        		+ " geändert werden."
        		+ " Gespeichert wird je einmal im angegebenen Dateiformat"
        		+ " und im eigenen Vektorgrafikformat [PIC].",
        		HelpMouseListener.HELP_ID_ALWAYS,
        		_jf, _help, 
        		tb_saveAs, 
        		null));

        tb_load.addMouseListener(new HelpMouseListener(
        		"Bild Laden.",
        		HelpMouseListener.HELP_ID_ALWAYS,
        		_jf, _help, 
        		tb_load, 
        		null));
        tb_new.addMouseListener(new HelpMouseListener(
        		"Öffnet eine Form um ein neues Bild zu erstellen."
        		+ " Größeninformationen können angegeben werden.",
        		HelpMouseListener.HELP_ID_ALWAYS,
        		_jf, _help, 
        		tb_new, 
        		null));
        

        tb_turnMirror.addMouseListener(new HelpMouseListener(
        		"Bildschirm invertieren um besser zeichnen zu können.\n\n"
        		+ "Empfohlen wird dazu die Installation von xrandr:\n"
        		+ "\tsudo apt-get install libwebkitgtk-1.0-0.",
        		HelpMouseListener.HELP_ID_ALWAYS,
        		_jf, _help, 
        		tb_turnMirror, 
        		null));
        tb_turnNormal.addMouseListener(new HelpMouseListener(
        		"Bildschirmorientierung nach Invertierung wiederherstellen.\n\n"
        		+ "Empfohlen wird dazu die Installation von xrandr:\n"
        		+ "sudo apt-get install libwebkitgtk-1.0-0.",
        		HelpMouseListener.HELP_ID_ALWAYS,
        		_jf, _help, 
        		tb_turnNormal, 
        		null));
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
	public MButton [] getJbtn_colors() {
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
     * @return the tb_save
     */
    public Item1Button getTb_saveAs() {
        return tb_saveAs;
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
    public Item1Menu getTb_erase() {
        return it_erase;
    }

	/**
	 * @return the tb_eraseAll
	 */
	public Item1Button getTb_eraseAll() {
		return tb_eraseAll;
	}

	/**
	 * @return the tb_eraseDestroy
	 */
	public Item1Button getTb_eraseDestroy() {
		return tb_eraseDestroy;
	}


	/**
	 * @return the tb_insertImage
	 */
	public Item1Button getTb_insertImage() {
		return tb_insertImage;
	}


	/**
	 * @param tb_insertImage the tb_insertImage to set
	 */
	public void setTb_insertImage(Item1Button tb_insertImage) {
		this.tb_insertImage = tb_insertImage;
	}

}
