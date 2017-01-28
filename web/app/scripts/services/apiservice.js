'use strict';

/**
 * @ngdoc service
 * @name webApp.apiService
 * @description
 * # apiService
 * Service in the webApp.
 */
angular.module('webApp')
    .service('apiService', function ($rootScope, $http) {
        // AngularJS will instantiate a singleton by calling "new" on this function
        const url = $rootScope.apiURL + 'persons/';
        const config = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        this.persons = (function () {
            return {
                all: function (successCallback, errorCallback) {
                    $http.get(url, config)
                        .then(successCallback, errorCallback);
                }
            }
        })();
    });
