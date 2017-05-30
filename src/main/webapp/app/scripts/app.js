'use strict';

/**
 * @ngdoc overview
 * @name petstoreFrontApp
 * @description
 * # petstoreFrontApp
 *
 * Main module of the application.
 */
angular
  .module('petstoreFrontApp', [
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ui.bootstrap',
    'ngTagsInput',
    'smart-table'
  ])
  .constant('CONSTANTS', (function () {
    /* La racine du projet sur le serv*/
    var resource = '';

    // Use the variable in your constants
    return {
      USERS_DOMAIN: resource,
      SUFFIXE_DOMAIN: ".do",

      PET_STATUS : ['Available','Pending','Sold']
    };
  })())
  .config(function ($routeProvider,$locationProvider) {
    $locationProvider.hashPrefix('');

    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        controllerAs: 'login'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
