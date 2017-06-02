'use strict';


//http://www.webdeveasy.com/interceptors-in-angularjs-and-useful-examples/

/**
 * @ngdoc service
 * @name poFront.responseObserver
 * @description
 * # responseObserver
 * Factory in the poFront.
 *
 * Ce module sert a intercepté les erreurs 401 ( user par loggé ) et 403 ( user loggé mais pas les droits pour l url demandé)
 * et redirigé vers le login en cas de 401
 *
 * On peut imaginer tout genre de traitement pour une 403 et 500 comme l'affichage d'un pop up d'erreurr, une redirection
 * une page error500.html itnrouvable etc ...
 */
angular.module('petstoreFrontApp')
  .factory('responseObserver', function responseObserver($q, $location, $injector, $window, toastr) {
    return {
      responseError: function (errorResponse) {
              console.log("observer");
        
    
        switch (errorResponse.status) {
          case 401:
            $location.path('login');
            break;
          case 400:
            displayToastError(errorResponse,toastr);
            break;
          case 403:
            displayToastError(errorResponse,toastr);
            break;
          case 404:
            displayToastError(errorResponse,toastr);
            break;
          case 500:
            displayToastError(errorResponse,toastr);
            break;
        }

        return $q.reject(errorResponse);
      }
    };
  });
