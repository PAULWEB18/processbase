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
package org.processbase.ui.core.bonita.forms;

import java.io.ByteArrayInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BonitaFormParcer {

    private XMLProcessDefinition process;

    public BonitaFormParcer(byte[] procBytes) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(procBytes));
            doc.getDocumentElement().normalize();
            Node mainProcess = doc.getElementsByTagName("process:MainProcess").item(0);
            NodeList processNodes = mainProcess.getChildNodes();
            for (int i = 0; i < processNodes.getLength(); i++) {
                if (processNodes.item(i).getNodeName().equals("elements")
                        && processNodes.item(i).getAttributes().getNamedItem("xmi:type").getNodeValue().equals("process:Pool")) {
                    Node processNode = processNodes.item(i);
                    process = new XMLProcessDefinition(processNode.getAttributes().getNamedItem("name").getNodeValue(), processNode.getAttributes().getNamedItem("label").getNodeValue());
                    process.setSkipEntryPageFlow(processNode.getAttributes().getNamedItem("entryPageFlowType") != null && processNode.getAttributes().getNamedItem("entryPageFlowType").getNodeValue().equals("SKIP"));
                    NodeList processChilds = processNode.getChildNodes();
                    for (int y = 0; y < processChilds.getLength(); y++) {
                        // TASKS IN POOL (NO LANES)
                        if (processChilds.item(y).getNodeName().equals("elements")
                                && processChilds.item(y).getAttributes().getNamedItem("xmi:type").getNodeValue().equals("process:Task")) {
                            Node nodeTask = processChilds.item(y);
                            if (nodeTask.getChildNodes().getLength() > 0) {
                                XMLTaskDefinition task = new XMLTaskDefinition(nodeTask.getAttributes().getNamedItem("name").getNodeValue(), nodeTask.getAttributes().getNamedItem("label").getNodeValue());
                                task.setSkipEntryPageFlow(nodeTask.getAttributes().getNamedItem("entryPageFlowType") != null && nodeTask.getAttributes().getNamedItem("entryPageFlowType").getNodeValue().equals("SKIP"));
                                process.addTask(nodeTask.getAttributes().getNamedItem("name").getNodeValue(), task);
                            }
                            // TASKS IN LANES
                        } else if (processChilds.item(y).getNodeName().equals("elements")
                                && processChilds.item(y).getAttributes().getNamedItem("xmi:type").getNodeValue().equals("process:Lane")) {
                            NodeList laneChilds = processChilds.item(y).getChildNodes();
                            for (int z = 0; z < laneChilds.getLength(); z++) {
                                if (laneChilds.item(z).getNodeName().equals("elements")
                                        && laneChilds.item(z).getAttributes().getNamedItem("xmi:type").getNodeValue().equals("process:Task")) {
                                    Node nodeTask = laneChilds.item(z);
                                    if (nodeTask.getChildNodes().getLength() > 0) {
                                        XMLTaskDefinition task = new XMLTaskDefinition(nodeTask.getAttributes().getNamedItem("name").getNodeValue(), nodeTask.getAttributes().getNamedItem("label").getNodeValue());
                                        task.setSkipEntryPageFlow(nodeTask.getAttributes().getNamedItem("entryPageFlowType") != null && nodeTask.getAttributes().getNamedItem("entryPageFlowType").getNodeValue().equals("SKIP"));
                                        process.addTask(nodeTask.getAttributes().getNamedItem("name").getNodeValue(), task);
                                    }
                                }
                            
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public XMLProcessDefinition getProcess() {
        return process;
    }

    

    public static FormsDefinition createFormsDefinition(String xmlString) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(FormsDefinition.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (FormsDefinition) unmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()));
    }
}
