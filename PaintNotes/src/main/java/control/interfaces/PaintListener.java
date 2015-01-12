package control.interfaces;

/**
 * Listener for the PaintLabel class. The listener contains methods that
 * are called just before and just after the location or size have changed.
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public interface PaintListener {

	/**
	 * size change listener called before the size has changed externally.
	 * Contains the new location in passed MouseEvent.
	 * 
	 * @param _ev 
	 * 				the passed MouseEvent which contains the new location of 
	 * 				PaintLabel.
	 */
	void beforeLocationChange(final MoveEvent _ev);

	/**
	 * location change listener called before location has changed externally.
	 * Contains the new location in passed MouseEvent.
	 * 
	 * @param _ev 
	 * 				the passed MouseEvent which contains the new location of 
	 * 				PaintLabel.
	 */
	void beforeExternalLocationChange(final MoveEvent _ev);

	/**
	 * size change listener called before the size has changed externally.
	 * Contains the new size in passed MouseEvent.
	 * 
	 * @param _ev 
	 * 				the passed MouseEvent which contains the new size of 
	 * 				PaintLabel.
	 */
	void beforeExternalSizeChange(final MoveEvent _ev);
	

	/**
	 * location change listener called after location has changed and containing
	 * the old location in passed MouseEvent.
	 * 
	 * @param _ev 
	 * 				the passed MouseEvent which contains the old location of 
	 * 				PaintLabel.
	 */
	void afterLocationChange(final MoveEvent _ev);

	/**
	 * location change listener called after location has changed externally.
	 * Contains the old location in passed MouseEvent.
	 * 
	 * @param _ev 
	 * 				the passed MouseEvent which contains the old location of 
	 * 				PaintLabel.
	 */
	void afterExternalLocationChange(final MoveEvent _ev);

	/**
	 * size change listener called after the size has changed externally.
	 * Contains the old size in passed MouseEvent.
	 * 
	 * @param _ev 
	 * 				the passed MouseEvent which contains the old size of 
	 * 				PaintLabel.
	 */
	void afterExternalSizeChange(final MoveEvent _ev);
	 
	
	
}