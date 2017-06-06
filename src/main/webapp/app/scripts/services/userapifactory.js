'use strict';

/**
 * @ngdoc service
 * @name petstoreFrontApp.userApiFactory
 * @description
 * # userApiFactory
 * Factory in the petstoreFrontApp.
 */
angular.module('petstoreFrontApp')
  .factory('userApiFactory', function (CONSTANTS,$http) {
    // Service logic
    this.user = {
      username : null
    }

    var me = this;
    // Public API here
    return {
     login: function (login,password) {

        return $http.get(CONSTANTS.USERS_DOMAIN + "/user/login" + CONSTANTS.SUFFIXE_DOMAIN+"?user="+login+"&password="+password)
          .then(
            function (response) {
              me.user.username = response.data.username;
              return response.data;
            })
        },
      user: function (login,password) {
        return $http.get(CONSTANTS.USERS_DOMAIN + "/user")
          .then(
            function (response) {
              me.user.username = response.data.username;
              return response.data;
            })
      },
      getUsername:function() {
        if(!isUndefinedOrNull(this.user)) {
          return me.user.username;
        }
      }
    };
  });
