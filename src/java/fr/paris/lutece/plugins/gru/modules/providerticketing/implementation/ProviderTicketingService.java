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

import fr.paris.lutece.plugins.directory.business.Directory;
import fr.paris.lutece.plugins.directory.business.DirectoryHome;
import fr.paris.lutece.plugins.directory.business.EntryFilter;
import fr.paris.lutece.plugins.directory.business.EntryHome;
import fr.paris.lutece.plugins.directory.business.EntryTypeGeolocation;
import fr.paris.lutece.plugins.directory.business.IEntry;
import fr.paris.lutece.plugins.directory.business.Record;
import fr.paris.lutece.plugins.directory.business.RecordField;
import fr.paris.lutece.plugins.directory.business.RecordFieldFilter;
import fr.paris.lutece.plugins.directory.business.RecordFieldHome;
import fr.paris.lutece.plugins.directory.service.DirectoryPlugin;
import fr.paris.lutece.plugins.directory.utils.DirectoryUtils;
import fr.paris.lutece.plugins.workflow.modules.notifygru.business.TaskNotifyGruConfig;
import fr.paris.lutece.plugins.workflow.service.WorkflowPlugin;
import fr.paris.lutece.plugins.workflow.service.security.IWorkflowUserAttributesManager;
import fr.paris.lutece.plugins.workflow.service.taskinfo.TaskInfoManager;
import fr.paris.lutece.plugins.workflowcore.business.action.Action;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.plugins.workflowcore.business.state.StateFilter;
import fr.paris.lutece.plugins.workflowcore.service.action.IActionService;
import fr.paris.lutece.plugins.workflowcore.service.state.IStateService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.mailinglist.AdminMailingListService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * NotifyDirectoryService
 *
 */ 
public final class ProviderTicketingService implements IProviderTicketingService
{
//    /** The Constant BEAN_SERVICE. */
//    public static final String BEAN_SERVICE = "notifygru-providerdirectory.ProviderDirectoryService";
//
//    // SERVICES
//    @Inject
//    private IActionService _actionService;
//    @Inject
//    private IStateService _stateService;
//    @Inject
//    private IWorkflowUserAttributesManager _userAttributesManager;


//
//
//    @Override
//    public ReferenceList getMailingList( HttpServletRequest request )
//    {
//        ReferenceList refMailingList = new ReferenceList(  );
//        refMailingList.addItem( DirectoryUtils.CONSTANT_ID_NULL, StringUtils.EMPTY );
//        refMailingList.addAll( AdminMailingListService.getMailingLists( AdminUserService.getAdminUser( request ) ) );
//
//        return refMailingList;
//    }

//    @Override
//    public List<IEntry> getListEntries( int nidDirectory )
//    {
//        Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );
//        List<IEntry> listEntries = new ArrayList<IEntry>(  );
//        EntryFilter entryFilter = new EntryFilter(  );
//        entryFilter.setIdDirectory( nidDirectory );
//
//        listEntries = EntryHome.getEntryList( entryFilter, pluginDirectory );
//
//        return listEntries;
//    }
//
//    @Override
//    public ReferenceList getListEntriesUserGuid( int nidDirectory, Locale locale )
//    {
//        ReferenceList refenreceListEntries = new ReferenceList(  );
//        refenreceListEntries.addItem( DirectoryUtils.CONSTANT_ID_NULL, DirectoryUtils.EMPTY_STRING );
//
//        for ( IEntry entry : getListEntries( nidDirectory ) )
//        {
//            int nIdEntryType = entry.getEntryType(  ).getIdType(  );
//
//            if ( isEntryTypeUserGuidAccepted( nIdEntryType ) )
//            {
//                refenreceListEntries.addItem( entry.getPosition(  ), buildReferenceEntryToString( entry, locale ) );
//            }
//        }
//
//        return refenreceListEntries;
//    }
//
//    private String buildReferenceEntryToString( IEntry entry, Locale locale )
//    {
//        StringBuilder sbReferenceEntry = new StringBuilder(  );
//        sbReferenceEntry.append( entry.getPosition(  ) );
//        sbReferenceEntry.append( ProviderTicketingConstants.SPACE + ProviderTicketingConstants.OPEN_BRACKET );
//        sbReferenceEntry.append( entry.getTitle(  ) );
//        sbReferenceEntry.append( ProviderTicketingConstants.SPACE + ProviderTicketingConstants.HYPHEN +
//            ProviderTicketingConstants.SPACE );
//        sbReferenceEntry.append( I18nService.getLocalizedString( entry.getEntryType(  ).getTitleI18nKey(  ), locale ) );
//        sbReferenceEntry.append( ProviderTicketingConstants.CLOSED_BRACKET );
//
//        return sbReferenceEntry.toString(  );
//    }
//
//    @Override
//    public ReferenceList getListEntriesEmailSMS( int nidDirectory, Locale locale )
//    {
//        ReferenceList refenreceListEntries = new ReferenceList(  );
//        refenreceListEntries.addItem( DirectoryUtils.CONSTANT_ID_NULL, DirectoryUtils.EMPTY_STRING );
//
//        for ( IEntry entry : getListEntries( nidDirectory ) )
//        {
//            int nIdEntryType = entry.getEntryType(  ).getIdType(  );
//
//            if ( isEntryTypeEmailSMSAccepted( nIdEntryType ) )
//            {
//                refenreceListEntries.addItem( entry.getPosition(  ), buildReferenceEntryToString( entry, locale ) );
//            }
//        }
//
//        return refenreceListEntries;
//    }
//
//    @Override
//    public List<IEntry> getListEntriesFreemarker( int nidDirectory )
//    {
//        List<IEntry> listEntries = new ArrayList<IEntry>(  );
//
//        for ( IEntry entry : getListEntries( nidDirectory ) )
//        {
//            int nIdEntryType = entry.getEntryType(  ).getIdType(  );
//
//            if ( !isEntryTypeRefused( nIdEntryType ) )
//            {
//                listEntries.add( entry );
//            }
//        }
//
//        return listEntries;
//    }
//
//    @Override
//    public List<IEntry> getListEntriesFile( int nidDirectory, Locale locale )
//    {
//        List<IEntry> listEntries = new ArrayList<IEntry>(  );
//
//        for ( IEntry entry : getListEntries( nidDirectory ) )
//        {
//            int nIdEntryType = entry.getEntryType(  ).getIdType(  );
//
//            if ( isEntryTypeFileAccepted( nIdEntryType ) )
//            {
//                listEntries.add( entry );
//            }
//        }
//
//        return listEntries;
//    }
//
//    @Override
//    public String getEmail( int nPositionEmail, int nIdRecord, int nIdDirectory )
//    {
//        String strEmail = StringUtils.EMPTY;
//
//        strEmail = getRecordFieldValue( nPositionEmail, nIdRecord, nIdDirectory );
//
//        return strEmail;
//    }
//
//    @Override
//    public int getIdDemand( int nPositionDemand, int nIdRecord, int nIdDirectory )
//    {
//        String strIdDemand = StringUtils.EMPTY;
//
//        strIdDemand = getRecordFieldValue( nPositionDemand, nIdRecord, nIdDirectory );
//
//        int nId = -1;
//
//        try
//        {
//            nId = Integer.parseInt( strIdDemand );
//        }
//        catch ( NumberFormatException e )
//        {
//        }
//
//        return nId;
//    }
//
//    @Override
//    public int getIdDemandType( int nPositionDemandType, int nIdRecord, int nIdDirectory )
//    {
//        String strIdDemandType = StringUtils.EMPTY;
//
//        strIdDemandType = getRecordFieldValue( nPositionDemandType, nIdRecord, nIdDirectory );
//
//        int nId = -1;
//
//        try
//        {
//            nId = Integer.parseInt( strIdDemandType );
//        }
//        catch ( NumberFormatException e )
//        {
//        }
//
//        return nId;
//    }
//
//    @Override
//    public String getRecordFieldValue( int nPosition, int nIdRecord, int nIdDirectory )
//    {
//        String strRecordFieldValue = StringUtils.EMPTY;
//        Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );
//
//        // RecordField
//        EntryFilter entryFilter = new EntryFilter(  );
//        entryFilter.setPosition( nPosition );
//        entryFilter.setIdDirectory( nIdDirectory );
//
//        List<IEntry> listEntries = EntryHome.getEntryList( entryFilter, pluginDirectory );
//
//        if ( ( listEntries != null ) && !listEntries.isEmpty(  ) )
//        {
//            IEntry entry = listEntries.get( 0 );
//            RecordFieldFilter recordFieldFilterEmail = new RecordFieldFilter(  );
//            recordFieldFilterEmail.setIdDirectory( nIdDirectory );
//            recordFieldFilterEmail.setIdEntry( entry.getIdEntry(  ) );
//            recordFieldFilterEmail.setIdRecord( nIdRecord );
//
//            List<RecordField> listRecordFields = RecordFieldHome.getRecordFieldList( recordFieldFilterEmail,
//                    pluginDirectory );
//
//            if ( ( listRecordFields != null ) && !listRecordFields.isEmpty(  ) && ( listRecordFields.get( 0 ) != null ) )
//            {
//                RecordField recordFieldIdDemand = listRecordFields.get( 0 );
//                strRecordFieldValue = recordFieldIdDemand.getValue(  );
//
//                if ( recordFieldIdDemand.getField(  ) != null )
//                {
//                    strRecordFieldValue = recordFieldIdDemand.getField(  ).getTitle(  );
//                }
//            }
//        }
//
//        return strRecordFieldValue;
//    }
//
//    @Override
//    public String getSMSPhoneNumber( int nPositionPhoneNumber, int nIdRecord, int nIdDirectory )
//    {
//        String strSMSPhoneNumber = StringUtils.EMPTY;
//
//        strSMSPhoneNumber = getRecordFieldValue( nPositionPhoneNumber, nIdRecord, nIdDirectory );
//
//        return strSMSPhoneNumber;
//    }
//
//    @Override
//    public String getUserGuid( int nPositionUserGuid, int nIdRecord, int nIdDirectory )
//    {
//        String strUserGuid = StringUtils.EMPTY;
//
//        if ( nPositionUserGuid != DirectoryUtils.CONSTANT_ID_NULL )
//        {
//            strUserGuid = getRecordFieldValue( nPositionUserGuid, nIdRecord, nIdDirectory );
//        }
//
//        return strUserGuid;
//    }
//
//    private void fillModelWithUserAttributes( Map<String, Object> model, String strUserGuid )
//    {
//        if ( _userAttributesManager.isEnabled(  ) && StringUtils.isNotBlank( strUserGuid ) )
//        {
//            Map<String, String> mapUserAttributes = _userAttributesManager.getAttributes( strUserGuid );
//            String strFirstName = mapUserAttributes.get( LuteceUser.NAME_GIVEN );
//            String strLastName = mapUserAttributes.get( LuteceUser.NAME_FAMILY );
//            String strEmail = mapUserAttributes.get( LuteceUser.BUSINESS_INFO_ONLINE_EMAIL );
//            String strPhoneNumber = mapUserAttributes.get( LuteceUser.BUSINESS_INFO_TELECOM_TELEPHONE_NUMBER );
//
//            model.put( ProviderTicketingConstants.MARK_FIRST_NAME,
//                StringUtils.isNotEmpty( strFirstName ) ? strFirstName : StringUtils.EMPTY );
//            model.put( ProviderTicketingConstants.MARK_LAST_NAME,
//                StringUtils.isNotEmpty( strLastName ) ? strLastName : StringUtils.EMPTY );
//            model.put( ProviderTicketingConstants.MARK_EMAIL,
//                StringUtils.isNotEmpty( strEmail ) ? strEmail : StringUtils.EMPTY );
//            model.put( ProviderTicketingConstants.MARK_PHONE_NUMBER,
//                StringUtils.isNotEmpty( strPhoneNumber ) ? strPhoneNumber : StringUtils.EMPTY );
//        }
//    }
//
//    @Override
//    public Locale getLocale( HttpServletRequest request )
//    {
//        Locale locale = null;
//
//        if ( request != null )
//        {
//            locale = request.getLocale(  );
//        }
//        else
//        {
//            locale = I18nService.getDefaultLocale(  );
//        }
//
//        return locale;
//    }
//
//    private String getBaseUrl( HttpServletRequest request )
//    {
//        String strBaseUrl = StringUtils.EMPTY;
//
//        if ( request != null )
//        {
//            strBaseUrl = AppPathService.getBaseUrl( request );
//        }
//        else
//        {
//            strBaseUrl = AppPropertiesService.getProperty( ProviderTicketingConstants.PROPERTY_LUTECE_ADMIN_PROD_URL );
//
//            if ( StringUtils.isBlank( strBaseUrl ) )
//            {
//                strBaseUrl = AppPropertiesService.getProperty( ProviderTicketingConstants.PROPERTY_LUTECE_BASE_URL );
//
//                if ( StringUtils.isBlank( strBaseUrl ) )
//                {
//                    strBaseUrl = AppPropertiesService.getProperty( ProviderTicketingConstants.PROPERTY_LUTECE_PROD_URL );
//                }
//            }
//        }
//
//        return strBaseUrl;
//    }
}
