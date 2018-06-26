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

import fr.paris.lutece.plugins.workflow.modules.tipi.exception.TransactionResultException;
import fr.paris.lutece.plugins.workflow.modules.tipi.util.TipiConstants;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.tipi.generated.CreerPaiementSecuriseRequest;
import fr.paris.vdp.tipi.create.url.enumeration.PaymentType;
import fr.paris.vdp.tipi.create.url.webservice.CreateURLWebService;

/**
 * 
 * Service for communication with tipi service
 *
 */

public class TipiServiceCaller implements ITipiServiceCaller
{
    // CONTANTS
    private static final String X = "X";
    private static final String W = "W";

    /**
     * {@inheritDoc }
     */
    @Override
    public String getIdop( String email, String refDet, int amount )
    {

        final String urlWsdl = AppPropertiesService.getProperty( TipiConstants.PROPERTY_URLWDSL );

        CreerPaiementSecuriseRequest request = createRequest( email, refDet, amount );

        String strIdop = null;

        try
        {

            strIdop = new CreateURLWebService( ).appelWebServiceCreerPaiement( urlWsdl, request );

        }
        catch( RemoteException | ServiceException e )
        {
            AppLogService.debug( "url du webservice : " + urlWsdl );
            AppLogService.debug( "parametre de la requete : " + request );
        }

        return strIdop;

    }

    /**
     * Method to create a payment request
     * 
     * @param email
     * @param refDet
     * @param amount
     * @return Request
     */
    private CreerPaiementSecuriseRequest createRequest( String email, String refDet, int amount )
    {
        CreerPaiementSecuriseRequest request = new CreerPaiementSecuriseRequest( );
        Calendar calendar = Calendar.getInstance( );

        request.setMel( email );
        request.setMontant( String.valueOf( ( amount ) ) );
        request.setRefdet( refDet );
        request.setNumcli( AppPropertiesService.getProperty( TipiConstants.PROPERTY_REFERENCE_CLIENT ) );
        request.setUrlnotif( AppPropertiesService.getProperty( TipiConstants.PROPERTY_URL_NOTIF ) );
        request.setUrlredirect( AppPropertiesService.getProperty( TipiConstants.PROPERTY_URL_REDIRECT ) );
        request.setExer( String.valueOf( calendar.get( Calendar.YEAR ) ) );
        request.setObjet( AppPropertiesService.getProperty( TipiConstants.PROPERTY_TIPI_OBJET ) );

        String saisie = AppPropertiesService.getProperty( TipiConstants.PROPERTY_PAYMENT_TYPE );

        if ( W.equalsIgnoreCase( saisie ) )
        {
            request.setSaisie( PaymentType.PRODUCTION_WS.getStringValues( ) );
        }
        else
            if ( X.equalsIgnoreCase( saisie ) )
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
     */
    @Override
    public String getTransactionResult( String strIdop ) throws TransactionResultException
    {
        // TODO : implement this method
        return null;
    }

    /**
     * 
     * Method to set the certificate to TIPI
     * 
     */
    // TODO use this method
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
