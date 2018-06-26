package fr.paris.lutece.plugins.workflow.modules.tipi.service;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetHistory;

public interface ITipiRefDetHistoryService
{

    // BEAN
    String BEAN_NAME = "workflow-tipi.tipiRefDetHistoryService";

    /**
     * Create an instance of the tipiRefDetHistory class
     * 
     * @param tipiRefDetHistory
     *            The instance of the TipiRefDetHistory which contains the informations to store
     * @return The instance of tipiRefDetHistory which has been created with its primary key.
     */

    TipiRefDetHistory create( TipiRefDetHistory tipiRefDetHistory );

    /**
     * Update of the tipiRefDetHistory data specified in parameter
     * 
     * @param tipiRefDetHistory
     *            The instance of the TipiRefDetHistory which contains the data to store
     * @return The instance of the tipiRefDetHistory which has been updated
     */

    TipiRefDetHistory update( TipiRefDetHistory tipiRefDetHistory );

    /**
     * Remove the tipiRefDetHistory whose identifier is specified in parameter
     * 
     * @param nIdHistory
     *            The idHistory
     */

    void remove( int nIdHistory );

    /**
     * Returns an instance of a tipiRefDetHistory whose identifier is specified in parameter
     * 
     * @param nIdHistory
     *            The tipiRefDetHistory primary key
     * @return an instance of TipiRefDetHistory
     */

    TipiRefDetHistory findByPrimaryKey( int nIdHistory );

    /**
     * Returns an instance of a tipiRefDetHistory associated to the specified RefDet
     * 
     * @param strRefDet
     *            the RefDet
     * @return an instance of TipiRefDetHistory
     */
    TipiRefDetHistory findByRefDet( String strRefDet );

}
