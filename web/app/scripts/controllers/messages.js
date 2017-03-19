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

        $scope.msgs = [];


        var message_obj = function (date, user, content) {
            return {
                date: date,
                user: user,
                content: content
            }
        };

        var ws = new WebSocket($rootScope.wsURL + $routeParams.id + '/' + $rootScope.user.nickname);
        ws.onopen = function (websocket) {
            console.log('on open...');
            console.log(websocket);
        };
        ws.onmessage = function (message) {
            console.log('message...');
            console.log(message);
            if (message.data) {
                test(message.data);
            }
        };
        ws.onerror = function (websocket) {
            console.log('on error...');
            console.log(websocket);
        };

        var test = function (data) {
            console.log(data);
            var dataJson = JSON.parse(data);
            console.log(dataJson);
            if (dataJson.from === FROM.SERVER) {
                var message = JSON.parse(dataJson.content);
                console.log(message);
                var msg = message_obj(message.date,
                    {nickname: message.nickname},
                    message.content);
                add(msg);
                console.log($scope.msgs);
            } else if (data.from === FROM.CLIENT) {

            }
        };
        var add = function (object) {
            $scope.$apply(function(){
                $scope.msgs.push(object);
            });

        }
    });
