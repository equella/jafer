/**
 * JAFER Toolkit Project. Copyright (C) 2002, JAFER Toolkit Project, Oxford
 * University. This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version. This library is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.jafer.registry.web.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jafer.registry.RegistryException;
import org.jafer.registry.ServiceLocator;
import org.jafer.registry.model.ServiceProvider;
import org.jafer.registry.web.struts.ParameterKeys;

/**
 * This action views the specified providers services
 */
public final class ViewProviderServicesAction extends JaferRegistryAction
{

    /**
     * Execute the action
     * 
     * @param mapping The struts action mapping
     * @param form The action form sent in the request
     * @param request The request object
     * @param response The response object
     * @return The action to forward to
     * @throws IOException
     * @throws ServletException
     * @throws RegistryException
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws RegistryException, IOException, ServletException
    {
        // initialise the action
        initAction(mapping, form, request, response);

        String id = (String) dynaForm.get(ParameterKeys.PROVIDER_ID);

        ServiceLocator servLoc = getServiceLocator();
        ServiceProvider provider = servLoc.getServiceProvider(id);

        if (provider != null)
        {
            dynaForm.set(ParameterKeys.PROVIDER_NAME, provider.getName());
            dynaForm.set(ParameterKeys.PROVIDER_DESCRIPTION, provider.getDescription());
            dynaForm.set(ParameterKeys.PROVIDER_HOMEPAGE, provider.getHomePage());
            dynaForm.set(ParameterKeys.PROVIDER_SERVICES, provider.getServices()); 
            dynaForm.set(ParameterKeys.NUM_PROVIDER_SERVICES,String.valueOf(provider.getServices().size()));            
        }
        else
        {
            addErrorMessage("errors.expected.provider.notfound");
            return getErrorPage();
        }

        return getActionForward(SUCCESS);
    }
}