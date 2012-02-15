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
package org.processbase.engine.bam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author mgubaidullin
 */
public class BAMConstants {

    public static boolean LOADED = false;
    public static Properties properties = new Properties();
    public static String BAM_DB_POOLNAME;
    public static String BAM_DB_DIALECT;
    public static String BAM_MQ_CONNECTION_FACTORY;
    public static String BAM_MQ_DESTINATION_RESOURCE;

    public static void loadConstants() {
        try {
            File file = null;
            String userHomeDir = System.getProperty("BONITA_HOME");
            file = new File(userHomeDir + "/processbase3.properties");//global configuration can be accessed %USER_HOME%\processbase3.properties
            if (file.exists()) {
                load(file);
            } else {
                save(file);
                load(file);
            }
            LOADED = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void load(File file) throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream(file);
        if (properties == null) {
            properties = new Properties();
        }
        properties.loadFromXML(fis);
        fis.close();

        BAM_MQ_CONNECTION_FACTORY = properties.containsKey("BAM_MQ_CONNECTION_FACTORY") ? properties.getProperty("BAM_MQ_CONNECTION_FACTORY") : "jms/pbbamConnectionFactory";
        BAM_MQ_DESTINATION_RESOURCE = properties.containsKey("BAM_MQ_DESTINATION_RESOURCE") ? properties.getProperty("BAM_MQ_DESTINATION_RESOURCE") : "jms/pbbamDestinationResource";

        BAM_DB_POOLNAME = properties.containsKey("BAM_DB_POOLNAME") ? properties.getProperty("BAM_DB_POOLNAME") : "jdbc/pbbam";
        BAM_DB_DIALECT = properties.containsKey("BAM_DB_DIALECT") ? properties.getProperty("BAM_DB_DIALECT") : "org.hibernate.dialect.Oracle10gDialect";
    }

    private static void save(File file) throws FileNotFoundException, IOException {
        properties.setProperty("BAM_MQ_CONNECTION_FACTORY", "jms/pbbamConnectionFactory");
        properties.setProperty("BAM_MQ_DESTINATION_RESOURCE", "jms/pbbamDestinationResource");
        properties.setProperty("BAM_DB_POOLNAME", "jdbc/pbbam");
        properties.setProperty("BAM_DB_DIALECT", "org.hibernate.dialect.Oracle10gDialect");

        FileOutputStream fos = new FileOutputStream(file);
        properties.storeToXML(fos, null);
        fos.close();
    }

   
}
