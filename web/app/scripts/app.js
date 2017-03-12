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
            .when('/person', {
                templateUrl: 'views/persons.html',
                controller: 'Persons',
                controllerAs: 'persons',
                middleware: 'middlewareAuth'
            })
            .when('/chat', {
                templateUrl: 'views/chat.html',
                controller: 'ChatCtrl',
                controllerAs: 'chat',
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
        $rootScope.apiURL = 'http://127.0.0.1:8080/littleapp/';
        $rootScope.user = {};
    });
