package fr.paris.lutece.plugins.workflow.modules.tipi.service;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;

public interface ITipiService
{

    // BEAN
    String BEAN_NAME = "workflow-tipi.tipiService";

    /**
     * Create an instance of the tipi class
     * 
     * @param tipi
     *            The instance of the Tipi which contains the informations to store
     * @return The instance of tipi which has been created with its primary key.
     */

    Tipi create( Tipi tipi );

    /**
     * Update of the tipi data specified in parameter
     * 
     * @param tipi
     *            The instance of the Tipi which contains the data to store
     * @return The instance of the tipi which has been updated
     */

    Tipi update( Tipi tipi );

    /**
     * Remove the tipi whose identifier is specified in parameter
     * 
     * @param strRefDet
     *            The RefDet
     */

    void remove( String strRefDet );

    /**
     * Returns an instance of a tipi whose identifier is specified in parameter
     * 
     * @param strRefDet
     *            The RefDet
     * @return an instance of Tipi
     */

    Tipi findByPrimaryKey( String strRefDet );

    /**
     * Returns an instance of a tipi associated to the specified idop
     * 
     * @param strIdop
     *            the idop
     * @return an instance of Tipi
     */
    Tipi findByIdop( String strIdop );

}
