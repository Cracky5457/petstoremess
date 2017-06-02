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
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ui.bootstrap',
    'ngTagsInput',
    'ngFileUpload',
    'smart-table',
    'toastr'
  ])
  .constant('CONSTANTS', (function () {
    /* La racine du projet sur le serv*/
    var resource = '';

    // Use the variable in your constants
    return {
      USERS_DOMAIN: resource,
      SUFFIXE_DOMAIN: ".do",

      PET_STATUS : ['Available','Pending','Sold'],
      STATE_VIEW : "View",
      STATE_EDIT : "Edit",
      STATE_ADD  : "Add"
    };
  })())
  .config(function ($routeProvider,$locationProvider,$httpProvider) {
    $locationProvider.hashPrefix('');
    $httpProvider.interceptors.push('responseObserver');

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
