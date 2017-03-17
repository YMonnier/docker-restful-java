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
        console.log('messages controller');
        console.log('channel number ' + $routeParams.id);

        const FROM = {SERVER: 'SERVER', CLIENT: 'CLIENT'};

        $scope.messages = [];
        $scope.messages.push('test');
        console.log($scope.messages);
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

        var push = function (data) {
            var data = JSON.parse(data);
            if (data.from === FROM.SERVER) {
                var content = JSON.parse(data.content);
                console.log(content);
                var obj = {
                    data: content.date,
                    user: {nickname: content.nickname},
                    content: content.content
                };
                console.log(obj);
                $scope.messages.push(obj)
            } else if (data.from === FROM.SERVER) {

            }
        }
    });
