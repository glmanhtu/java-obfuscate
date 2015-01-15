package com.example.spring.util;

/**
 * An interface to know when a line is printed.
 */
public interface PrintListener {

	/**
	 * A line was printed
	 * @param line The printed line
	 */
	public void linePrinted(String line);
}
