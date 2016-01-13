/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.gru.modules.providerticketing.implementation;

import fr.paris.lutece.plugins.genericattributes.business.Entry;
import fr.paris.lutece.plugins.ticketing.business.TicketForm;
import java.util.List;

/**
 *
 * @author root
 */
public class FormCategoryTicket {
    
    private TicketForm _fForm;
    private List<Entry> _lEntity;
 

 

    public TicketForm getForm() {
        return _fForm;
    }

    public void setForm(TicketForm _fForm) {
        this._fForm = _fForm;
    }

    public List<Entry> getEntity() {
        return _lEntity;
    }

    public void setEntity(List<Entry> _lEntity) {
        this._lEntity = _lEntity;
    }
    
    
    
    
}
