'use strict';

/**
 * @ngdoc function
 * @name webApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the webApp
 */
angular.module('webApp')
    .controller('MainCtrl', function($scope, $rootScope, apiService) {
        const server = $rootScope.apiURL + 'persons/';
        console.log('Main controller... ' + server);
        apiService.persons.all(function(successResponse) {
            console.log(successResponse);
        }, function(errorResponse) {
            console.log(errorResponse);
        });
        $scope.addPerson = function() {
            console.log('#addPerson');
            $('.ui.modal').modal('show');
        };
    });
