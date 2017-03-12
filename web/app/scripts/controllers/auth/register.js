'use strict';

/**
 * @ngdoc function
 * @name webApp.controller:RegisterCtrl
 * @description
 * # RegisterCtrl
 * Controller of the webApp
 */
angular.module('webApp')
    .controller('RegisterCtrl', function ($scope, $location, apiService) {
        $scope.processing = false;
        $scope.user = {
            role: 0
        };
        $scope.errors = [];

        $scope.register = function (user) {
            $scope.processing = true;
            console.log('register function');
            console.log(user);
            if (valid(user)) {
                console.log('VALID!');
                apiService.auth
                    .registration(user)
                    .then(function (response) {
                        $scope.error = [];
                        if(apiService.isValid(response)) {
                            $scope.processing = false;
                            $location.path('/login');
                        }
                    }, function (error) {
                        console.log(error);
                        if (error.errors)
                            $scope.errors = error.errors;
                        else
                            $scope.errors.push('Please try again...');
                        $scope.processing = false;
                    });
            } else {
                $scope.processing = false;
            }
            console.log($scope.errors);
        };

        var valid = function (user) {
            console.log('valid');
            console.log(user);
            var res = true;
            if (user.nickname.length < 4) {
                res = false;
                $scope.errors.push('The nickname is too short!');
            }
            if (user.password.length < 8) {
                res = false;
                $scope.errors.push('The password must have a minimum of 8 characters.');
            }
            if (user.password != user.password_confirmation) {
                res = false;
                $scope.errors.push('The password must be the same.');
            }
            if (user.address.length === 0) {
                res = false;
                $scope.errors.push('The address must not be empty.');
            }

            return res;
        }
    });
