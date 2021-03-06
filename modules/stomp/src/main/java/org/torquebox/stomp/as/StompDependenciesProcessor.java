/*
 * Copyright 2008-2012 Red Hat, Inc, and individual contributors.
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.torquebox.stomp.as;

import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.module.ModuleDependency;
import org.jboss.as.server.deployment.module.ModuleSpecification;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoader;
import org.torquebox.core.app.RubyAppMetaData;

public class StompDependenciesProcessor implements DeploymentUnitProcessor {
    
    private static ModuleIdentifier TORQUEBOX_STOMP_ID = ModuleIdentifier.create("org.torquebox.stomp");

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        DeploymentUnit unit = phaseContext.getDeploymentUnit();
        
        final ModuleSpecification moduleSpecification = unit.getAttachment( Attachments.MODULE_SPECIFICATION );
        final ModuleLoader moduleLoader = Module.getBootModuleLoader();

        if (unit.hasAttachment( RubyAppMetaData.ATTACHMENT_KEY )) {
            addDependency( moduleSpecification, moduleLoader, TORQUEBOX_STOMP_ID );
            addDependency( moduleSpecification, moduleLoader,
                    org.projectodd.polyglot.stomp.as.StompServices.MODULE_IDENTIFIER );
        }
    }

    private void addDependency(ModuleSpecification moduleSpecification, ModuleLoader moduleLoader, ModuleIdentifier moduleIdentifier) {
        moduleSpecification.addLocalDependency( new ModuleDependency( moduleLoader, moduleIdentifier, false, false, false, false ) );
    }

    @Override
    public void undeploy(DeploymentUnit context) {
        // TODO Auto-generated method stub

    }

}
