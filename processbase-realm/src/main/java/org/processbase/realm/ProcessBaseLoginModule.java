/**
 * Copyright (C) 2011 PROCESSBASE Ltd.
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation
 * version 2.1 of the License.
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, see <http://www.gnu.org/licenses/>.
 */
package org.processbase.realm;

import com.sun.appserv.security.AppservPasswordLoginModule;
//import com.sun.enterprise.deployment.PrincipalImpl;
import org.glassfish.security.common.PrincipalImpl;
import java.util.Set;
import javax.security.auth.login.LoginException;

/**
 *
 * @author mgubaidullin
 */
public class ProcessBaseLoginModule extends AppservPasswordLoginModule {

    @Override
    protected void authenticateUser() throws LoginException {
        Set principals = _subject.getPrincipals();
        PrincipalImpl principal = new PrincipalImpl(_username);
        principals.add(new PrincipalImpl(_username));
        String grpList[] = new String[1];
        grpList[0] = "User";
//        grpList[1] = "users";
//        grpList[2] = "administrators";
        this.commitUserAuthentication(grpList);
    }
}

