'use strict';

/**
 * @ngdoc overview
 * @name webApp
 * @description
 * # webApp
 *
 * Main module of the application.
 */
angular
    .module('webApp', [
        'ngRoute',
        'ngRoute.middleware'
    ])
    .value('authenticate', {
        val: false
    })
    .config(function ($routeProvider, $middlewareProvider) {
        $middlewareProvider.map({
            'middlewareAuth': function auth(authenticate) {
                console.log('test ...');
                console.log(authenticate);
                if (authenticate.val === true) {
                    return this.next();
                }
                console.log('error');
                this.redirectTo('/login');
            }
        });

        $routeProvider
            .when('/', {
                redirectTo: '/login'
            })
            .when('/persons', {
                templateUrl: 'views/users.html',
                controller: 'UsersCtrl',
                controllerAs: 'users',
                middleware: 'middlewareAuth'
            })
            .when('/channels', {
                templateUrl: 'views/channels.html',
                controller: 'ChannelsCtrl',
                controllerAs: 'channels',
                middleware: 'middlewareAuth'
            })
            .when('/channels/:id/:nickname', {
                templateUrl: 'views/messages.html',
                controller: 'MessagesCtrl',
                controllerAs: 'messages',
                middleware: 'middlewareAuth'
            })
            .when('/login', {
                templateUrl: 'views/auth/login.html',
                controller: 'LoginCtrl',
                controllerAs: 'auth/login'
            })
            .when('/register', {
                templateUrl: 'views/auth/register.html',
                controller: 'RegisterCtrl',
                controllerAs: 'auth/register'
            })

            .otherwise({
                redirectTo: '/'
            });
    })
    .run(function ($rootScope) {
        $rootScope.apiURL = 'http://192.168.99.100:8080/littleapp/';
        $rootScope.wsURL = 'ws://192.168.99.100:8025/littleapp/ws/channel/';
        $rootScope.user = {test: 'test'};
    });
