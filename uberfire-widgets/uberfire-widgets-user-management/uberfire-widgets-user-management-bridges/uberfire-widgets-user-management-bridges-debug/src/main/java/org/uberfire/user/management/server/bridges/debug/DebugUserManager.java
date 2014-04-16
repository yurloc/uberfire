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
package org.uberfire.user.management.server.bridges.debug;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;

import org.uberfire.user.management.service.UserManager;

@ApplicationScoped
public class DebugUserManager implements UserManager {

    final Map<String, Set<String>> users = new HashMap<String, Set<String>>() {{
        put( "mansits", new HashSet<String>() {{
            add( "admin" );
        }} );
    }};

    @Override
    public boolean isAddUserSupported() {
        return true;
    }

    @Override
    public boolean isUpdateUserSupported() {
        return true;
    }

    @Override
    public boolean isDeleteUserSupported() {
        return true;
    }

    @Override
    public Set<String> getUserNames() {
        return users.keySet();
    }

    @Override
    public Set<String> getUserRoles( final String userName ) {
        return users.get( userName );
    }

    @Override
    public void addUser( final String userName,
                         final String userPassword,
                         final Set<String> userRoles ) {
        System.out.println( "Add User" );
        System.out.println( "User Name: " + userName );
        System.out.println( "User Password: " + userPassword );
        System.out.println( "User Roles: " + userRoles );
    }

    @Override
    public void updateUserPassword( final String userName,
                                    final String userPassword ) {
        System.out.println( "Update User Password" );
        System.out.println( "User Name: " + userName );
        System.out.println( "User Password: " + userPassword );
    }

    @Override
    public void updateUserRoles( final String userName,
                                 final Set<String> userRoles ) {
        System.out.println( "Update User Roles" );
        System.out.println( "User Name: " + userName );
        System.out.println( "User Roles: " + userRoles );
    }

    @Override
    public void addUserRole( final String userName,
                             final String userRole ) {
        System.out.println( "Add User Role" );
        System.out.println( "User Name: " + userName );
        System.out.println( "User Role: " + userRole );
    }

    @Override
    public void removeUserRole( final String userName,
                                final String userRole ) {
        System.out.println( "Remove User Role" );
        System.out.println( "User Name: " + userName );
        System.out.println( "User Role: " + userRole );
    }

    @Override
    public void deleteUser( final String userName ) {
        System.out.println( "Delete User" );
        System.out.println( "User Name: " + userName );
    }

}
