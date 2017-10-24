package com.itechart.projects.contactDirectory.controller.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class LoggerFilter implements Filter {

    private static final boolean debug = true;
    private static final Logger LOGGER = Logger.getRootLogger();

    private FilterConfig filterConfig = null;

    public LoggerFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        boolean isMultipart = ServletFileUpload.isMultipartContent((HttpServletRequest) request);
        if (!isMultipart) {
            LOGGER.info("------------REQUEST_PARAMS--------------");
            Enumeration<String> params = request.getParameterNames();
            String param, value;
            while (params.hasMoreElements()) {
                param = params.nextElement();
                value = request.getParameter(param);
                LOGGER.info(param + ": " + value);
            }
        } else {
            LOGGER.info("request is encrypted");
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }
}
