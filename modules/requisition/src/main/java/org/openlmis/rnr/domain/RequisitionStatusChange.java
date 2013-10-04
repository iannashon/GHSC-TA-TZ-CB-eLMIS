
/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.rnr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openlmis.core.domain.User;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequisitionStatusChange {
  private Long id;

  private Long rnrId;
  private RnrStatus status;
  private User createdBy;
  private Date createdDate;

  public RequisitionStatusChange(Rnr requisition) {
    this.rnrId = requisition.getId();
    this.status = requisition.getStatus();
    this.createdBy = new User(requisition.getModifiedBy(), null);
  }
}
