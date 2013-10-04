/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.core.service;

import lombok.NoArgsConstructor;
import org.openlmis.core.domain.ProductGroup;
import org.openlmis.core.repository.ProductGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ProductGroupService {

  private ProductGroupRepository productGroupRepository;

  @Autowired
  public ProductGroupService(ProductGroupRepository productGroupRepository) {
      this.productGroupRepository = productGroupRepository;
  }

  public void save(ProductGroup productGroup) {
    if(productGroup.getId() == null) {
      productGroupRepository.insert(productGroup);
    }
    productGroupRepository.update(productGroup);
  }

  public ProductGroup getByCode(String code) {
    return productGroupRepository.getByCode(code);
  }

}
