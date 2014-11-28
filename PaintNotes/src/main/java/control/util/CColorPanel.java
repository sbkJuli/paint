//package declaration
package control.util;

//import declarations
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;

import model.settings.Error;
import view.util.VColorPanel;
import view.util.mega.MPanel;

/**
 * singleton class which handles the ActionEvents of ColorPanel.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public class CColorPanel implements KeyListener, MouseListener,
MouseMotionListener {
	
	/**
	 * the colorPanel which is changed.
	 */
	private VColorPanel cp;

	/**
	 * selected position in color array where to change.
	 */
	private int selectedPosition;
	
	/**
	 * set pressed.
	 */
	private boolean pressed = false;
	
	/**
	 * Constructor saves the ColorPanel.
	 * @param _cp the ColorPanel.
	 */
	public CColorPanel(final VColorPanel _cp) {
		
		//save ColorPanel
		this.cp = _cp;
		
		//initialize selectedPosition with standard value
		final int startSelectedPosition = 6;
		selectedPosition = startSelectedPosition;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public final void mouseMoved(final MouseEvent _event) {
		cp.repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	public final void mouseDragged(final MouseEvent _event) {
		cp.repaint();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public final void mouseReleased(final MouseEvent _event) {
		pressed = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void mousePressed(final MouseEvent _event) {
		
		//set pressed
		pressed = true;

		//update color
		updateColor(_event);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void mouseExited(final MouseEvent _event) {
		cp.repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	public final void mouseEntered(final MouseEvent _event) {

	    if (pressed) {
			//update color
			updateColor(_event);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public final void mouseClicked(final MouseEvent _event) {
			
		//apply changes and update the background of both the Button of 
		//the main view and those of the panel
		if (_event.getSource().equals(cp.getJbtn_applyCanges())) {
			
			//those of the view
			cp.getJbtn_colorsUpdate()[selectedPosition].setBackground(
					cp.getJlbl_selectedColor().getBackground());
			
			//those of the panel
			cp.getJbtn_colorsPanel()[selectedPosition].setBackground(
					cp.getJlbl_selectedColor().getBackground());
		} else {
			
			//update a not specific button
			for (int i = 0; i < cp.getJbtn_colorsUpdate().length; i++) {
				if (_event.getSource().equals(cp.getJbtn_colorsUpdate()[i])) {
					
					//update button
					cp.getJbtn_colorsUpdate()[selectedPosition].setBorder(
							BorderFactory.createCompoundBorder(
									new LineBorder(Color.black), 
									new LineBorder(Color.white)));
					cp.getJbtn_colorsUpdate()[i].setBorder(BorderFactory
							.createLineBorder(Color.red, 2));
					
					//update selected position
					selectedPosition = i;
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public final void keyTyped(final KeyEvent _event) { }

	/**
	 * {@inheritDoc}
	 */
	public final void keyReleased(final KeyEvent _event) { }
	
	/**
	 * {@inheritDoc}
	 */
	public final void keyPressed(final KeyEvent _event) {
		
		//fetch the strings out of JTextField
		String red = cp.getJtf_r().getText();
		String green = cp.getJtf_g().getText();
		String blue = cp.getJtf_b().getText();
		
		//add the current char
		if (_event.getSource().equals(cp.getJtf_r())) {
			red += "" + _event.getKeyChar();
		} else if (_event.getSource().equals(cp.getJtf_g())) {
			green += "" + _event.getKeyChar();
		} else if (_event.getSource().equals(cp.getJtf_b())) {
			blue += "" + _event.getKeyChar();
		} 
		
		try {

			//fetch integer values
			int r = Integer.parseInt(red);
			int g = Integer.parseInt(green);
			int b = Integer.parseInt(blue);
			
			//set the background of the selected color
			cp.getJlbl_selectedColor().setBackground(new Color(r, g, b));
		} catch (final Exception e) {
		    Error.printError("", "", "", null, Error.ERROR_MESSAGE_DO_NOTHING);
		}
	}

	/**
	 * update the selected color including the TextFields for r, g, b and
	 * the selected color.
	 * @param _event the MouseEvent.
	 */
	private void updateColor(final MouseEvent _event) {

		try {
			
			//fetch color 
			Color clr_background = ((MPanel) _event.getSource())
			        .getBackground();

			//update the displayed selected color
			cp.getJlbl_selectedColor().setBackground(clr_background);
			
			//update the text
			cp.getJtf_r().setText("" + clr_background.getRed());
			cp.getJtf_g().setText("" + clr_background.getGreen());
			cp.getJtf_b().setText("" + clr_background.getBlue());
		} catch (ClassCastException e) {
		    Error.printError(getClass().getSimpleName(), "updateColor(...)",
		            "Cast failed.", e, Error.ERROR_MESSAGE_DO_NOTHING);
		}
	}
	

	/**
	 * simple getter method.
	 * @return the selectedPosition
	 */
	public final int getSelectedPosition() {
		return selectedPosition;
	}
	
	


    /**
     * @return the pressed
     */
    public final boolean isPressed() {
        return pressed;
    }


    /**
     * @param _pressed the pressed to set
     */
    public final void setPressed(final boolean _pressed) {
        this.pressed = _pressed;
    }
}