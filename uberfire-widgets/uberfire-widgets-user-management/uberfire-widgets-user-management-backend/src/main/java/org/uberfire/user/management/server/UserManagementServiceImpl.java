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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.Dependent;

import org.jboss.errai.bus.server.annotations.Service;
import org.uberfire.user.management.model.UserInformation;
import org.uberfire.user.management.model.UserInformationWithPassword;
import org.uberfire.user.management.service.UserManagementService;

@Service
@Dependent
public class UserManagementServiceImpl implements UserManagementService {

    @Override
    public boolean isUserManagerInstalled() {
        return false;
    }

    @Override
    public List<UserInformation> getUsers() {
        return getUserInformation();
    }

    private List<UserInformation> getUserInformation() {
        final List<UserInformation> userInformation = new ArrayList<UserInformation>();
        userInformation.add( new UserInformation( "manstis",
                                                  new HashSet<String>() {{
                                                      add( "admin" );
                                                  }} ) );
        return userInformation;
    }

    @Override
    public void deleteUser( UserInformation userInformation ) {
        final String userName = userInformation.getUserName();
        System.out.println( "Delete User" );
        System.out.println( "User Name: " + userName );
    }

    @Override
    public void addUser( final UserInformationWithPassword userInformation ) {
        final String userName = userInformation.getUserName();
        final String userPassword = userInformation.getUserPassword();
        final Set<String> userRoles = userInformation.getUserRoles();
        System.out.println( "Add User" );
        System.out.println( "User Name: " + userName );
        System.out.println( "User Password: " + userPassword );
        System.out.println( "User Roles: " + userRoles );
    }

    @Override
    public void updateUser( final UserInformation userInformation ) {
        final String userName = userInformation.getUserName();
        final Set<String> userRoles = userInformation.getUserRoles();
        System.out.println( "Update User" );
        System.out.println( "User Name: " + userName );
        System.out.println( "User Roles: " + userRoles );
    }

    @Override
    public void updateUser( final UserInformationWithPassword userInformation ) {
        final String userName = userInformation.getUserName();
        final String userPassword = userInformation.getUserPassword();
        final Set<String> userRoles = userInformation.getUserRoles();
        System.out.println( "Update User" );
        System.out.println( "User Name: " + userName );
        System.out.println( "User Password: " + userPassword );
        System.out.println( "User Roles: " + userRoles );
    }

}
