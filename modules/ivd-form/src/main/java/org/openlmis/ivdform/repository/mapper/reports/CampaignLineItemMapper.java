/*
 * Electronic Logistics Management Information System (eLMIS) is a supply chain management system for health commodities in a developing country setting.
 *
 * Copyright (C) 2015  John Snow, Inc (JSI). This program was produced for the U.S. Agency for International Development (USAID). It was prepared under the USAID | DELIVER PROJECT, Task Order 4.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openlmis.ivdform.repository.mapper.reports;

import org.apache.ibatis.annotations.*;
import org.openlmis.ivdform.domain.reports.CampaignLineItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignLineItemMapper {


  @Insert("INSERT INTO vaccine_report_campaign_line_items " +
      "(reportId, name, venue, startDate, endDate,childrenVaccinated, pregnantWomanVaccinated, otherObjectives, vaccinated, remarks, createdBy, createdDate, modifiedBy, modifiedDate) " +
      " values " +
      "( #{reportId}, #{name}, #{venue}, #{startDate}, #{endDate}, #{childrenVaccinated}, #{pregnantWomanVaccinated}, #{otherObjectives}, #{vaccinated}, #{remarks}, #{createdBy}, NOW(), #{modifiedBy}, NOW() )")
  @Options(useGeneratedKeys = true)
  void insert(CampaignLineItem lineItem);

  @Update("UPDATE vaccine_report_campaign_line_items " +
      " SET" +
      " reportId = #{reportId}" +
      " , name = #{name} " +
      " , venue = #{venue}" +
      " ,startDate = #{startDate}" +
      " , endDate = #{endDate}" +
      " , childrenVaccinated = #{childrenVaccinated}" +
      " , pregnantWomanVaccinated = #{pregnantWomanVaccinated}" +
      " , otherObjectives = #{otherObjectives}" +
      " , vaccinated = #{vaccinated}" +
      " , remarks = #{remarks} " +
      " , modifiedBy = #{modifiedBy}" +
      " , modifiedDate = NOW() " +
      " WHERE id = #{id}")
  void update(CampaignLineItem lineItem);

  @Select("SELECT e.* from vaccine_report_campaign_line_items e where reportId = #{reportId} " +
      " order by id")
  List<CampaignLineItem> getLineItems(@Param("reportId") Long reportId);

  @Delete("delete from vaccine_report_campaign_line_items where reportId = #{reportId} ")
  void deleteLineItems(@Param("reportId") Long reportId);
}
