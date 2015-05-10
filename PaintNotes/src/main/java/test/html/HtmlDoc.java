package test.html;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTMLDocument;


/**
 * The HtmlDocument.
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public class HtmlDoc {
	
	
	/**
	 * The HTML document.
	 */
	private HTMLDocument hl;
	
	
	/**
	 * Constructor: save the HTML document.
	 * @param _hl 	the HTML document.
	 */
	public HtmlDoc(final HTMLDocument  _hl) {
		this.hl = _hl;
	}

	
	
	
	/**
	 * Insert HTML code into document.
	 * @param _offset	the offset
	 * @param _htmlText	the HTML text that is going to be inserted.
	 * @throws BadLocationException	exception
	 * @throws IOException exception
	 */
	public final void insertHTML(final int _offset, final String _htmlText) 
			throws BadLocationException, IOException {
	    if (hl.getParser() == null) {
	        throw new IllegalStateException("No HTMLEditorKit.Parser");
	    }
	    Element elem = getCurrentElement(_offset);

	    //the method insertHTML is not visible
	    try {
	        Method insertHTML = javax.swing.text.html.HTMLDocument.class
	        		.getDeclaredMethod("insertHTML",
	                new Class[] {Element.class, int.class, 
	        				String.class, boolean.class});
	        insertHTML.setAccessible(true);
	        insertHTML.invoke(hl, new Object[] {elem, _offset,
	        		_htmlText, false});
	    } catch (Exception e) {
	        throw new IOException("The method insertHTML() "
	        		+ "could not be invoked", e);
	    }
	}
	
	
	

	/**
	 * Insert HTML code into a certain document (into the _parent document).
	 * 
	 * @param _parent		the tag name of the parent document
	 * @param _htmlText		the HTML text that is going to be inserted
	 * @throws BadLocationException exception 
	 * @throws IOException exception
	 */
	public final void insertHTML(final String _parent, 
			final String _htmlText) throws BadLocationException, IOException {
	    if (hl.getParser() == null) {
	        throw new IllegalStateException("No HTMLEditorKit.Parser");
	    }
	    
	    Element elem = getElement(_parent);
	    int offset = elem.getStartOffset();

	    //the method insertHTML is not visible
	    try {
	        Method insertHTML = javax.swing.text.html.HTMLDocument
	        		.class.getDeclaredMethod("insertHTML",
	                new Class[] {Element.class, int.class,
	        				String.class, boolean.class});
	        insertHTML.setAccessible(true);
	        insertHTML.invoke(hl, new Object[] {
	        		elem, offset, _htmlText, false});
	    } catch (Exception e) {
	        throw new IOException("The method insertHTML() could not"
	        		+ " be invoked", e);
	    }
	}
	
	
	
	/**
	 * Return the element with specified offset.
	 * @param _offset	the offset.
	 * @return			the element with specified offset.
	 */
	public final Element getCurrentElement(final int _offset) {
	    ElementIterator ei = new ElementIterator(hl);
	    Element elem, currentElem = null;
	    int elemLength = Integer.MAX_VALUE;

	    while ((elem = ei.next()) != null) { //looking for closest element
	        int start = elem.getStartOffset(),
	        		end = elem.getEndOffset(), len = end - start;
	        if (elem.isLeaf() 
	        		|| elem.getName().equals("html")) {
	            continue;
	        }
	        if (start <= _offset && _offset < end && len <= elemLength) {
	            currentElem = elem;
	            elemLength = len;
	        }
	    }

	    return currentElem;
	}



	/**
	 * {@inheritDoc}
	 */
	public final Element[] getRootElements() {
		return hl.getRootElements();
	}



	/**
	 * {@inheritDoc}
	 */
	public final void remove(final int _offs, final int _len) 
			throws BadLocationException {
		hl.remove(_offs, _len);
	}



	/**
	 * {@inheritDoc}
	 */
	public final Element getElement(final String _string) {
		return hl.getElement(_string);
	}



	/**
	 * {@inheritDoc}
	 */
	public final HTMLDocument getHl() {
		return hl;
	}



	/**
	 * {@inheritDoc}
	 */
	public final void setHl(final HTMLDocument _hl) {
		this.hl = _hl;
	}

}
