/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openlmis.upload.Importable;
import org.openlmis.upload.annotation.ImportField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(include = NON_EMPTY)
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Facility extends BaseModel implements Importable {
  @ImportField(mandatory = true, name = "Facility Code")
  private String code;

  @ImportField(mandatory = true, name = "Facility Name")
  private String name;

  @ImportField(name = "Facility Description")
  private String description;

  @ImportField(name = "GLN")
  private String gln;

  @ImportField(name = "Facility Main Phone")
  private String mainPhone;

  @ImportField(name = "Facility Fax")
  private String fax;

  @ImportField(name = "Facility Address1")
  private String address1;

  @ImportField(name = "Facility Address2")
  private String address2;

  @ImportField(mandatory = true, name = "Geographic Zone Code", nested = "code")
  private GeographicZone geographicZone;

  @ImportField(mandatory = true, name = "Facility Type Code", nested = "code")
  private FacilityType facilityType;

  @ImportField(type = "long", name = "Catchment Population")
  private Long catchmentPopulation;

  @ImportField(type = "double", name = "Facility LAT")
  private Double latitude;

  @ImportField(type = "double", name = "Facility LONG")
  private Double longitude;

  @ImportField(type = "double", name = "Facility Altitude")
  private Double altitude;

  @ImportField(type = "String", name = "Facility Operated By", nested = "code")
  private FacilityOperator operatedBy;

  @ImportField(type = "double", name = "Cold Storage Gross Capacity")
  private Double coldStorageGrossCapacity;

  @ImportField(type = "double", name = "Cold Storage Net Capacity")
  private Double coldStorageNetCapacity;

  @ImportField(type = "boolean", name = "Facility Supplies Others")
  private Boolean suppliesOthers;

  @ImportField(type = "boolean", mandatory = true, name = "Facility Is SDP")
  private Boolean sdp;

  @ImportField(type = "boolean", name = "Facility Has Electricity")
  private Boolean hasElectricity;

  @ImportField(type = "boolean", name = "Facility Is Online")
  private Boolean online;

  @ImportField(type = "boolean", name = "Facility Has Electronic SCC")
  private Boolean hasElectronicScc;

  @ImportField(type = "boolean", name = "Facility Has Electronic DAR")
  private Boolean hasElectronicDar;

  @ImportField(type = "boolean", mandatory = true, name = "Is Active")
  private Boolean active;

  @ImportField(type = "Date", mandatory = true, name = "Facility Go Live Date")
  private Date goLiveDate;

  @ImportField(type = "Date", name = "Facility Go Down Date")
  private Date goDownDate;

  @ImportField(type = "boolean", name = "Is Satellite Facility")
  private Boolean satellite;

  @ImportField(name = "Parent Facility ID")
  private Long parentFacilityId;

  @ImportField(name = "Facility Comments")
  private String comment;

  @ImportField(type = "boolean", mandatory = true, name = "Enabled")
  private Boolean enabled;

  private Boolean virtualFacility = false;

  //TODO : change supportedPrograms to programsSupported
  List<ProgramSupported> supportedPrograms = new ArrayList<>();

  public Facility(Long id) {
    this.id = id;
  }

  public Facility(Long id, String code, String name, FacilityOperator operatedBy, GeographicZone geographicZone, FacilityType facilityType) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.operatedBy = operatedBy;
    this.geographicZone = geographicZone;
    this.facilityType = facilityType;
  }

  public Facility(Long id, boolean enabled, boolean active, Long modifiedBy) {
    this.id = id;
    this.enabled = enabled;
    this.active = active;
    this.modifiedBy = modifiedBy;
  }

  @Override
  public boolean equals(Object o) {

    return reflectionEquals(this, o, false, Facility.class, "supportedPrograms", "geographicZone") &&
      reflectionEquals(this.geographicZone, ((Facility) o).geographicZone, false, GeographicZone.class, "parent", "level");
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(17, 37, this, false, Facility.class, "supportedPrograms", "geographicZone");
  }

  public Facility basicInformation() {
    return new Facility(id, code, name, operatedBy, geographicZone, facilityType);
  }

  public static Facility createFacilityToBeDeleted(Long facilityId, Long modifiedBy) {
    return new Facility(facilityId, false, false, modifiedBy);
  }

  public static Facility createFacilityToBeRestored(Long facilityId, Long modifiedBy, boolean active) {
    return new Facility(facilityId, true, active, modifiedBy);
  }

  public void validate() {
    for (ProgramSupported programSupported : supportedPrograms) {
      programSupported.isValid();
    }
  }
}
