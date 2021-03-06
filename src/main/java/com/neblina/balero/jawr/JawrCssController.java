package com.neblina.balero.jawr;

import net.jawr.web.JawrConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Controller
@DependsOn(value = "JawrBinaryController")
public class JawrCssController extends AbstractJawrController {

    @Autowired
    private ServletContext servletContext;

    /**
     * Constructor
     */
    public JawrCssController() {
        super(JawrConstant.CSS_TYPE);
    }

    /* (non-Javadoc)
     * @see net.jawr.web.servlet.JawrSpringController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    @RequestMapping(value = "/jawr/**/**.css", method = RequestMethod.GET)
    ///gzip_
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        return super.handleRequest(request, response);
    }

    /* (non-Javadoc)
     * @see net.jawr.web.servlet.JawrSpringController#afterPropertiesSet()
     */
    @Override
    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        setServletContext(servletContext);
        super.afterPropertiesSet();
    }

}