package es.ligasnba.app.model.util;

import java.util.HashMap;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import es.ligasnba.app.util.constants.Constants;

public class DynamicPropertiesFileReader implements BeanFactoryAware{
	
	@Autowired
	private BeanFactory beanFactory;
	private Properties coreDynamicPropertiesBean;
	private HashMap<String, String> dynamicPropertiesMap;
	
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		this.beanFactory = beanFactory;		
	}
	
	public void setDynamicPropertiesMap(
			HashMap<String, String> dynamicPropertiesMap) {
		this.dynamicPropertiesMap = dynamicPropertiesMap;
	}
	
	public String getProperties(){
		setCoreDynamicPropertiesBean(createCoreDynamicPropertiesBeanInstance());
		
			
			if (getDynamicPropertiesMap().get("maintenancemode")!=null)
				return getDynamicPropertiesMap().get("maintenancemode").toString();
			else return "0";
			
		
	}
	
	public String setProperty(String value){
		
		setCoreDynamicPropertiesBean(createCoreDynamicPropertiesBeanInstance());
		
		getDynamicPropertiesMap().put("maintenancemode", value);
		
		return getDynamicPropertiesMap().get("maintenancemode").toString();
	}
	
	
	public void setCoreDynamicPropertiesBean(Properties coreDynamicPropertiesBean) {
		this.coreDynamicPropertiesBean = coreDynamicPropertiesBean;
		}
	
	public Properties createCoreDynamicPropertiesBeanInstance() {
		
		if (this.beanFactory != null)
			return  (Properties) this.beanFactory.getBean(Constants.BEAN_NAME_CORE_DYNAMIC_PROPERTIES_BEAN);
		else
			System.out.println("this.beanFactory is NULL!");
			return new Properties();
			
	}
	public Properties getCoreDynamicPropertiesBean() {
		return coreDynamicPropertiesBean;
	}

	public HashMap<String, String> getDynamicPropertiesMap() {
		return dynamicPropertiesMap;
	}
	
}
