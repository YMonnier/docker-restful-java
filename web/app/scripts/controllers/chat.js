'use strict';

/**
 * @ngdoc function
 * @name webApp.controller:ChatCtrl
 * @description
 * # ChatCtrl
 * Controller of the webApp
 */
angular.module('webApp')
    .controller('ChatCtrl', function () {
        console.log('chat controller');
        console.log(window.location.host);
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
