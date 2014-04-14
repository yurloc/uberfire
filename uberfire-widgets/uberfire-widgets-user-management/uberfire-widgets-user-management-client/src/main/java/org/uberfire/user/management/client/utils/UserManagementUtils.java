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
package org.uberfire.user.management.client.utils;

import java.util.HashSet;
import java.util.Set;

public class UserManagementUtils {

    /**
     * Convert Set<String> of roles into a display-friendly list of Users' roles
     * @param roles
     * @return
     */
    public static String convertUserRoles( final Set<String> roles ) {
        final StringBuilder sb = new StringBuilder();
        for ( String role : roles ) {
            sb.append( role ).append( ", " );
        }
        return sb.substring( 0, sb.length() - 2 );
    }

    /**
     * Convert String of roles into a Set<String> collection of Users' roles
     * @param roles
     * @return
     */
    public static Set<String> convertUserRoles( final String roles ) {
        final Set<String> userRoles = new HashSet<String>();
        if ( roles == null || roles.isEmpty() ) {
            return userRoles;
        }
        for ( String role : roles.split( "," ) ) {
            userRoles.add( role.trim() );
        }
        return userRoles;
    }

}
