package com.consistent.services.liferay.sax;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.consistent.services.liferay.portal.Portal;
import com.consistent.services.liferay.constants.Constants;
import com.consistent.services.liferay.interf.Mapping;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.impl.JournalArticleImpl;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

public class HotelMapping extends Portal implements Mapping{
private static final Log log = LogFactoryUtil.getLog(HotelMapping.class);
	
	private String articleId;
    private String title;
    private String hotelCode;
    private String name;
    private String keyword;
    private String description;
    private String shortDescription;
    private List<String> amenities;
    private List<String> mediaLinks;
    private List<String> roomLinks;
    private String address;
    private String city;
    private String country;
    private String latitude;
    private String longitude;
    private String references;
    private String addresses;
    private String state;
    private String zipCode;    
    private List<String> phones;
    
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getKeyword(){
		return keyword;
	}
	
	public void setKeyword(String keyword){
		this.keyword = keyword;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String codigo) {
		this.hotelCode = codigo;
	}
	public String getName() {
		return name;
	}

	public void setName(String nombre) {
		this.name = nombre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descripcion) {
		this.description = descripcion;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String descripcionCorta) {
		this.shortDescription = descripcionCorta;
	}
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String direccion) {
		this.address = direccion;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String pais) {
		this.country = pais;
	}

	public String getState() {
		return state;
	}

	public void setState(String estado) {
		this.state = estado;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String ciudad) {
		this.city = ciudad;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String codPostal) {
		this.zipCode = codPostal;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitud) {
		this.latitude = latitud;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitud) {
		this.longitude = longitud;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String referencias) {
		this.references = referencias;
	}

	public String getAddresses() {
		return addresses;
	}

	public void setAddresses(String direcciones) {
		this.addresses = direcciones;
	}

	public List<String> getRoomLinks() {
		return roomLinks;
	}

	public void setRoomLinks(List<String> roomLinks) {
		this.roomLinks = roomLinks;
	}

	public List<String> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<String> amenitieArray) {
		this.amenities = amenitieArray;
	}

	public List<String> getMediaLinks() {
		return mediaLinks;
	}

	public void setMediaLinks(List<String> mediaLinksObj) {
		this.mediaLinks = mediaLinksObj;
	}
	
	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> telefonos) {
		this.phones = telefonos;
	}
	
	//Metodo que obtienes todos los hoteles
	public HashSet<String> getHotels() throws PortalException, XMLStreamException, IOException{
		
			final HashSet<String> hotels = new HashSet<>();
				
			try {
				//Obtiene el Id de la estructura
			    DDMStructure results = DDMStructureLocalServiceUtil.getStructure(Constants.STRUCTURE_HOTEL_ID);
				log.info("Estructura: "+results.getStructureKey());
				//Obtiene el Id de la carpeta
				long folderId = 0;
				if(Constants.CODIGODEMARCA != null && !Constants.CODIGODEMARCA.isEmpty()) {
					JournalFolder folder = JournalFolderLocalServiceUtil.fetchFolder(Constants.SITE_ID, Constants.FOLDER_ID, Constants.CODIGODEMARCA);
					folderId = folder.getFolderId();
					log.info("Folder id: "+folderId);
				}
				
				log.info("El codigo de hotel: "+Constants.CODIGODEHOTEL);
				if(!Constants.CODIGODEHOTEL.isEmpty()){
					log.info("Entro al filtrado");
					DynamicQuery dynamicQueryJournal = DynamicQueryFactoryUtil.forClass(JournalArticleImpl.class, "folder", PortalClassLoaderUtil.getClassLoader());
					dynamicQueryJournal.add(PropertyFactoryUtil.forName("DDMStructureKey").eq(results.getStructureKey()));
					dynamicQueryJournal.add(PropertyFactoryUtil.forName("groupId").eq(new Long(Constants.SITE_ID)));
					dynamicQueryJournal.add(PropertyFactoryUtil.forName("folderId").eq(getFolderId(Constants.CODIGODEHOTEL)));
					final HashSet<JournalArticle> journalArticles = new HashSet<JournalArticle>(JournalArticleLocalServiceUtil.dynamicQuery(dynamicQueryJournal));
					for (JournalArticle journal : journalArticles) {
						if(!journal.isInTrash()){
							if(JournalArticleLocalServiceUtil.isLatestVersion(Constants.SITE_ID, journal.getArticleId(), journal.getVersion(),WorkflowConstants.STATUS_APPROVED)){
								hotels.add(HotelContentsMapping(journal, Constants.getLanguaje()));
								
							} 
						}
							
					}
				}else{
					log.info("Entro al no filtrado");
					DynamicQuery dynamicQueryJournal = DynamicQueryFactoryUtil.forClass(JournalArticleImpl.class, "folder", PortalClassLoaderUtil.getClassLoader());
					dynamicQueryJournal.add(PropertyFactoryUtil.forName("DDMStructureKey").eq(results.getStructureKey()));
					dynamicQueryJournal.add(PropertyFactoryUtil.forName("groupId").eq(new Long(Constants.SITE_ID)));
					dynamicQueryJournal.add(PropertyFactoryUtil.forName("treePath").like("%"+folderId+"%"));
					final HashSet<JournalArticle> journalArticles = new HashSet<JournalArticle>(JournalArticleLocalServiceUtil.dynamicQuery(dynamicQueryJournal));
					for (JournalArticle journal : journalArticles) {
						if(!journal.isInTrash()){
							if(JournalArticleLocalServiceUtil.isLatestVersion(Constants.SITE_ID, journal.getArticleId(), journal.getVersion(),WorkflowConstants.STATUS_APPROVED)){
								hotels.add(HotelContentsMapping(journal, Constants.getLanguaje()));
								
							} 
						}
							
					}
				}
				
				
				log.info("Total de hoteles: "+hotels.size());
			}  catch (IndexOutOfBoundsException ie) {
				log.error("El nombre de la carpeta Hotel No coincide");
				log.error("Causa: " + ie.getCause());
				ie.fillInStackTrace();
			} catch (NoSuchStructureException e) {
				log.error("La estructura no existe");
				log.error("Causa: " + e.getCause());
			}catch (NullPointerException e) {
				log.error("El nombre de la Marca");
				log.error("Corregir el nombre de la marca");
				log.error("Causa: " + e.getCause());
			}
			return hotels;
		}

	@Override
	public String getMapping() throws XMLStreamException, IOException, NumberFormatException, PortalException {

		 StringWriter stringWriter = new StringWriter();
		 XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
  	 
       XMLStreamWriter xMLStreamWriter =
       xMLOutputFactory.createXMLStreamWriter(stringWriter);
       	 xMLStreamWriter.writeStartDocument();
	         xMLStreamWriter.writeStartElement("hotel");	
		         xMLStreamWriter.writeStartElement("guid");
		         	xMLStreamWriter.writeCharacters(articleId); 
		         xMLStreamWriter.writeEndElement();
		         xMLStreamWriter.writeStartElement("code");
		         	xMLStreamWriter.writeCharacters(hotelCode);
		         xMLStreamWriter.writeEndElement();
		         xMLStreamWriter.writeStartElement("name");
		         	xMLStreamWriter.writeCharacters(name);
		         xMLStreamWriter.writeEndElement();
		         xMLStreamWriter.writeStartElement("title");
		         	xMLStreamWriter.writeCharacters(title);
		         xMLStreamWriter.writeEndElement();
		         xMLStreamWriter.writeStartElement("language");
		         	xMLStreamWriter.writeCharacters(Constants.LENGUAJE);
		         xMLStreamWriter.writeEndElement();
			    xMLStreamWriter.writeStartElement("keyword");
		        	xMLStreamWriter.writeCharacters(keyword);
		        xMLStreamWriter.writeEndElement();
		        xMLStreamWriter.writeStartElement("shortDescription");
		        	xMLStreamWriter.writeDTD(shortDescription);
		        xMLStreamWriter.writeEndElement();
		        xMLStreamWriter.writeStartElement("description");
		        	xMLStreamWriter.writeDTD(description);
		        xMLStreamWriter.writeEndElement();
		        xMLStreamWriter.writeStartElement("order");
		        	xMLStreamWriter.writeCharacters(Mapping.order);
		        xMLStreamWriter.writeEndElement();
		        xMLStreamWriter.writeStartElement("channel");
		        	xMLStreamWriter.writeCharacters(Mapping.channel);
		        xMLStreamWriter.writeEndElement();
		        
		        /*mediaLink section*/
		         JSONArray ArrayMediaLinks = JSONFactoryUtil.createJSONArray();
		         List<String> MeliaLinkList = getMediaLinks();
					for (String mediaLinkItem : MeliaLinkList) {
						JSONObject myObject;
						try {
							myObject = JSONFactoryUtil.createJSONObject(mediaLinkItem);
							ArrayMediaLinks.put(myObject);
						} catch (JSONException e) {
						log.error("Error converter json"+e);
						}
						
					}
		         xMLStreamWriter.writeStartElement("medialinks");		   
			         xMLStreamWriter.writeStartElement("medialink");
			         
					   xMLStreamWriter.writeStartElement("keyword");
					   xMLStreamWriter.writeEndElement();
					         for (int i = 0; i < ArrayMediaLinks.length(); i++) {
									JSONObject jsonobject = ArrayMediaLinks.getJSONObject(i);
								    String link = jsonobject.getString("link");
								    String type_image = jsonobject.getString("type_image");
									xMLStreamWriter.writeStartElement("multimedia");
						            xMLStreamWriter.writeAttribute("type",type_image);
							        xMLStreamWriter.writeStartElement("url");
							        xMLStreamWriter.writeCharacters(link);
							        xMLStreamWriter.writeEndElement();
						            xMLStreamWriter.writeEndElement();
								}
					         xMLStreamWriter.writeStartElement("thumbnail");
					         xMLStreamWriter.writeEndElement();
					         xMLStreamWriter.writeStartElement("type");
					         xMLStreamWriter.writeEndElement();
				      xMLStreamWriter.writeEndElement();
		         xMLStreamWriter.writeEndElement();
		          //mediaLink section
		         
		       //location section
		         xMLStreamWriter.writeStartElement("location");
			         xMLStreamWriter.writeStartElement("address");
			         xMLStreamWriter.writeCharacters(address);
			         xMLStreamWriter.writeEndElement();
			         xMLStreamWriter.writeStartElement("city");
			         xMLStreamWriter.writeCharacters(city);
			         xMLStreamWriter.writeEndElement();
			         xMLStreamWriter.writeStartElement("country");
			         xMLStreamWriter.writeCharacters(country);
			         xMLStreamWriter.writeEndElement();
			         xMLStreamWriter.writeStartElement("directions");
			         xMLStreamWriter.writeCharacters(getAddresses());
			         xMLStreamWriter.writeEndElement();
			         xMLStreamWriter.writeStartElement("latitude");
			         xMLStreamWriter.writeCharacters(latitude);
			         xMLStreamWriter.writeEndElement();
			         xMLStreamWriter.writeStartElement("longitude");
			         xMLStreamWriter.writeCharacters(longitude);
			         xMLStreamWriter.writeEndElement();
			         xMLStreamWriter.writeStartElement("references");
			         xMLStreamWriter.writeCharacters(references);
			         xMLStreamWriter.writeEndElement();
			         xMLStreamWriter.writeStartElement("state");
			         xMLStreamWriter.writeCharacters(state);
			         xMLStreamWriter.writeEndElement();
			         xMLStreamWriter.writeStartElement("zip");
			         xMLStreamWriter.writeCharacters(zipCode);
			         xMLStreamWriter.writeEndElement();
		         xMLStreamWriter.writeEndElement();
		         //location section
		         
			      //telephone section
			       xMLStreamWriter.writeStartElement("telephones");
			        for (String phone : getPhones()) {
			        	if(!phone.equals("{}")){
			            xMLStreamWriter.writeStartElement("telephone");
			  	       
			            xMLStreamWriter.writeStartElement("number");
			  	        xMLStreamWriter.writeCharacters(phone);
			  	        xMLStreamWriter.writeEndElement();
			  	        
			  	        xMLStreamWriter.writeStartElement("type");
				        xMLStreamWriter.writeCharacters("telephone");
				        xMLStreamWriter.writeEndElement();
				        
				        xMLStreamWriter.writeEndElement();
			        	}
					}
			        xMLStreamWriter.writeEndElement();
			        //fin telephone section
			        //rooms
					JSONArray ArrayRoom = JSONFactoryUtil.createJSONArray();
					xMLStreamWriter.writeStartElement("rooms");
					for(String roomLink : getRoomLinks()){				
							JSONObject object=null;
								try {
									object=JSONFactoryUtil.createJSONObject(roomLink);
									long classpk=object.getLong("classPK");
									xMLStreamWriter.writeDTD(getJournalArticleByClassPk(classpk));
									ArrayRoom.put(object);		
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}			
					}
					xMLStreamWriter.writeEndElement();
			        //fin rooms
			//Root 
	         xMLStreamWriter.writeEndDocument();
	           
	         xMLStreamWriter.flush();
	         xMLStreamWriter.close();
	         String xmlString = stringWriter.getBuffer().toString();
	         stringWriter.close();
	        xmlString=xmlString.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
	        return xmlString;        
	}

	public HotelMapping(String articleId, String title, String hotelCode, String name, String keyword,
			String description, String shortDescription, List<String> amenities, List<String> mediaLinks,
			List<String> roomLinks, String address, String city, String country, String latitude, String longitude,
			String references, String addresses, String state, String zipCode, List<String> phones) {
		super();
		this.articleId = articleId;
		this.title = title;
		this.hotelCode = hotelCode;
		this.name = name;
		this.keyword = keyword;
		this.description = description;
		this.shortDescription = shortDescription;
		this.amenities = amenities;
		this.mediaLinks = mediaLinks;
		this.roomLinks = roomLinks;
		this.address = address;
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.references = references;
		this.addresses = addresses;
		this.state = state;
		this.zipCode = zipCode;
		this.phones = phones;
	}

	public HotelMapping() {
		super();
		this.articleId = "";
		this.title = "";
		this.hotelCode = "";
		this.name = "";
		this.keyword = "";
		this.description = "";
		this.shortDescription = "";
		this.amenities = new ArrayList<>();
		this.mediaLinks = new ArrayList<>();
		this.roomLinks = new ArrayList<>();
		this.address = "";
		this.city = "";
		this.country = "";
		this.latitude = "";
		this.longitude = "";
		this.references = "";
		this.addresses = "";
		this.state = "";
		this.zipCode = "";
		this.phones = new ArrayList<>();
	}
	
	private String HotelContentsMapping(JournalArticle content, String locale) throws XMLStreamException, IOException, NumberFormatException, PortalException{
		
		HotelMapping hotelMapping = new HotelMapping();
		Document docXML=null;
	        try {
	        	
				docXML = SAXReaderUtil.read(content.getContentByLocale(locale));
				hotelMapping = new HotelMapping();
				hotelMapping.articleId = content.getArticleId();
				hotelMapping.title =content.getTitle(locale);
				hotelMapping.keyword = docXML.valueOf("//dynamic-element[@name='keywordsHotel']/dynamic-content/text()");
				hotelMapping.hotelCode= docXML.valueOf("//dynamic-element[@name='codeHotel']/dynamic-content/text()");
				hotelMapping.name =docXML.valueOf("//dynamic-element[@name='nameHotel']/dynamic-content/text()");
				hotelMapping.description=docXML.valueOf("//dynamic-element[@name='descriptionHotel']/dynamic-content/text()");
				hotelMapping.shortDescription=docXML.valueOf("//dynamic-element[@name='shortDescriptionHotel']/dynamic-content/text()");
				List<Node> addressNodes = docXML.selectNodes("//dynamic-element[@name='addressHotel']/dynamic-element");
				
				for(Node addressNode : addressNodes){
					String nombre= addressNode.valueOf("@name");
					String valor= addressNode.valueOf("dynamic-content/text()");
					if(nombre.equals("addressDetailHotel")){
						hotelMapping.address=valor;
					}
					if(nombre.equals("countryHotel")){
						hotelMapping.country=valor;
					}
					if(nombre.equals("stateHotel")){
						hotelMapping.state=valor;
					}
					if(nombre.equals("cityHotel")){
						hotelMapping.city=valor;
					}
					if(nombre.equals("zipHotel")){
						hotelMapping.zipCode=valor;
					}
					if(nombre.equals("latitudHotel")){
						hotelMapping.latitude=valor;
					}
					if(nombre.equals("longitudHotel")){
						hotelMapping.longitude=valor;
					}
					if(nombre.equals("referencesHotel")){
						hotelMapping.references=valor;
					}
					if(nombre.equals("directionsHotel")){
						hotelMapping.addresses=valor;
					}
	            }
				
				List<Node> telefonoNodes = docXML.selectNodes("//dynamic-element[@name='telephoneHotel']/dynamic-element");		
				List<String> telefonosArray=new ArrayList<String>();
				for(Node telefonoNode : telefonoNodes){				
					String valor= telefonoNode.valueOf("dynamic-content/text()");
					if(!valor.trim().equals("")){					
						telefonosArray.add(valor);
					}			
	            }
				
				hotelMapping.phones=sanitizeArray(telefonosArray);
			
				List<Node> alternativeHotels = docXML.selectNodes("//dynamic-element[@name='hotelesAlternos']/dynamic-element");		
				List<String> alternativeHotelsArray=new ArrayList<String>();
				for(Node alternativeHotel : alternativeHotels){				
					String valor= alternativeHotel.valueOf("dynamic-content");
					if(!valor.trim().equals("") && !valor.trim().equals("{}")){
						alternativeHotelsArray.add(valor);
					}
								
	            }
				
				List<Node> mediaNodes = docXML.selectNodes("//dynamic-element[@name='mediaLinksHotel']/dynamic-element");
				List<String> mediaArray=new ArrayList<String>();
				for(Node mediaNode : mediaNodes){				
					String pie= mediaNode.valueOf("dynamic-element[@name='Pie']/dynamic-content/text()");				
					String link= mediaNode.valueOf("dynamic-content/text()");				
					String type_image= mediaNode.valueOf("dynamic-element[@name='typeHotel']/dynamic-content/text()");						
					if(!link.trim().equals("")){
						JSONObject object=JSONFactoryUtil.createJSONObject();
						object.put("link", link);
						object.put("pie", pie);
						object.put("type_image", type_image);
						mediaArray.add(object.toJSONString());
					}
					}
				
				hotelMapping.mediaLinks=sanitizeArray(mediaArray);
				
				
				
				List<Node> roomLinkNodes = docXML.selectNodes("//dynamic-element[@name='roomLinksHotel']/dynamic-element");		
				List<String> roomsArray=new ArrayList<String>();
				for(Node roomLinkNode : roomLinkNodes){				
					String valor= roomLinkNode.valueOf("dynamic-content/text()");
					if(!valor.trim().equals("")){
						JSONObject object=null;
						try {
							object=JSONFactoryUtil.createJSONObject(valor);
							getJournalArticleByClassPk(Long.parseLong(object.get("classPK").toString()));
						} catch (JSONException e) {
							log.error("ERROR AL OBTENER JSON",e);
						}					
						roomsArray.add(object.toJSONString());
					}
				}
				
				hotelMapping.roomLinks=sanitizeArray(roomsArray);

							
	        }catch (DocumentException e) {
				log.error("ERROR to get XML",e);
			}
	        return hotelMapping.getMapping();
	        
	}
	
	private List<String> sanitizeArray(List<String> arraySan){
	    	if(arraySan.size()>0){
		    	while(arraySan.size()<2){
					JSONObject object=JSONFactoryUtil.createJSONObject();
					arraySan.add(object.toJSONString());				
				}
	    	}
	    	return arraySan;    	
	    }
	 
	private String getJournalArticleByClassPk(Long classPk) throws PortalException, XMLStreamException{
		 //log.info("<------- getJournalArticleByClassPk ------->");	
		 DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(com.liferay.journal.model.impl.JournalArticleImpl.class, "journal",PortalClassLoaderUtil.getClassLoader());			
		 dynamicQuery.add((PropertyFactoryUtil.forName("resourcePrimKey").eq(new Long(classPk))));
     	 dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId",Constants.SITE_ID));
		 HashSet<com.liferay.journal.model.impl.JournalArticleImpl> ja= new HashSet<>(JournalArticleLocalServiceUtil.dynamicQuery(dynamicQuery));
		 String result = "";	
			for (JournalArticleImpl journalArticleImpl : ja) {
					if(!journalArticleImpl.isInTrash()){
						if(JournalArticleLocalServiceUtil.isLatestVersion(Constants.SITE_ID, journalArticleImpl.getArticleId(), journalArticleImpl.getVersion(),WorkflowConstants.STATUS_APPROVED)){
							com.consistent.services.liferay.sax.RoomMapping content = new com.consistent.services.liferay.sax.RoomMapping();
							content.RoomContent(journalArticleImpl,Constants.getLanguaje());
							result = content.mappngRoom();
								
						}
					}
					
				
			}
			
			return result;
			}
	
}
