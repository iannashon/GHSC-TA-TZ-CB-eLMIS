
/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */
function SeasonRationingAdjustmentTypeController($scope, $location, $filter, $dialog,DeleteSeasonalityRationings, messageService,navigateBackService,ngTableParams,seasonalityRationingsList,SeasonalityRationingTypes) {


    $scope.disabled = false;
    $scope.seasonalityRationingType = {};

    $scope.seasonalityRationings = seasonalityRationingsList;

//    storage type search

    $scope.showSeasonalityRationingTypeSearch = function () {

        var query = $scope.query;

        var len = (query === undefined) ? 0 : query.length;

        if (len >= 3) {

            if ($scope.previousQuery.substr(0, 3) === query.substr(0, 3)) {
                $scope.previousQuery = query;

                filterSeasonalityRationingTypesByName(query);
                return true;
            }
            $scope.previousQuery = query;

            SeasonalityRationingTypes.get({param: $scope.query.substr(0, 3)}, function (data) {
                $scope.seasonalityRationingList = data.seasonalityRationingsList;
                filterSeasonalityRationingTypesByName(query);
            }, {});

            return true;
        } else {
            return false;
        }
    };

    $scope.previousQuery = '';
    $scope.query = navigateBackService.query;

    $scope.showSeasonalityRationingTypeSearch();

    var filterSeasonalityRationingTypesByName = function (query) {
        $scope.filteredSeasonalityRationingTypes = [];
        query = query || "";

        angular.forEach($scope.seasonalityRationingList, function (seasonalityRationing) {
            var name = seasonalityRationing.name.toLowerCase();

            if (name.indexOf() >= 0 ||
                name.toLowerCase().indexOf(query.trim().toLowerCase()) >= 0) {
                $scope.filteredSeasonalityRationingTypes.push(seasonalityRationing);
            }
        });
        $scope.resultCount = $scope.filteredSeasonalityRationingTypes.length;
    };
//    end of search
    $scope.createSeasonalityRationingType = function () {

        $scope.error = "";
        if ($scope.seasonalityRationingForm.$invalid) {
            $scope.showError = true;

            $scope.errorMessage = "The form you submitted is invalid. Please revise and try again.";
            return;
        }

        var createSuccessCallback = function (data) {
            $scope.seasonalityRationings = seasonalityRationingsList;
            $scope.$parent.message = 'New Seasonality/Rationing Adjustment Type created successfully';

            $scope.seasonalityRationingType = {};
        };

        var errorCallback = function (data) {
            $scope.showError = true;

            $scope.errorMessage = messageService.get(data.data.error);
        };
        $scope.error = "";


        SeasonalityRationingTypes.save( $scope.seasonalityRationingType, createSuccessCallback, errorCallback);

        $location.path('/list');
    };
    $scope.cancelEdit = function () {
        $location.path('/list');
    };
    $scope.clearSearch = function () {
        $scope.query = "";
        $scope.resultCount = 0;
        angular.element("#searchSeaonalityRationingTypes").focus();
        $location.path('/list');
    };
    $scope.editSeaonalityRationingTypes = function (id) {
        if (id) {

            $location.path('/edit/' + id);
        }
    };
    $scope.deleteSeasonalityRationingType = function (result) {
        if (result) {

            var deleteSuccessCallback = function (data) {
                $scope.$parent.message = 'Seasonality/Rationing Adjustment Type Deleted Successfully';

                $scope.seasonalityRationingType = {};

                $scope.seasonalityRationings = seasonalityRationingsList;

            };

            var deleteErorCallback = function (data) {
                $scope.showError = true;

                $scope.errorMessage = messageService.get(data.data.error);

            };

            SeasonalityRationingTypes.delete($scope.seasonalityRationingType, deleteSuccessCallback, deleteErorCallback);

        }
    };
    $scope.showDeleteConfirmDialog = function (seasonalityRationingType) {
        $scope.seasonalityRationingType = seasonalityRationingType;
        var options = {
            id: "removeSeasonalityRationingTypesConfirmDialog",
            header: "Confirmation",
            body: "Are you sure you want to remove the Seasonality/Rationing Adjustment Type: " + seasonalityRationingType.name
        };
        OpenLmisDialog.newDialog(options, $scope.deleteSeasonalityRationingType, $dialog, messageService);
    };
    $scope.clearForm = function () {
        $scope.seasonalityRationingType = {};
        $location.path('/list');
    };
//start of pagination////////////////////////////////////////////////

    // the grid options
    $scope.tableParams = new ngTableParams({
        page: 1,            // show first page
        total: 0,           // length of data
        count: 25           // count per page
    });

    $scope.paramsChanged = function(params) {
        // slice array data on pages

        $scope.seasonalityRationings = [];
        $scope.data = seasonalityRationingsList;
        params.total =  $scope.data.length;

        var data = $scope.data;
        var orderedData = params.filter ? $filter('filter')(data, params.filter) : data;
        orderedData = params.sorting ?  $filter('orderBy')(orderedData, params.orderBy()) : data;

        params.total = orderedData.length;
        $scope.seasonalityRationings = orderedData.slice( (params.page - 1) * params.count,  params.page * params.count );
        var i = 0;
        var baseIndex = params.count * (params.page - 1) + 1;

        while(i < $scope.seasonalityRationings.length){

            $scope.seasonalityRationings[i].no = baseIndex + i;

            i++;

        }
    };

    // watch for changes of parameters
    $scope.$watch('tableParams', $scope.paramsChanged , false);

//    $scope.getPagedDataAsync = function (pageSize, page) {
//        // Clear the results on the screen
//        $scope.countries = [];
//        $scope.data = [];
//        var params =  {
//            "max" : 10000,
//            "page" : 1
//        };
//
//        $.each($scope.filterObject, function(index, value) {
//            if(value !== undefined)
//                params[index] = value;
//        });
//        $scope.paramsChanged($scope.tableParams);
////
//    };
}
SeasonRationingAdjustmentTypeController.resolve = {
    seasonalityRationingsList: function ($q, $timeout, SeasonalityRationingTypes) {

        var deferred = $q.defer();

        $timeout(function () {

            SeasonalityRationingTypes.get({param: ''}, function(data){
                deferred.resolve( data.seasonalityRationingsList );
            },{});

        }, 100);
        return deferred.promise;
    }
};



