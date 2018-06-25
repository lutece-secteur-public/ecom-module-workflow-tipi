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
package fr.paris.lutece.plugins.workflow.modules.tipi.business.task;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import fr.paris.lutece.plugins.workflowcore.business.config.TaskConfig;

/**
 * 
 * Task object to expose the state after payment for an successful failed or canceled payment
 * 
 */
public class TaskTipiConfig extends TaskConfig
{
    @NotNull
    @Min( 1 )
    private int _nIdStateAfterSuccessPayment;
    private int _nIdStateAfterFailurePayment;
    private int _nIdStateAfterCanceledPayment;

    /**
     * Get the state after a success payment
     * 
     * @return the _nIdStateAfterSuccessPayment
     */
    public int getIdStateAfterSuccessPayment( )
    {
        return _nIdStateAfterSuccessPayment;
    }

    /**
     * Set the state after a success payment
     *
     * @param idStateAfterSuccessPayment
     *            the idStateAfterSuccessPayment to set
     */
    public void setIdStateAfterSuccessPayment( int idStateAfterSuccessPayment )
    {
        _nIdStateAfterSuccessPayment = idStateAfterSuccessPayment;
    }

    /**
     * Get the state after a Failed payment
     * 
     * @return the _nIdStateAfterFailurePayment
     */
    public int getIdStateAfterFailurePayment( )
    {
        return _nIdStateAfterFailurePayment;
    }

    /**
     * Set the state after a success payment
     *
     * @param idStateAfterFailurePayment
     *            the idStateAfterFailurePayment to set
     */
    public void setIdStateAfterFailurePayment( int idStateAfterFailurePayment )
    {
        _nIdStateAfterFailurePayment = idStateAfterFailurePayment;
    }

    /**
     * Get the state after a canceled payment
     * 
     * @return the _nIdStateAftercanceledPayment
     */
    public int getIdStateAfterCanceledPayment( )
    {
        return _nIdStateAfterCanceledPayment;
    }

    /**
     * Set the state after a canceled payment
     * 
     * @param idStateAfterCanceledPayment
     *            the idStateAftercanceledPayment to set
     */
    public void setIdStateAfterCanceledPayment( int idStateAfterCanceledPayment )
    {
        _nIdStateAfterCanceledPayment = idStateAfterCanceledPayment;
    }

}
