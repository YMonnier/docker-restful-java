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
        val: false,
        hidden: false
    })
    .config(function ($routeProvider, $middlewareProvider) {
        const middlewareAuth = 'middleware-auth';
        console.log('middlewareProvider');
        $middlewareProvider.map({
            'middlewareAuth': function auth(authenticate) {
                console.log('test ' + authenticate);
                if (authenticate === true) {
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
              templateUrl: 'views/login.html',
              controller: 'LoginCtrl',
              controllerAs: 'login'
            })
            .when('/register', {
              templateUrl: 'views/register.html',
              controller: 'RegisterCtrl',
              controllerAs: 'register'
            })
            .otherwise({
                redirectTo: '/'
            });
    })
    .run(function ($rootScope) {
        $rootScope.apiURL = 'http://127.0.0.1:8080/littleapp/';
        $rootScope.auth = false;
    });
