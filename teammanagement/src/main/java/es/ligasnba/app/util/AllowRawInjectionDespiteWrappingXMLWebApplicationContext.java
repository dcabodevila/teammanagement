package es.ligasnba.app.util;

import java.io.IOException;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class AllowRawInjectionDespiteWrappingXMLWebApplicationContext extends XmlWebApplicationContext {
	   @Override
	    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
	            throws IOException {
	        beanFactory.setAllowRawInjectionDespiteWrapping(true);
	        super.loadBeanDefinitions(beanFactory);
	    }
}
