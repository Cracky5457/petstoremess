'use strict';

/**
 * @ngdoc service
 * @name petstoreFrontApp.petsApiFactory
 * @description
 * # petsApiFactory
 * Factory in the petstoreFrontApp.
 */
angular.module('petstoreFrontApp')
  .factory('PetsApiFactory', function ($http,CONSTANTS) {
    // Service logic


    // Public API here
    return {
      save: function (pet) {
        return $http.post(CONSTANTS.USERS_DOMAIN + "/pet" + CONSTANTS.SUFFIXE_DOMAIN, pet)
          .then(
            function (response) {
              return response.data;
            },
            function (response) {
              console.log(response);
              return response;
            });
      },
      delete: function (petId) {
        return $http.delete(CONSTANTS.USERS_DOMAIN + "/pet/" + petId + CONSTANTS.SUFFIXE_DOMAIN)
          .then(
            function (response) {
              return response.data;
            },
            function (response) {
              console.log(response);
              return response;
            });
      },
      list: function () {
        return $http.get(CONSTANTS.USERS_DOMAIN + "/pet/list" + CONSTANTS.SUFFIXE_DOMAIN)
          .then(
            function (response) {
              return response.data;
            },
            function (response) {
              console.log(response);
              return response;
            });
      }
    };
  });
