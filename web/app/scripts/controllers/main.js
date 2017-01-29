'use strict';

/**
 * @ngdoc function
 * @name webApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the webApp
 */
angular.module('webApp')
    .controller('MainCtrl', function ($scope, $rootScope, apiService) {
        $scope.persons = [];
        $scope.errors = [];
        var update = false;

        // GET //
        // Fetch all persons
        apiService.persons.all(function (successResponse) {
            if (successResponse.status >= 200 && successResponse.status < 400) {
                $scope.persons = successResponse.data;
            }
        }, function (errorResponse) {
            setErrors(errorResponse);
        });

        // Show the modal to add a new person
        $scope.showAddingModal = function () {
            update = false;
            $scope.user = null;
            showModal();
        };
        const showModal = function () {
            $scope.errors = [];
            $('.ui.modal').modal('show');
        };

        // POST //
        // Add a new person
        $scope.blankP = {};
        $scope.reset = function () {
            $scope.user = angular.copy($scope.blankP);
        };
        $scope.reset();
        $scope.addPerson = function (user) {
            console.log(user);
            if (!update) {
                apiService.persons.create(user, function (response) {
                    if (response.status >= 200 && response.status < 400) {
                        $scope.persons.push(response.data);
                    }
                }, function (response) {
                    setErrors(response);
                });
            } else {
                apiService.persons.update(user, function (response) {
                    if (response.status >= 200 && response.status < 400) {
                        var index = $scope.persons.indexOf(user);
                        if (index > -1) {
                            $scope.persons[index] = response.data;
                        }
                    }
                }, function (response) {
                    setErrors(response);
                });
            }
        };

        // DELETE //
        // Delete the person
        $scope.delete = function(user) {
            var index = $scope.persons.indexOf(user);
            apiService.persons.delete(user, function (response) {
                if (response.status >= 200 && response.status < 400) {
                    if (index > -1) {
                        $scope.persons.splice(index, 1);
                    }
                }
            }, function (response) {
                setErrors(response);
            });
        };

        $scope.selection = function (user) {
            update = true;
            $scope.user = user;
            showModal();
        };

        const setErrors = function(response) {
            if (response.status >= 400) {
                $scope.errors = response.data.errors;
            }
        };
    });
