'use strict';

/**
 * @ngdoc function
 * @name petstoreFrontApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the petstoreFrontApp
 */
angular.module('petstoreFrontApp')
  .controller('MenuCtrl', function ($location, userApiFactory) {

	var me = this;
	
	this.user = userApiFactory.getUser();
	
    this.menus = {
        "home":"",
        "about":"",
        "login":"",
    }

	this.clickMenu = function(name) {

	    this.menus = {
	    	"home":"",
	    	"about":"",
	    	"login":"",
			"logout":""
	    }

	    this.menus[name] = 'active';
	}

    this.init = function() {
        var url = $location.url();

		if(url == '/') {
			this.menus.home = 'active';
		} else if(url == '/about') {
			this.menus.about = 'active';
		} else if(url =='/login') {
			this.menus.login = 'active';
		}
    }

	this.logout= function() {

		userApiFactory.logout().then(function() {
			// we redirect to login
			me.clickMenu('login');
			$location.path('/login');
		})
	}

  });
