package com.excilys.computerdb.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

/**
 * Custom tag to display page related content (change page and number of item
 * per page)
 * 
 * @author excilys
 *
 */
public class PageTag extends SimpleTagSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(PageTag.class);
	private int currentPage = 0;

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		try {
			displaypageSize(out);
			displayPageButtons(out);
		} catch (IOException e) {
			LOGGER.warn("PageTag IOException");
		}

	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void displaypageSize(JspWriter out) throws IOException {

		out.println("<form class=\"btn-group btn-group-sm pull-right\" role=\"group\">");
		out.println("<button type=\"submit\" class=\"btn btn-default\" name=\"nbElements\" value=\"10\">10</button>");
		out.println("<button type=\"submit\" class=\"btn btn-default\" name=\"nbElements\" value=\"50\">50</button>");
		out.println("<button type=\"submit\" class=\"btn btn-default\" name=\"nbElements\" value=\"100\">100</button>");
		out.println("</form>");
	}

	/**
	 * generate html related to Display the whole button bar at the middleBottom
	 * of the page
	 * 
	 * @param out
	 * @throws IOException
	 */
	public void displayPageButtons(JspWriter out) throws IOException {
		out.println("<div class=\"container text-center\">");
		out.println("<ul class=\"pagination\">");
		displayPreviousButton(out);
		displayRegularButtons(out);
		displayNextButton(out);
		out.println("</ul>");
		out.println("</div>");
	}

	private void displayPreviousButton(JspWriter out) throws IOException {

		if (currentPage > 1) {
			out.println("<li><a href=\"?page=" + (currentPage - 1) + "\" aria-label=\"Previous\">");
			out.println(" <span aria-hidden=\"true\">&laquo;</span>");
			out.println("</a></li>");
		}
	}

	private void displayRegularButtons(JspWriter out) throws IOException {
		if (currentPage <= 2) {
			for (int i = 1; i < 6; i++) {
				out.println("<li><a href=\"?page=" + i + "\" >" + i + "</a></li>");
			}
		} else {
			for (int i = currentPage - 2; i < currentPage + 3; i++) {
				out.println("<li><a href=\"?page=" + i + "\" >" + i + "</a></li>");
			}
		}

	}

	private void displayNextButton(JspWriter out) throws IOException {
		out.println("<li><a href=\"?page=" + (currentPage + 1) + "\" aria-label=\"Next\">");
		out.println(" <span aria-hidden=\"true\">&raquo;</span>");
		out.println("</a></li>");

	}
}
