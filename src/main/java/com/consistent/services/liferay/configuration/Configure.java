package com.consistent.services.liferay.configuration;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;


import com.consistent.services.liferay.constants.Constants;
import com.consistent.services.liferay.interf.ConfigurationRate;
import com.consistent.services.liferay.portal.Portal;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Component(
		immediate=true,
		configurationPid = "com.consistent.services.liferay.interf.ConfigurationRate",
		configurationPolicy = ConfigurationPolicy.OPTIONAL,
		property={"jaxrs.application=true"})

public class Configure extends Portal{
	
	private static final Log log = LogFactoryUtil.getLog(Configure.class);
	
	
	@Activate
	@Modified
	public void activate(Map<String, Object> properties) {
				log.info("Se ha cardo la configuracion del portal " + new Date().toString());
				_restConfigurationApi = ConfigurableUtil.createConfigurable(ConfigurationRate.class, properties);
				
		if (_restConfigurationApi != null) {
			if(_restConfigurationApi.structureHotelId()!=0){
				Constants.STRUCTURE_HOTEL_ID =_restConfigurationApi.structureHotelId();
				log.info("Estructura de hotel localizada="+Constants.STRUCTURE_HOTEL_ID);
			}
		
			if(_restConfigurationApi.structureRatesId()!=0){
				Constants.STRUCTURE_RATE_ID =_restConfigurationApi.structureRatesId();
				log.info("Identificador de rate localizado="+Constants.STRUCTURE_RATE_ID);
			}
			
			if(_restConfigurationApi.folderId()!=0){
				Constants.FOLDER_ID =_restConfigurationApi.folderId();
				log.info("Identificador de folder de hotel localizado="+Constants.FOLDER_ID);
			}
			
			} else {
			System.out.println("The sample DXP REST config object is not yet initialized");
		}
	}

	private ConfigurationRate _restConfigurationApi;
}
