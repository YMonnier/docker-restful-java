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
        var config = function () {
            return {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': $rootScope.user.token
                }
            };
        };

        this.auth = (function () {
            const url = $rootScope.apiURL + 'auth/';
            return {
                registration: function (data) {
                    return $http.post(url + 'register', data, config());
                },
                login: function (data) {
                    return $http.post(url + 'login', data, config());
                }
            }
        })();

        this.users = (function () {
            const url = $rootScope.apiURL + 'users/';
            return {
                all: function () {
                    return $http.get(url, config());
                },
                create: function (data) {
                    return $http.post(url, data, config());
                },
                update: function (id, data) {
                    return $http.put(url + id, data, config());
                },
                delete: function (id) {
                    return $http.delete(url + id);
                }
            };
        })();

        this.channels = (function () {
            const url = $rootScope.apiURL + 'channels/';
            return {
                all: function () {
                    return $http.get(url, config());
                },
                create: function (data) {
                    return $http.post(url, data, config());
                },
                delete: function (id) {
                    return $http.delete(url + id, config());
                },
                messages: function(id) {
                  return $http.get(url + id + '/messages', config());
                }
            }
        })();


        this.isValid = function (response) {
            return response.status >= 200 && response.status < 400;
        };
    });
