'use strict';

/**
 * @ngdoc function
 * @name webApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the webApp
 */
angular.module('webApp')
    .controller('UsersCtrl', function ($scope, $rootScope, apiService) {
        $scope.auth = $rootScope.user;
        $scope.users = [];
        $scope.errors = [];
        var update = false;

        const setErrors = function (response) {
            if (response.status >= 400) {
                $scope.errors = response.data.errors;
            }
        };

        const showModal = function () {
            $scope.errors = [];
            $('.ui.modal').modal('show');
        };

        // GET //
        // Fetch all persons
        apiService.users.all()
            .then(function (successResponse) {
                if (apiService.isValid(successResponse)) {
                    $scope.users = successResponse.data;
                }
            }, function (errorResponse) {
                setErrors(errorResponse);
            });

        // POST //
        // Add a new person
        $scope.blankP = {};
        $scope.reset = (function () {
            $scope.user = angular.copy($scope.blankP);
        })();

        $scope.update = function (user) {
            console.log(user);
            apiService.users.update(user.id, user)
                .then(function (response) {
                    if (apiService.isValid(response)) {
                        var index = $scope.users.indexOf(user);
                        if (index > -1) {
                            $scope.users[index] = response.data;
                        }
                    }
                }, function (response) {
                    setErrors(response);
                });
        };

        // DELETE //
        // Delete the person
        $scope.delete = function (user) {
            var index = $scope.users.indexOf(user);
            apiService.users.delete(user.id)
                .then(function (response) {
                    if (apiService.isValid(response)) {
                        if (index > -1) {
                            $scope.users.splice(index, 1);
                        }
                    }
                }, function (response) {
                    setErrors(response);
                });
        };

        $scope.selection = function (user) {
            console.log('$scope.selection');
            console.log(user);
            $scope.user = user;
            showModal();
        };
    });
