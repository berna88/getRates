package com.consistent.services.liferay.interf;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;


	@ExtendedObjectClassDefinition(category = "Rates", scope = ExtendedObjectClassDefinition.Scope.SYSTEM)
	@Meta.OCD( localization = "content/Language",id = "com.consistent.services.liferay.interf.ConfigurationRate",name = "Service Rates")
	public interface ConfigurationRate {
		@Meta.AD(required = false,description = "Get web content folder id")
	    public Long folderId();
	    
	    @Meta.AD(required = false,description = "Get web content structure hotel id")
	    public Long structureHotelId();
	   
	    @Meta.AD(required = false,description = "Get web content structure rate id")
	    public Long structureRatesId();
	    
}
