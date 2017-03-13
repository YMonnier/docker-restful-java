'use strict';

/**
 * @ngdoc function
 * @name webApp.controller:ChatCtrl
 * @description
 * # ChatCtrl
 * Controller of the webApp
 */
angular.module('webApp')
    .controller('ChannelsCtrl', function ($scope, $rootScope, apiService) {
        console.log('ChannelsCtrl');
        console.log(window.location.host);

        $scope.channels = [];
        $scope.errors = [];
        $scope.channel = {};

        var blankC = {};
        const reset = function () {
            $scope.channel = angular.copy(blankC);
        };

        const setErrors = function (response) {
            if (response.status >= 400) {
                $scope.errors = response.data.errors;
            }
        };

        $scope.showModal = function () {
            $scope.errors = [];
            reset();
            $('.ui.modal').modal('show');
        };

        apiService.channels.all()
            .then(function (response) {
                console.log('all channels');
                console.log(response.data);
                if (apiService.isValid(response)) {
                    $scope.channels = response.data;
                }
            }, function (error) {
                console.log(error);
                setErrors(error);
            });


        $scope.add = function (channel) {
            console.log(channel);
            apiService.channels.create(channel)
                .then(function (response) {
                    console.log(response);
                    if (apiService.isValid(response)) {
                        $scope.channels.push(response.data)
                    }
                }, function (response) {
                    setErrors(response);
                });
        };

        $scope.delete = function (channel) {
            var index = $scope.channels.indexOf(channel);
            apiService.channels.delete(channel.id)
                .then(function (response) {
                    if (apiService.isValid(response)) {
                        console.log('response delete channel...');
                        console.log(response);
                        if (index > -1) {
                            $scope.channels.splice(index, 1);
                        }
                    }
                }, function (response) {
                    setErrors(response);
                });
        };


        /*
         var wss = [
         new WebSocket("ws://localhost:8080/littleapp/chat"),
         new WebSocket("ws://localhost:8080/chat")
         ];

         wss.forEach(function (ws) {
         ws.onopen = function (websocket) {
         console.log('on open...');
         console.log(websocket);
         };
         ws.onerror = function (websocket) {
         console.log('on error...');
         console.log(websocket);
         };
         });
         */
    });
