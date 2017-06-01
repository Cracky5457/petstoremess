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


    // Public API here
    return {
      login: function (login,password) {
        return $http.get(CONSTANTS.USERS_DOMAIN + "/user/login" + CONSTANTS.SUFFIXE_DOMAIN+"?user="+login+"&password="+password)
          .then(
            function (response) {
              return response;
            })
        }
    };
  });
