/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

function HeaderController($scope, localStorageService, loginConfig) {
  $scope.loginConfig = loginConfig;
  $scope.user = localStorageService.get(localStorageKeys.USERNAME);

  $scope.logout = function () {
    localStorageService.remove(localStorageKeys.RIGHT);
    localStorageService.remove(localStorageKeys.USERNAME);
      $.each(localStorageKeys.REPORTS, function(itm,idx){

          localStorageService.remove(idx);
      });
    document.cookie = 'JSESSIONID' + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/';
    window.location = "/j_spring_security_logout";
  };
}