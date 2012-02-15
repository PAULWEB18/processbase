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

import org.ow2.bonita.facade.runtime.Category;
import org.ow2.bonita.light.LightProcessDefinition;

/**
 *
 * @author marat
 */
public class CategoryAndProcessDefinition {

    private Category category;
    private LightProcessDefinition processDef;

    public CategoryAndProcessDefinition(Category category, LightProcessDefinition processDef) {
        this.category = category;
        this.processDef = processDef;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LightProcessDefinition getProcessDef() {
        return processDef;
    }

    public void setProcessDef(LightProcessDefinition processDef) {
        this.processDef = processDef;
    }

    

}
