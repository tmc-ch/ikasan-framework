package org.ikasan.spec.solr;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Ikasan Development on 27/08/2017.
 */
public interface SolrGeneralService<ENTITY, RESULTS>
{
    /**
     * Perform general search against ikasan solr index.
     *
     * @param moduleNames
     * @param flowNames
     * @param searchString
     * @param startTime
     * @param endTime
     * @param resultSize
     * @param negateQuery
     * @param sortField
     * @param sortOrder
     * @return
     */
    public RESULTS search(Set<String> moduleNames, Set<String> flowNames, String searchString, long startTime
        , long endTime, int resultSize, boolean negateQuery, String sortField, String sortOrder) throws IOException;

    /**
     * Perform general search against ikasan solr index.
     *
     * @param moduleNames
     * @param flowNames
     * @param searchString
     * @param startTime
     * @param endTime
     * @param resultSize
     * @param entityTypes
     * @param negateQuery
     * @param sortField
     * @param sortOrder
     * @return
     */
    public RESULTS search(Set<String> moduleNames, Set<String> flowNames, String searchString, long startTime
        , long endTime, int resultSize, List<String> entityTypes, boolean negateQuery, String sortField, String sortOrder);

    /**
     * Perform general search against ikasan solr index.
     *
     * @param searchString
     * @param startTime
     * @param endTime
     * @param resultSize
     * @param entityTypes
     * @param negateQuery
     * @param sortField
     * @param sortOrder
     * @return RESULTS
     */
    public RESULTS search(String searchString, long startTime, long endTime, int resultSize, List<String> entityTypes, boolean negateQuery, String sortField, String sortOrder);

    /**
     * Perform general search against ikasan solr index.
     *
     * @param searchString
     * @param startTime
     * @param endTime
     * @param offset
     * @param resultSize
     * @param entityTypes
     * @param negateQuery
     * @param sortField
     * @param sortOrder
     * @return RESULTS
     */
    public RESULTS search(String searchString, long startTime, long endTime, int offset, int resultSize, List<String> entityTypes, boolean negateQuery, String sortField, String sortOrder);

    /**
     * Perform general search against ikasan solr index.
     *
     * @param moduleNames
     * @param searchString
     * @param startTime
     * @param endTime
     * @param offset
     * @param resultSize
     * @param entityTypes
     * @param negateQuery
     * @param sortField
     * @param sortOrder
     * @return RESULTS
     */
    public RESULTS search(Set<String> moduleNames, String searchString, long startTime, long endTime, int offset, int resultSize
        , List<String> entityTypes, boolean negateQuery, String sortField, String sortOrder);

    /**
     * Perform general search against ikasan solr index.
     *
     * @param moduleNames
     * @param flowNames
     * @param componentNames
     * @param eventId
     * @param searchString
     * @param startTime
     * @param endTime
     * @param offset
     * @param resultSize
     * @param entityTypes
     * @param negateQuery
     * @param sortField
     * @param sortOrder
     * @return
     */
    public RESULTS search(Set<String> moduleNames, Set<String> flowNames, Set<String> componentNames, String eventId
        , String searchString, long startTime, long endTime, int offset, int resultSize, List<String> entityTypes, boolean negateQuery, String sortField, String sortOrder);

    /**
     * Method to find a document in the solr index by type and id.
     *
     * @param type
     * @param id
     */
    public ENTITY findById(String type, String id);

    /**
     * Method to find a document in the solr index by type and error uri.
     *
     * @param type
     * @param uri
     */
    public ENTITY findByErrorUri(String type, String uri);

    /**
     * Save or update an ENTITY
     *
     * @param entity
     */
    public void saveOrUpdate(ENTITY entity);

    /**
     * Save or update a list of ENTITY
     *
     * @param entity
     */
    public void saveOrUpdate(List<ENTITY> entity);

    /**
     * Set the solr username
     *
     * @param solrUsername
     */
    public void setSolrUsername(String solrUsername);


    /**
     * Set the solr password
     *
     * @param solrPassword
     */
    public void setSolrPassword(String solrPassword);
}
