'use strict';

/**
 * @ngdoc function
 * @name webApp.controller:MessagesCtrl
 * @description
 * # ChannelCtrl
 * Controller of the webApp
 */
angular.module('webApp')
    .controller('MessagesCtrl', function ($scope, $rootScope, $routeParams) {
        const FROM = {SERVER: 'SERVER', CLIENT: 'CLIENT'};
        $scope.msgs = [];

        var message_obj = function (date, nickname, content) {
            return {
                date: date,
                nickname: nickname,
                content: content
            }
        };

        /**
         * Websocket object. Init connection + setup handlers.
         * @type {WebSocket}
         */
        var ws = new WebSocket($rootScope.wsURL + $routeParams.id + '/' + $rootScope.user.nickname);
        ws.onopen = function (websocket) {
            console.log('on open...');
            console.log(websocket);
        };
        ws.onmessage = function (message) {
            console.log('message...');
            console.log(message);
            if (message.data) {
                push(message.data);
            }
        };
        ws.onerror = function (websocket) {
            console.log('on error...');
            console.log(websocket);
        };

        $scope.send = function (message) {
            console.log('send function...');
            if (message) {
                console.log(message);
                ws.send(message);
            }
        };


        // Utilities
        /**
         * Parse the data from received from the websocket.
         * @param data A string json.
         */
        var push = function (data) {
            var dataJson;
            if (dataJson = JSON.parse(data)) {
                console.log(dataJson);
                var message = dataJson.content;
                if (dataJson.from === FROM.SERVER) {
                    add(message_obj(message.date,
                        'Server',
                        message.content));
                } else if (dataJson.from === FROM.CLIENT) {
                    add(message_obj(message.date,
                        message.nickname,
                        message.content));
                }
            }
            console.log($scope.msgs);
        };

        /**
         * Add object into array
         * from outside the angular process
         * @param object
         */
        var add = function (object) {
            console.log("ADDDDDD");
            console.log(object);
            $scope.$apply(function () {
                $scope.msgs.push(object);
            });

        }
    });
