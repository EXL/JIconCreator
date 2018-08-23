package ru.exlmoto.jiconcreator;

public class RGB {
    /**
     * the red component of the RGB
     */
    public int red;
    /**
     * the green component of the RGB
     */
    public int green;
    /**
     * the blue component of the RGB
     */
    public int blue;

    /**
     * Constructs an instance of this class with the given red, green and blue
     * values.
     *
     * @param red the red component of the new instance
     * @param green the green component of the new instance
     * @param blue the blue component of the new instance
     * @exception IllegalArgumentException
     *              <ul>
     *              <li>ERROR_INVALID_ARGUMENT - if the red, green or blue
     *              argument is not between 0 and 255</li>
     *              </ul>
     */
    public RGB( int red, int green, int blue ) {
        if( ( red > 255 )
                || ( red < 0 )
                || ( green > 255 )
                || ( green < 0 )
                || ( blue > 255 )
                || ( blue < 0 ) )
            System.out.println("Error: Invalid Argument");
            // SWT.error( SWT.ERROR_INVALID_ARGUMENT );
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Compares the argument to the receiver, and returns true if they represent
     * the <em>same</em> object using a class specific comparison.
     *
     * @param object the object to compare with this object
     * @return <code>true</code> if the object is the same as this object and
     *         <code>false</code> otherwise
     * @see #hashCode()
     */
    public boolean equals( Object object ) {
        if( object == this )
            return true;
        if( !( object instanceof RGB ) )
            return false;
        RGB rgb = ( RGB )object;
        return ( rgb.red == this.red )
                && ( rgb.green == this.green )
                && ( rgb.blue == this.blue );
    }

    /**
     * Returns an integer hash code for the receiver. Any two objects that return
     * <code>true</code> when passed to <code>equals</code> must return the
     * same value for this method.
     *
     * @return the receiver's hash
     * @see #equals(Object)
     */
    public int hashCode() {
        return ( blue << 16 ) | ( green << 8 ) | red;
    }

    /**
     * Returns a string containing a concise, human-readable description of the
     * receiver.
     *
     * @return a string representation of the <code>RGB</code>
     */
    public String toString() {
        return "RGB {" + red + ", " + green + ", " + blue + "}"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }
}
