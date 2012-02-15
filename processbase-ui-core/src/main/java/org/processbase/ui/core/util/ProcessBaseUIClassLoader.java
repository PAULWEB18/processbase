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

package org.processbase.ui.core.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author marat
 */
public class ProcessBaseUIClassLoader extends URLClassLoader {

    public ProcessBaseUIClassLoader(URL[] urls) {
        super(urls, ProcessBaseUIClassLoader.class.getClassLoader());
    }

    public void addFile(String path) throws MalformedURLException {
        String urlPath = "jar:file:" + path + "!/";
        addURL(new URL(urlPath));
    }

    public void printPackages() {
        Package[] x = this.getPackages();
        for (int i = 0; i < x.length; i++) {
            System.out.println("Package = " + x[i].getName());
        }
    }

    public Package[] getP() {
        return this.getPackages();
    }
}
