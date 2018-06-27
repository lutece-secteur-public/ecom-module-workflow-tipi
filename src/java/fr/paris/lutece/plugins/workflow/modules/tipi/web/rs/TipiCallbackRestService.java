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
package fr.paris.lutece.plugins.workflow.modules.tipi.web.rs;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.plugins.workflow.modules.tipi.exception.TipiNotFoundException;
import fr.paris.lutece.plugins.workflow.modules.tipi.exception.TransactionResultException;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiPaymentService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.TipiPlugin;
import fr.paris.lutece.portal.service.util.AppLogService;

/**
 * This class represents a web service called by the TIPI notification mechanism
 *
 */
@Path( RestConstants.BASE_PATH + TipiPlugin.PLUGIN_NAME + TipiCallbackRestService.PATH_SERVICE )
public class TipiCallbackRestService
{
    protected static final String PATH_SERVICE = "/callback/";
    private static final String PATH_PAYMENT = "payment";

    private final ITipiPaymentService _tipiPaymentService;

    /**
     * Constructor
     * 
     * @param tipiPaymentService
     *            the TIPI payment service
     */
    @Inject
    public TipiCallbackRestService( ITipiPaymentService tipiPaymentService )
    {
        _tipiPaymentService = tipiPaymentService;
    }

    /**
     * Method called by the TIPI notification mechanism
     * 
     * @param strIdop
     *            the idop
     * @return the response
     */
    @GET
    @Path( PATH_PAYMENT )
    public Response paymentNotified( @QueryParam( "IdOp" ) String strIdop )
    {
        try
        {
            _tipiPaymentService.paymentProcessed( strIdop );
        }
        catch( TransactionResultException e )
        {
            AppLogService.error( "Cannot get the transaction result for the IdOp " + strIdop, e );
        }
        catch( TipiNotFoundException e )
        {
            AppLogService.error( "There is no TIPI object associated to the IdOp " + strIdop, e );
        }

        return Response.ok( ).build( );
    }
}