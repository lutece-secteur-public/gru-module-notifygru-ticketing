/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
package fr.paris.lutece.plugins.gru.modules.providerticketing.implementation;



import fr.paris.lutece.plugins.directory.business.IEntry;
import fr.paris.lutece.plugins.directory.service.DirectoryPlugin;
import fr.paris.lutece.plugins.ticketing.business.ContactMode;
import fr.paris.lutece.plugins.ticketing.business.ContactModeHome;
import fr.paris.lutece.plugins.ticketing.business.Ticket;
import fr.paris.lutece.plugins.ticketing.business.TicketCategory;
import fr.paris.lutece.plugins.ticketing.business.TicketCategoryHome;
import fr.paris.lutece.plugins.ticketing.business.TicketDomain;
import fr.paris.lutece.plugins.ticketing.business.TicketDomainHome;
import fr.paris.lutece.plugins.ticketing.business.TicketHome;
import fr.paris.lutece.plugins.ticketing.business.TicketType;
import fr.paris.lutece.plugins.ticketing.business.TicketTypeHome;
import fr.paris.lutece.plugins.ticketing.business.UserTitle;
import fr.paris.lutece.plugins.ticketing.business.UserTitleHome;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.AbstractServiceProvider;



import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;






import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;



public class ProviderTicketing extends AbstractServiceProvider
{
    private static final String TEMPLATE_FREEMARKER_LIST = "admin/plugins/workflow/modules/notifygru/providerticketing/freemarker_list.html";
    @Inject
    private IResourceHistoryService _resourceHistoryService;
    
    private Ticket _ticket;
 
  
    //config provider  
    private String _strStatusTexte;

    Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );

    @Override
    public String getUserEmail( int nIdResource )
    {
    	
    	 ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResource );
     	// _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

     	 int nIdTicket = resourceHistory.getIdResource();
         _ticket = TicketHome.findByPrimaryKey( nIdTicket );
       
        return _ticket.getEmail();
    }

    @Override
    public String getUserGuid( int nIdResource )
    {  
    	
    	return "getUserGuid Provider";  
    	
    }

 

    @Override
    public String getInfosHelp( Locale local )
    {
        
    	
    
    	
    	 
    	 
        Map<String, Object> model = new HashMap<String, Object>(  );
        _ticket = new Ticket(  );
       
        model.put( ProviderTicketingConstants.MARK_GUID,  "");
        model.put( ProviderTicketingConstants.MARK_FIRSTNAME,  "");
        model.put( ProviderTicketingConstants.MARK_LASTNAME,  "");
        model.put( ProviderTicketingConstants.MARK_FIXED_PHONE,  "");
        model.put( ProviderTicketingConstants.MARK_MOBILE_PHONE,  "");
        model.put( ProviderTicketingConstants.MARK_EMAIL, "");
        model.put( ProviderTicketingConstants.MARK_TICKET,  _ticket);
        model.put( ProviderTicketingConstants.MARK_USER_TITLES,  "");
        model.put( ProviderTicketingConstants.MARK_TICKET_TYPES, "");
        model.put( ProviderTicketingConstants.MARK_TICKET_DOMAINS, "");
        model.put( ProviderTicketingConstants.MARK_TICKET_CATEGORIES, "");      
        model.put( ProviderTicketingConstants.MARK_CONTACT_MODES, "");
        model.put( ProviderTicketingConstants.MARK_COMMENT, "");
      

        @SuppressWarnings("deprecation")
		HtmlTemplate t = AppTemplateService.getTemplateFromStringFtl( AppTemplateService.getTemplate( 
                    TEMPLATE_FREEMARKER_LIST, local, model ).getHtml(  ), local, model );

        String strResourceInfo = t.getHtml(  );

        return strResourceInfo;
        
    
    }

    @Override
    public Map<String, Object> getInfos( int nIdResource )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        
        
        
   
      
        
        if ( nIdResource > 0 )
        {
        	
        	 ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResource );
        	// _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

        	 int nIdTicket = resourceHistory.getIdResource();
            _ticket = TicketHome.findByPrimaryKey( nIdTicket );
            
            
            UserTitle usertitle= UserTitleHome.findByPrimaryKey(_ticket.getIdUserTitle());
            
            TicketType typeTicket = TicketTypeHome.findByPrimaryKey(_ticket.getIdTicketType());
            
           
            TicketDomain typeDomaine = TicketDomainHome.findByPrimaryKey(_ticket.getIdTicketDomain());
            
            TicketCategory typeCategory= TicketCategoryHome.findByPrimaryKey(_ticket.getIdTicketCategory());
            
            ContactMode typeContactMode= ContactModeHome.findByPrimaryKey(_ticket.getIdContactMode());
            
            
        	   model.put( ProviderTicketingConstants.MARK_GUID,  111);
               model.put( ProviderTicketingConstants.MARK_FIRSTNAME,  _ticket.getFirstname());
               model.put( ProviderTicketingConstants.MARK_LASTNAME,  _ticket.getLastname());
               model.put( ProviderTicketingConstants.MARK_FIXED_PHONE,  _ticket.getFixedPhoneNumber());
               model.put( ProviderTicketingConstants.MARK_MOBILE_PHONE,  _ticket.getMobilePhoneNumber());
               model.put( ProviderTicketingConstants.MARK_EMAIL,  _ticket.getEmail());
               model.put( ProviderTicketingConstants.MARK_TICKET,  _ticket);
               model.put( ProviderTicketingConstants.MARK_USER_TITLES,  _ticket.getUserTitle());
               model.put( ProviderTicketingConstants.MARK_TICKET_TYPES,  _ticket.getTicketType());
               model.put( ProviderTicketingConstants.MARK_TICKET_DOMAINS, _ticket.getTicketDomain());
               model.put( ProviderTicketingConstants.MARK_TICKET_CATEGORIES, _ticket.getTicketCategory());        
               model.put( ProviderTicketingConstants.MARK_CONTACT_MODES, _ticket.getContactMode());
               model.put( ProviderTicketingConstants.MARK_COMMENT,  _ticket.getTicketComment());
        }
        else
        {
            model.put( ProviderTicketingConstants.MARK_GUID,  "");
            model.put( ProviderTicketingConstants.MARK_FIRSTNAME,  "");
            model.put( ProviderTicketingConstants.MARK_LASTNAME,  "");
            model.put( ProviderTicketingConstants.MARK_FIXED_PHONE,  "");
            model.put( ProviderTicketingConstants.MARK_MOBILE_PHONE,  "");
            model.put( ProviderTicketingConstants.MARK_EMAIL, "");
            model.put( ProviderTicketingConstants.MARK_TICKET,  _ticket);
            model.put( ProviderTicketingConstants.MARK_USER_TITLES,  "");
            model.put( ProviderTicketingConstants.MARK_TICKET_TYPES, "");
            model.put( ProviderTicketingConstants.MARK_TICKET_DOMAINS, "");
            model.put( ProviderTicketingConstants.MARK_TICKET_CATEGORIES, "");      
            model.put( ProviderTicketingConstants.MARK_CONTACT_MODES, "");
            model.put( ProviderTicketingConstants.MARK_COMMENT, "");
        }

        return model;
    }

     @Override
    public String getOptionalMobilePhoneNumber( int nIdResource )
    {
    	 ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResource );
      	// _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

      	 int nIdTicket = resourceHistory.getIdResource();
          _ticket = TicketHome.findByPrimaryKey( nIdTicket );
        
         return _ticket.getMobilePhoneNumber();
    }

   

   @Override
    public int getOptionalDemandId( int nIdResource )
    {
	   ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResource );
     	// _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

     	 int nIdTicket = resourceHistory.getIdResource();
         _ticket = TicketHome.findByPrimaryKey( nIdTicket );
       
        return _ticket.getId();
    }

  
   

@Override
    public int getOptionalDemandIdType( int nIdResource )
    {
	ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResource );
 	// _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

 	 int nIdTicket = resourceHistory.getIdResource();
     _ticket = TicketHome.findByPrimaryKey( nIdTicket );
   
    return _ticket.getIdTicketType();
    }


    public Boolean isIdDemandTypeAvailable( int nIdResource )
    {
        return true;
    }


  



    public String getStatusTexte(  )
    {
    	return this._strStatusTexte;
    }

    public void setStatusTexte( String _strStatusTexte )
    {
        this._strStatusTexte = _strStatusTexte;
    }

   
}
