/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.uberfire.user.management.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.user.management.model.UserInformation;
import org.uberfire.user.management.model.UserInformationWithPassword;
import org.uberfire.user.management.service.UserManagementService;
import org.uberfire.user.management.service.UserManager;

@Service
@ApplicationScoped
public class UserManagementServiceImpl implements UserManagementService {

    private static final Logger logger = LoggerFactory.getLogger( UserManagementServiceImpl.class );

    @Inject
    private Instance<UserManager> availableUserManagers;

    private UserManager userManager;

    @PostConstruct
    public void initUserManager() {
        if ( availableUserManagers.isUnsatisfied() ) {
            logger.info( "No UserManager available. User management will therefore not be available." );
        } else if ( !availableUserManagers.isAmbiguous() ) {
            userManager = availableUserManagers.get();
            logger.info( "Installing UserManager '" + userManager.getClass().getName() + "'." );
        } else {
            userManager = availableUserManagers.iterator().next();
            logger.info( "Multiple UserManagers detected." );
            logger.info( "Installing UserManager '" + userManager.getClass().getName() + "'." );
        }
    }

    @Override
    public boolean isUserManagerInstalled() {
        return userManager != null;
    }

    @Override
    public List<UserInformation> getUsers() {
        if ( userManager == null ) {
            throw new IllegalStateException( "UserManager has not been installed." );
        }

        logger.info( "Retrieving list of users using '" + userManager.getClass().getName() + "'." );

        final Set<String> userNames = userManager.getUserNames();
        final List<UserInformation> userInformation = new ArrayList<UserInformation>();
        for ( String userName : userNames ) {
            userInformation.add( new UserInformation( userName,
                                                      userManager.getUserRoles( userName ) ) );
        }

        return userInformation;
    }

    @Override
    public void addUser( final UserInformationWithPassword userInformation ) {
        if ( userManager == null ) {
            throw new IllegalStateException( "UserManager has not been installed." );
        }
        final String userName = userInformation.getUserName();
        final String userPassword = userInformation.getUserPassword();
        final Set<String> userRoles = userInformation.getUserRoles();

        logger.info( "Adding user '" + userName + "' using '" + userManager.getClass().getName() + "'." );

        userManager.addUser( userName,
                             userPassword,
                             userRoles );
    }

    @Override
    public void updateUser( final UserInformation userInformation ) {
        if ( userManager == null ) {
            throw new IllegalStateException( "UserManager has not been installed." );
        }
        final String userName = userInformation.getUserName();
        final Set<String> userRoles = userInformation.getUserRoles();

        logger.info( "Updating user '" + userName + "' using '" + userManager.getClass().getName() + "'." );

        userManager.updateUserRoles( userName,
                                     userRoles );
    }

    @Override
    public void updateUser( final UserInformationWithPassword userInformation ) {
        if ( userManager == null ) {
            throw new IllegalStateException( "UserManager has not been installed." );
        }
        final String userName = userInformation.getUserName();
        final String userPassword = userInformation.getUserPassword();
        final Set<String> userRoles = userInformation.getUserRoles();

        logger.info( "Updating user '" + userName + "' using '" + userManager.getClass().getName() + "'." );

        userManager.updateUserPassword( userName,
                                        userPassword );
        userManager.updateUserRoles( userName,
                                     userRoles );
    }

    @Override
    public void deleteUser( final UserInformation userInformation ) {
        if ( userManager == null ) {
            throw new IllegalStateException( "UserManager has not been installed." );
        }
        final String userName = userInformation.getUserName();

        logger.info( "Deleting user '" + userName + "' using '" + userManager.getClass().getName() + "'." );

        userManager.deleteUser( userName );
    }

}
