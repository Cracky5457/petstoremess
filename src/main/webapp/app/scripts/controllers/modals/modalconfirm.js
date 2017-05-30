(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name petstoreFrontApp.controller:LoginCtrl
   * @description
   * # LoginCtrl
   * Controller of the poFront
   */
  angular.module('petstoreFrontApp')
    .controller('ModalConfirmCtrl', function ($uibModalInstance, items) {
      var me = this;

      this.title = items.title;
      this.message = items.message;

      this.init = function () {};

      this.ok = function () {
        $uibModalInstance.close('ok');
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
      };

      /* la fonction déclenché a l'ouverte du modal */
      $uibModalInstance.opened.then(function () {
        me.init();
      });
    });
})();
