/*
 * Copyright (c) 2002-2018, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.tipi.service;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Calendar;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.workflow.modules.tipi.exception.TransactionResultException;
import fr.paris.lutece.plugins.workflow.modules.tipi.util.TipiConstants;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.tipi.generated.CreerPaiementSecuriseRequest;
import fr.paris.tipi.generated.RecupererDetailPaiementSecuriseRequest;
import fr.paris.vdp.tipi.create.url.enumeration.PaymentType;
import fr.paris.vdp.tipi.create.url.webservice.CreateURLWebService;
import fr.paris.vdp.tipi.create.url.webservice.ParametresPaiementTipi;

/**
 * 
 * Service for communication with tipi service
 *
 */

public class TipiServiceCaller implements ITipiServiceCaller
{
    // CONTANTS
    private static final String ACTIVATION_PAYMENT = "X";
    private static final String REAL_PAYMENT = "W";

    /**
     * Constructor
     */
    public TipiServiceCaller( )
    {
        setCertificateValues( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getIdop( String strEmail, String strRefDet, int nAmount, String strNotificationUrl )
    {

        final String strUrlTipiWebservice = AppPropertiesService.getProperty( TipiConstants.PROPERTY_URL_TIPI_WEBSERVICE );

        CreerPaiementSecuriseRequest request = createRequest( strEmail, strRefDet, nAmount, strNotificationUrl );

        String strIdop = StringUtils.EMPTY;

        try
        {

            strIdop = new CreateURLWebService( ).appelWebServiceCreerPaiement( strUrlTipiWebservice, request );

        }
        catch( RemoteException | ServiceException e )
        {
            AppLogService.error( "Error when getting the idop", e );
        }

        return strIdop;

    }

    /**
     * Method to create a payment request
     * 
     * @param strEmail
     *            the email
     * @param strRefDet
     *            the RefDet
     * @param nAmount
     *            the amount
     * @param strNotificationUrl
     *            the notification URL
     * @return Request
     *
     */
    private CreerPaiementSecuriseRequest createRequest( String strEmail, String strRefDet, int nAmount, String strNotificationUrl )
    {
        CreerPaiementSecuriseRequest request = new CreerPaiementSecuriseRequest( );
        Calendar calendar = Calendar.getInstance( );

        request.setMel( strEmail );
        request.setMontant( String.valueOf( nAmount ) );
        request.setRefdet( strRefDet );
        request.setNumcli( AppPropertiesService.getProperty( TipiConstants.PROPERTY_REFERENCE_CLIENT ) );
        request.setUrlnotif( strNotificationUrl );
        request.setUrlredirect( AppPropertiesService.getProperty( TipiConstants.PROPERTY_URL_REDIRECT ) );
        request.setExer( String.valueOf( calendar.get( Calendar.YEAR ) ) );
        request.setObjet( AppPropertiesService.getProperty( TipiConstants.PROPERTY_TIPI_SUBJECT ) );

        String saisie = AppPropertiesService.getProperty( TipiConstants.PROPERTY_PAYMENT_TYPE );

        if ( REAL_PAYMENT.equalsIgnoreCase( saisie ) )
        {
            request.setSaisie( PaymentType.PRODUCTION_WS.getStringValues( ) );
        }
        else
            if ( ACTIVATION_PAYMENT.equalsIgnoreCase( saisie ) )
            {
                request.setSaisie( PaymentType.ACTIVATION.getStringValues( ) );
            }
            else
            {
                request.setSaisie( PaymentType.TEST.getStringValues( ) );
            }

        return request;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ServiceException
     * @throws RemoteException
     * @throws FonctionnelleErreur
     * @throws TechProtocolaireErreur
     * @throws TechIndisponibiliteErreur
     * @throws TechDysfonctionnementErreur
     */
    @Override
    public String getTransactionResult( String strIdop ) throws TransactionResultException
    {
        String transactionResult = null;

        String strUrlWebservice = AppPropertiesService.getProperty( TipiConstants.PROPERTY_URL_TIPI_WEBSERVICE );

        RecupererDetailPaiementSecuriseRequest request = new RecupererDetailPaiementSecuriseRequest( );

        request.setIdOp( strIdop );

        try
        {
            ParametresPaiementTipi parameters = new CreateURLWebService( ).appelWebserviceDetailPaiement( request, strUrlWebservice );
            transactionResult = parameters.getResultrans( );

        }
        catch( RemoteException | ServiceException e )
        {
            throw new TransactionResultException( "Error when getting the transaction result", e );
        }

        if ( transactionResult == null )
        {
            throw new TransactionResultException( "The transaction result returned by the TIPI service is null" );
        }

        return transactionResult;
    }

    /**
     * 
     * Method to set the certificate to TIPI
     * 
     */
    private void setCertificateValues( )
    {
        if ( AppPropertiesService.getProperty( TipiConstants.PROPERTY_KEYSTORE ).isEmpty( ) )
        {
            File file = new File( getClass( ).getClassLoader( ).getResource( "security/cacerts" ).getFile( ) );
            System.setProperty( "javax.net.ssl.trustStore", file.getAbsolutePath( ) );
            System.setProperty( "javax.net.ssl.keyStore", file.getAbsolutePath( ) );
        }
        else
        {
            System.setProperty( "javax.net.ssl.trustStore", AppPropertiesService.getProperty( TipiConstants.PROPERTY_TRUSTSTORE ) );
            System.setProperty( "javax.net.ssl.keyStore", AppPropertiesService.getProperty( TipiConstants.PROPERTY_KEYSTORE ) );
        }

        System.setProperty( "javax.net.ssl.trustStorePassword", AppPropertiesService.getProperty( TipiConstants.PROPERTY_TRUSTSTORE_PASSWORD ) );
        System.setProperty( "javax.net.ssl.keyStorePassword", AppPropertiesService.getProperty( TipiConstants.PROPERTY_KEYSTORE_PASSWORD ) );
    }

}
