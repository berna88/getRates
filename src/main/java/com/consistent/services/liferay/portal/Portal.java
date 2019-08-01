package com.consistent.services.liferay.portal;

import java.util.List;

import com.consistent.services.liferay.constants.Constants;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

public abstract class Portal {
	private static final Log log = LogFactoryUtil.getLog(Portal.class);
	
	protected Long getVocabularyId(){
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class, "Vocabulary", PortalClassLoaderUtil.getClassLoader());
		dynamicQuery.add(PropertyFactoryUtil.forName("name").eq(Constants.MARCAS));
		dynamicQuery.add(PropertyFactoryUtil.forName("groupId").eq(Constants.SITE_ID));
		Long vocabularyId = null;
		try {
			List<AssetVocabulary> vocabularies = AssetVocabularyLocalServiceUtil.dynamicQuery(dynamicQuery);
			vocabularyId = vocabularies.get(0).getVocabularyId();
		} catch (Exception e) {
			log.error("Error en metodo getVocabularyId: "+ e.getMessage());
		}
		//log.info("VocabularyId: "+vocabularyId);
		return vocabularyId;
	}
	
	protected Long getCategory(String marca){
		//log.info("<---------- Metodo getCategory ---------->");
		Long vocabularyId = getVocabularyId();
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategory.class, "category", PortalClassLoaderUtil.getClassLoader());
		dynamicQuery.add(PropertyFactoryUtil.forName("vocabularyId").eq(vocabularyId));
		dynamicQuery.add(PropertyFactoryUtil.forName("groupId").eq(Constants.SITE_ID));
		dynamicQuery.add(PropertyFactoryUtil.forName("name").eq(marca));
		Long categoryId = null;
		try {
			List<AssetCategory> assetCategories = AssetCategoryLocalServiceUtil.dynamicQuery(dynamicQuery);
			categoryId = assetCategories.get(0).getCategoryId();
		}catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
			log.error("Codigo de marca incorrecto: "+e);
		}catch (NullPointerException e) {
			// TODO: handle exception
			log.error("null: "+e);
		}
		//log.info("Categoria Id: "+ categoryId);
		return categoryId;
	}
	
	protected Long getFolderId(String nameFolder){
        DynamicQuery queryJournalFolder = DynamicQueryFactoryUtil.forClass(com.liferay.journal.model.impl.JournalFolderImpl.class, "journalFolder",PortalClassLoaderUtil.getClassLoader());
        queryJournalFolder.add(RestrictionsFactoryUtil.eq("name", nameFolder));
        queryJournalFolder.add( RestrictionsFactoryUtil.eq("groupId",new Long(Constants.SITE_ID)));
        List<com.liferay.journal.model.impl.JournalFolderImpl> journalfolderResults = JournalArticleResourceLocalServiceUtil.dynamicQuery(queryJournalFolder);
        log.info("getFolderId: "+journalfolderResults.get(0).getFolderId());
        return journalfolderResults.get(0).getFolderId();
    }
	
}
