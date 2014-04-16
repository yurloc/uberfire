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
package org.uberfire.user.management.service;

import java.util.Set;

/**
 * Definition of a service to manage users. Different implementations to handle different User stored;
 * e.g. properties file, JACC, JASS etc and different containers; e.g. AS7/EAP, Tomcat or Jetty
 */
public interface UserManager {

    boolean isAddUserSupported();

    boolean isUpdateUserSupported();

    boolean isDeleteUserSupported();

    Set<String> getUserNames();

    Set<String> getUserRoles( final String userName );

    void addUser( final String userName,
                  final String userPassword,
                  final Set<String> userRoles );

    void updateUserPassword( final String userName,
                             final String userPassword );

    void updateUserRoles( final String userName,
                          final Set<String> userRoles );

    void addUserRole( final String userName,
                      final String userRole );

    void removeUserRole( final String userName,
                         final String userRole );

    void deleteUser( final String userName );

}
