'use strict';

/**
 * @ngdoc function
 * @name petstoreFrontApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the petstoreFrontApp
 */
angular.module('petstoreFrontApp')
  .controller('LoginCtrl', function ($location,userApiFactory) {

    this.username = "";
    this.password = "";

    this.login = function() {
        userApiFactory.login(this.username,this.password).then(
            function(response){
                console.log("reload");
                $location.path('/');
            },function(error) {
                console.log("bad credentials");
                console.log(error);
            });
    }
  });
