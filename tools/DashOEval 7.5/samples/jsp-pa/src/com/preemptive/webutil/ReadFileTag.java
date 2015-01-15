package com.preemptive.webutil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ReadFileTag extends TagSupport {
	private String file;

	public final String getFile() {
		return file;
	}

	public final void setFile(final String file) {
		this.file = file;
	}

	public int doEndTag() throws JspException {
		readFile(pageContext.getOut(), pageContext.getServletContext().getRealPath(file), file);
		return super.doEndTag();
	}

	private static void readFile(final JspWriter out, final String fileName, final String file) {
		BufferedReader bf = null;
		try{
			if (fileName != null) {
				bf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			} else {
				bf = new BufferedReader(new InputStreamReader(pageContext.getServletContext().getResourceAsStream("/"+file)));
			}
			String line = null;
			while((line = bf.readLine()) != null){
				line = line.replaceAll("&", "&amp;");
				line = line.replaceAll("<", "&lt;");
				line = line.replaceAll(">", "&gt;");
				out.println(line);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(bf != null){
				try{
					bf.close();
				}
				catch(IOException e){
					// ignored
				}
			}
		}
	}
}
