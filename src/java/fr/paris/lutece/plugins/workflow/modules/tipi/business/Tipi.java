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
package fr.paris.lutece.plugins.workflow.modules.tipi.business;

import fr.paris.lutece.plugins.workflowcore.business.config.TaskConfig;

/**
 * 
 * TIPI object for communication with TIPI
 *
 */
public class Tipi extends TaskConfig
{

    private int _nRefDet;
    private double _dAmount;
    private String _strEmail;
    private String _strIdOp;
    private Boolean _bResultTransaction;

    /**
     * Get the reference of the debt
     * 
     * @return the _nRefDet
     */
    public int getRefDet( )
    {
        return _nRefDet;
    }

    /**
     * Set the reference of the debt
     * 
     * @param refDet
     *            the _nRefDet to set
     */
    public void setRefDet( int refDet )
    {
        _nRefDet = refDet;
    }

    /**
     * Get the Amount
     * 
     * @return the _dAmount
     */
    public double getAmount( )
    {
        return _dAmount;
    }

    /**
     * Set the reference of the debt
     * 
     * @param amount
     *            the _dAmount to set
     */
    public void setAmount( double amount )
    {
        _dAmount = amount;
    }

    /**
     * Get the email
     * 
     * @return the _strEmail
     */
    public String getEmail( )
    {
        return _strEmail;
    }

    /**
     * Set the email
     * 
     * @param email
     *            the _strEmail to set
     */
    public void setEmail( String email )
    {
        _strEmail = email;
    }

    /**
     * Get the Idop number
     * 
     * @return the _strIdOp
     */
    public String getIdOp( )
    {
        return _strIdOp;
    }

    /**
     * Set the Idop number
     * 
     * @param idOp
     *            the _strIdOp to set
     */
    public void setIdOp( String idOp )
    {
        _strIdOp = idOp;
    }

    /**
     * Get the result of the transaction
     * 
     * @return the _bResultTransaction
     */
    public Boolean getResultTransaction( )
    {
        return _bResultTransaction;
    }

    /**
     * Set the result of the transaction
     * 
     * @param resultTransaction
     *            the _bResultTransaction to set
     */
    public void setResultTransaction( Boolean resultTransaction )
    {
        _bResultTransaction = resultTransaction;
    }

}
