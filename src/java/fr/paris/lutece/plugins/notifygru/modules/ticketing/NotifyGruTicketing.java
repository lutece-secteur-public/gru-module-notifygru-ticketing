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
package fr.paris.lutece.plugins.notifygru.modules.ticketing;

import fr.paris.lutece.plugins.ticketing.business.Ticket;
import fr.paris.lutece.plugins.ticketing.business.TicketHome;
import fr.paris.lutece.plugins.ticketing.service.TicketingPlugin;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.AbstractServiceProvider;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

public class NotifyGruTicketing extends AbstractServiceProvider {

    private static final String TEMPLATE_FREEMARKER_LIST = "admin/plugins/workflow/modules/notifygru/ticketing/freemarker_list.html";
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    private Ticket _ticket;

    //config provider  
    private String _strStatusTexte;
    private static Plugin _plugin = PluginService.getPlugin(TicketingPlugin.PLUGIN_NAME);

    @Override
    public String getUserEmail(int nIdResource) {

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey(nIdResource);
        // _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

        int nIdTicket = resourceHistory.getIdResource();
        _ticket = TicketHome.findByPrimaryKey(nIdTicket);

        return _ticket.getEmail();
    }

    @Override
    public String getUserGuid(int nIdResource) {

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey(nIdResource);
        // _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

        int nIdTicket = resourceHistory.getIdResource();
        _ticket = TicketHome.findByPrimaryKey(nIdTicket);

        return _ticket.getGuid();

    }

    @Override
    public String getInfosHelp(Locale local) {

        _ticket = new Ticket();

        Map<String, Object> model = buildModelNotifyGruTicketing(_ticket);

        @SuppressWarnings("deprecation")
        HtmlTemplate t = AppTemplateService.getTemplateFromStringFtl(AppTemplateService.getTemplate(
                TEMPLATE_FREEMARKER_LIST, local, model).getHtml(), local, model);

        String strResourceInfo = t.getHtml();

        return strResourceInfo;

    }

    /**
     */
    private Map<String, Object> buildModelNotifyGruTicketing(Ticket ticket) {

        Map<String, Object> model = new HashMap<String, Object>();

        model.put(Constants.MARK_GUID, (ticket.getGuid() != null) ? ticket.getGuid() : "");
        model.put(Constants.MARK_FIRSTNAME, (ticket.getFirstname() != null) ? ticket.getFirstname() : "");
        model.put(Constants.MARK_LASTNAME, (ticket.getLastname() != null) ? ticket.getLastname() : "");
        model.put(Constants.MARK_FIXED_PHONE, (ticket.getFixedPhoneNumber() != null) ? ticket.getFixedPhoneNumber() : "");
        model.put(Constants.MARK_MOBILE_PHONE, (ticket.getMobilePhoneNumber() != null) ? ticket.getMobilePhoneNumber() : "");
        model.put(Constants.MARK_EMAIL, (ticket.getEmail() != null) ? ticket.getEmail() : "");
        model.put(Constants.MARK_REFERENCE, (ticket.getReference() != null) ? ticket.getReference() : "");
        model.put(Constants.MARK_TICKET, ticket);
        model.put(Constants.MARK_USER_TITLES, (ticket.getUserTitle() != null) ? ticket.getUserTitle() : "");
        model.put(Constants.MARK_TICKET_TYPES, (ticket.getTicketType() != null) ? ticket.getTicketType() : "");
        model.put(Constants.MARK_TICKET_DOMAINS, (ticket.getTicketDomain() != null) ? ticket.getTicketDomain() : "");
        model.put(Constants.MARK_TICKET_CATEGORIES, (ticket.getTicketCategory() != null) ? ticket.getTicketCategory() : "");
        model.put(Constants.MARK_CONTACT_MODES, (ticket.getContactMode() != null) ? ticket.getContactMode() : "");
        model.put(Constants.MARK_COMMENT, (ticket.getTicketComment() != null) ? ticket.getTicketComment() : "");
        String strUrlCompleted = AppPropertiesService.getProperty(Constants.PROPERTIES_URL_COMPLETE);
        model.put(Constants.MARK_URL_COMPLETED, ((ticket.getGuid() != null) ? parseUrlCompleted(strUrlCompleted, ticket.getGuid()) : ""));
        model.put(Constants.MARK_USER_MESSAGE, "Message : User message");
        return model;
    }

    private String parseUrlCompleted(String strUrl, String strGuid) {        
        String strUrlwithGuid = strUrl.replace("${" + Constants.MARK_GUID + "}", strGuid);
        return strUrlwithGuid;
    }

    @Override
    public Map<String, Object> getInfos(int nIdResource) {
        Map<String, Object> model = new HashMap<String, Object>();

        if (nIdResource > 0) {

            ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey(nIdResource);

            int nIdTicket = resourceHistory.getIdResource();
            _ticket = (TicketHome.findByPrimaryKey(nIdTicket) != null) ? TicketHome.findByPrimaryKey(nIdTicket) : (new Ticket());
            model = buildModelNotifyGruTicketing(_ticket);

        } else {
            model = buildModelNotifyGruTicketing(new Ticket());

        }

        return model;
    }

    @Override
    public String getOptionalMobilePhoneNumber(int nIdResource) {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey(nIdResource);
        // _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

        int nIdTicket = resourceHistory.getIdResource();
        _ticket = TicketHome.findByPrimaryKey(nIdTicket);

        return _ticket.getMobilePhoneNumber();
    }

    @Override
    public int getOptionalDemandId(int nIdResource) {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey(nIdResource);
        // _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

        int nIdTicket = resourceHistory.getIdResource();
        _ticket = TicketHome.findByPrimaryKey(nIdTicket);

        return _ticket.getId();

    }

    @Override
    public int getOptionalDemandIdType(int nIdResource) {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey(nIdResource);
        // _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource(  ), pluginTicketing );

        int nIdTicket = resourceHistory.getIdResource();
        _ticket = TicketHome.findByPrimaryKey(nIdTicket);

        return _ticket.getIdTicketType();
    }

    public Boolean isIdDemandTypeAvailable(int nIdResource) {
        return true;
    }

    public String getStatusTexte() {
        return this._strStatusTexte;
    }

    public void setStatusTexte(String _strStatusTexte) {
        this._strStatusTexte = _strStatusTexte;
    }

}
