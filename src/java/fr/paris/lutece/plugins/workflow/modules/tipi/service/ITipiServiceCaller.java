package fr.paris.lutece.plugins.workflow.modules.tipi.service;

import fr.paris.tipi.generated.CreerPaiementSecuriseRequest;

public interface ITipiServiceCaller
{

    // BEAN
    String BEAN_NAME = "workflow-tipi.tipiServiceCaller";

    /**
     * 
     * Method to get the id_op
     * 
     * @param email
     * @param refDet
     * @param amount
     * @return TipiCallWebService
     */
    String getIdop( String email, String refDet, int amount );

    /**
     * Method to create a payment request
     * 
     * @param email
     * @param refDet
     * @param amount
     * @return Request
     */
    CreerPaiementSecuriseRequest createRequest( String email, String refDet, int amount );

}
