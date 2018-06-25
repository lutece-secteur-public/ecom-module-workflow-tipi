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
package fr.paris.lutece.plugins.workflow.modules.tipi.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetHistory;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.TipiRefDetHistoryService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.TipiService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.TipiServiceCaller;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.url.ITipiUrlService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.vdp.tipi.create.url.enumeration.TransactionResult;

/**
 * This class is a controller for the TIPI payment
 *
 */
public class TipiPaymentJspBean
{
    // Parameters
    private static final String PARAMETER_ID_HISTORY = "id_history";

    // Messages
    private static final String MESSAGE_REFDET_ALREADY_PAID = "module.workflow.tipi.message.refdet.already.paid";

    // Beans
    private static final String BEAN_URL_SERVICE = "workflow-tipi.tipiUrlService";

    // Other constants
    private static final int ID_NOT_SET = -1;

    // Services
    private final ITipiUrlService _tipiUrlService;
    private TipiService _tipiService;
    private TipiRefDetHistoryService _tipiRefDetHistoryService;
    private TipiServiceCaller _tipiServiceCaller;

    /**
     * Constructor
     */
    public TipiPaymentJspBean( )
    {
        _tipiUrlService = SpringContextService.getBean( BEAN_URL_SERVICE );
        _tipiService = SpringContextService.getBean( TipiService.BEAN_NAME );
        _tipiServiceCaller = SpringContextService.getBean( TipiServiceCaller.BEAN_NAME );
        _tipiRefDetHistoryService = SpringContextService.getBean( TipiRefDetHistoryService.BEAN_NAME );
    }

    /**
     * Constructor
     * 
     * @param tipiUrlService
     *            the TIPI URL service
     */
    public TipiPaymentJspBean( ITipiUrlService tipiUrlService )
    {
        _tipiUrlService = tipiUrlService;
    }

    /**
     * Do process the TIPI payment
     * 
     * @param request
     *            the request
     * @return the URL of the TIPI service
     * @throws SiteMessageException
     *             if there is an error during the process
     */
    public String doProcessPayment( HttpServletRequest request ) throws SiteMessageException
    {
        String strTipiUrl = StringUtils.EMPTY;

        if ( _tipiUrlService.isPaymentUrlAuthenticated( request ) )
        {
            strTipiUrl = doProcessPaymentInternal( request );
        }
        else
        {
            SiteMessageService.setMessage( request, Messages.USER_ACCESS_DENIED, SiteMessage.TYPE_STOP );
        }

        return strTipiUrl;
    }

    /**
     * Do process the TIPI payment. Internal method.
     * 
     * @param request
     *            the request
     * @return the URL of the TIPI service
     * @throws SiteMessageException
     *             if there is an error during the process
     */
    private String doProcessPaymentInternal( HttpServletRequest request ) throws SiteMessageException
    {
        int nIdHistory = NumberUtils.toInt( request.getParameter( PARAMETER_ID_HISTORY ), ID_NOT_SET );

        TipiRefDetHistory tipiRefDetHistory = _tipiRefDetHistoryService.findByPrimaryKey( nIdHistory );

        String refDetHistory = tipiRefDetHistory.getRefDet( );

        Tipi tipi = _tipiService.findByPrimaryKey( refDetHistory );

        if ( isTipiPaymentAlreadyPaid( tipi ) )
        {
            SiteMessageService.setMessage( request, MESSAGE_REFDET_ALREADY_PAID, SiteMessage.TYPE_INFO );
        }

        String email = tipi.getEmail( );
        int amount = tipi.getAmount( );
        String refDet = tipi.getRefDet( );

        String strIdop = _tipiServiceCaller.getIdop( email, refDet, amount );

        tipi.setIdOp( strIdop );
        _tipiService.update( tipi );

        return _tipiUrlService.generateTipiUrl( tipi );
    }

    /**
     * Tests whether the TIPI payment has already been paid.
     * 
     * @param tipi
     *            the TIPI object to test
     * @return {@code true} if the TIPI payment has already been paid, {@code false} otherwise
     */
    private boolean isTipiPaymentAlreadyPaid( Tipi tipi )
    {
        return tipi.getTransactionResult( ) != null && tipi.getTransactionResult( ).equalsIgnoreCase( TransactionResult.PAYMENT_SUCCEEDED.getValueStr( ) );
    }
}
