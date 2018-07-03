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
package fr.paris.lutece.plugins.workflow.modules.tipi.daemon;

import java.util.List;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;
import fr.paris.lutece.plugins.workflow.modules.tipi.exception.TransactionResultException;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiPaymentService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiService;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;

/**
 * This class represents a daemon for the TIPI payments. It checks payments for which no TIPI notification has been sent yet. The goal is to update these
 * payments.
 *
 */
public class TipiPaymentDaemon extends Daemon
{
    private static final String LOG_NUMBER_TIPI_PAYMENT = "Number of updated TIPI payments : ";

    private final ITipiService _tipiService;
    private final ITipiPaymentService _tipiPaymentService;

    /**
     * Constructor
     */
    public TipiPaymentDaemon( )
    {
        super( );

        _tipiPaymentService = SpringContextService.getBean( ITipiPaymentService.BEAN_NAME );
        _tipiService = SpringContextService.getBean( ITipiService.BEAN_NAME );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run( )
    {
        List<Tipi> listNotNotifiedPayment = findNotNotifiedPayments( );
        int nNumberOfUpdatedPayments = updateProcessedPayments( listNotNotifiedPayment );

        setLastRunLogs( LOG_NUMBER_TIPI_PAYMENT + nNumberOfUpdatedPayments );

    }

    /**
     * Finds TIPI objects for which no notification has been sent
     * 
     * @return the list of TIPI objects
     */
    private List<Tipi> findNotNotifiedPayments( )
    {
        return _tipiService.findNotNotifiedPayments( );
    }

    /**
     * Updates the specified processed payments
     * 
     * @param listProcessedPayment
     *            the list of TIPI object
     * @return the number of updated TIPI objects
     */
    private int updateProcessedPayments( List<Tipi> listProcessedPayment )
    {
        int nNumberOfUpdatedPayments = 0;

        for ( Tipi tipi : listProcessedPayment )
        {
            try
            {
                _tipiPaymentService.paymentProcessed( tipi );
                nNumberOfUpdatedPayments++;
            }
            catch( TransactionResultException e )
            {
                AppLogService.error( "Cannot get the transaction result for the IdOp " + tipi.getIdOp( ) );
            }
        }

        return nNumberOfUpdatedPayments;
    }

}
