'use strict';

/**
 * @ngdoc function
 * @name webApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the webApp
 */
angular.module('webApp')
    .controller('LoginCtrl', function ($rootScope, $scope, $location, apiService, authenticate) {
        $scope.user = {};
        $scope.processing = false;
        $scope.errors = [];
        $scope.login = function(user) {
            console.log(user);
            $scope.processing = true;
            apiService.auth
                .login(user)
                .then(function(response) {
                    $scope.error = [];
                    if (apiService.isValid(response)) {
                        console.log(response);
                        $scope.processing = false;
                        authenticate.val = true;
                        $rootScope.user = response.data;
                        $location.path('/persons');
                    } else {
                        $scope.processing = false;
                    }
                }, function(error) {
                    console.log(error);
                    if (error.errors)
                        $scope.errors = error.errors;
                    else
                        $scope.errors.push('Bad username or password. Please try again...');
                    $scope.processing = false;
                });
        }
    });
