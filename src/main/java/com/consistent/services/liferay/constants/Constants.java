package com.consistent.services.liferay.constants;

import java.util.List;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class Constants {
	
		private static final Log log = LogFactoryUtil.getLog(Constants.class);
		
		// SANITIZE
		public static int SANITIZE = 0;
		public static int SANITIZEFILTER = 2;
		// NOMBRE DE VOCABULARIOS
		public static final String MARCAS = "Marcas";
		// SITE ID
		public static Long SITE_ID = null;
		// NOMBRE DE CARPETAS
		public static String NOMBRE_CARPETA_HOTEL = "Hotel";
		
		
		public static Long STRUCTURE_HOTEL_ID = null;
		public static Long STRUCTURE_RATE_ID = null;
		public static Long FOLDER_ID = null;
		// NOMBRES DE CARPETAS
		public static final String RATES = "rates";
		public static final String HOTEL = "hotel";
		// CODIGO DE LA MARCA
		public static String CODIGODEMARCA = "";
		// LENGUALE
		public static String LENGUAJE = "";
		// CONTRACTCODES
		public static String CONTRACTCODES = "";
		// CODIGO DE HOTEL
		public static String CODIGODEHOTEL = "";
		// LISTA DE FOLDERS
		public static List<String> FOLDERS;
		
		// INDATE
		public static String CHECKINDATE = "";
		//OUTDATE
		public static String CHECKOUTDATE = "";
		
		public static String NAME_STRUCTURE_DEFAULT;
		//NOMBRE DE ESTRUCTURA
		public static String HOTEL_ESTRUCUTURA = "hotel";
		
		public static String CHANNEL;
		
		//Metodo para obtener el locate
			public static String getLanguaje(){
				
				if(LENGUAJE.equalsIgnoreCase("spanish")){
				
					
					return  "es_ES";
			
				}else if(LENGUAJE.equalsIgnoreCase("english")){
					return "en_US";
				}else {
					log.info("No se establecio un lenguaje valido");
				}
				return new String();
				
				
			}
		// ValidContractCodes
			public static void validContractCodes(String contractcodes){
				if(contractcodes != null && !contractcodes.isEmpty()){
					CONTRACTCODES = contractcodes;
				}else {
					CONTRACTCODES = "";
				}
			}
			
			public static String getNameBrand(String brand){
				
				if(brand.equalsIgnoreCase("fi")){
					brand = "Fiesta Inn";
				}else if(brand.equalsIgnoreCase("fa")){
					brand = "Fiesta Americana";
				}else if(brand.equalsIgnoreCase("ex")){
					brand = "The Explorean";
				}else if(brand.equalsIgnoreCase("gamma")){
					brand = "Gamma";
				}else if(brand.equalsIgnoreCase("aqua")){
					brand = "Live Aqua";
				}else if(brand.equalsIgnoreCase("fag")){
					brand = "Grand Fiesta Americana";
				}else if(brand.equalsIgnoreCase("one")){
					brand = "One";
				}else{
					brand = "El nombre de la marca no existe";
				}
				
				return brand;
			}
}
