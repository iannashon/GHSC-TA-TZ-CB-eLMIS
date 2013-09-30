/*
 *
 *  * Copyright © 2013 VillageReach. All Rights Reserved. This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 *  *
 *  * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */


function ConvertToOrderListController($scope, Orders, RequisitionForConvertToOrder, $dialog, messageService, $routeParams, $location) {
  $scope.message = "";
  $scope.noRequisitionSelectedMessage = "";
  $scope.maxNumberOfPages = 10;
  $scope.selectedItems = [];
  $scope.searchOptions = [
    {value: "all", name: "option.value.all"},
    {value: "programName", name: "option.value.program"},
    {value: "facilityCode", name: "option.value.facility.code"},
    {value: "facilityName", name: "option.value.facility.name"},
    {value: "supplyingDepot", name: "label.supplying.depot"}
  ];

  $scope.selectedSearchOption = $scope.searchOptions[0];

  var refreshGrid = function () {

    $scope.selectedItems.length = 0;
    $scope.currentPage = $routeParams.page ? utils.parseIntWithBaseTen($routeParams.page) : 1;
    $scope.selectedSearchOption = _.findWhere($scope.searchOptions, {value: $routeParams.searchType}) || $scope.searchOptions[0];
    $scope.query = $routeParams.searchVal;

    RequisitionForConvertToOrder.get({page: $scope.currentPage, searchType: $scope.selectedSearchOption.value, searchVal: $scope.query}, function (data) {
      $scope.filteredRequisitions = data.rnr_list;

      $scope.numberOfPages = data.number_of_pages || 1;
      $scope.resultCount = $scope.filteredRequisitions.length;
    }, function () {
      $location.search('page', 1);
    });
  };

  $scope.$on('$routeUpdate', refreshGrid);

  refreshGrid();

  $scope.inputKeypressHandler = function ($event) {
    if ($event.keyCode == 13) {
      $event.preventDefault();
      $scope.updateSearchParams();
    }
  };

  $scope.selectSearchType = function (searchOption) {
    $scope.selectedSearchOption = searchOption;
  };

  $scope.updateSearchParams = function () {
    $location.search({page: 1, searchType: $scope.selectedSearchOption.value, searchVal: $scope.query || ''});
  };

  $scope.$watch("currentPage", function () {
    $location.search("page", $scope.currentPage);
  });

  $scope.gridOptions = { data: 'filteredRequisitions',
    selectedItems: $scope.selectedItems,
    multiSelect: true,
    showSelectionCheckbox: true,
    sortInfo: { fields: ['submittedDate'], directions: ['asc'] },
    columnDefs: [
      {field: 'programName', displayName: messageService.get("program.header") },
      {field: 'facilityCode', displayName: messageService.get("option.value.facility.code")},
      {field: 'facilityName', displayName: messageService.get("option.value.facility.name")},
      {field: 'periodStartDate', displayName: messageService.get("label.period.start.date"), cellFilter: "date:'dd/MM/yyyy'"},
      {field: 'periodEndDate', displayName: messageService.get("label.period.end.date"), cellFilter: "date:'dd/MM/yyyy'"},
      {field: 'submittedDate', displayName: messageService.get("label.date.submitted"), cellFilter: "date:'dd/MM/yyyy'"},
      {field: 'modifiedDate', displayName: messageService.get("label.date.modified"), cellFilter: "date:'dd/MM/yyyy'"},
      {field: 'supplyingDepotName', displayName: messageService.get("label.supplying.depot")},
      {field: 'emergency', displayName: messageService.get("requisition.type.emergency"),
        cellTemplate: "<div class='ngCellText checked'><i ng-class='{\"icon-ok\": row.entity.emergency}'></i></div>",
        width: 110 }
    ]
  };

  var showConfirmModal = function () {
    var options = {
      id: "confirmDialog",
      header: messageService.get("label.confirm.action"),
      body: messageService.get("msg.question.confirmation")
    };

    function callBack() {
      return function (result) {
        if (result) {
          convert();
        }
      }
    }

    OpenLmisDialog.newDialog(options, callBack(), $dialog, messageService);
  };

  $scope.convertToOrder = function () {
    $scope.message = "";
    $scope.noRequisitionSelectedMessage = "";
    if ($scope.selectedItems.length == 0) {
      $scope.noRequisitionSelectedMessage = "msg.select.atleast.one.rnr";
      return;
    }
    showConfirmModal();
  };

  var convert = function () {
    var successHandler = function () {
      refreshGrid();
      $scope.message = "msg.rnr.converted.to.order";
      $scope.error = "";
    };

    var errorHandler = function (response) {
      $scope.message = "";
      if (response.status === 409) {
        $scope.error = response.data.error;
      } else {
        $scope.error = "msg.error.occurred";
      }

      refreshGrid();
    };

    Orders.post({}, $scope.selectedItems, successHandler, errorHandler);
  };
}
