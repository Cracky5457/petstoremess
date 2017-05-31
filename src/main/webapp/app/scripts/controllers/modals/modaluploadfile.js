'use strict';

/**
 * @ngdoc function
 * @name petstoreFrontApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the poFront
 */
 angular.module('petstoreFrontApp')
 .controller('ModalUploadFileCtrl', function ($uibModalInstance, Upload) {

 	  var me = this;


 	  this.file = null;
 	  
 	  this.init = function() {

 	  }

	  this.ok = function () {
	    $uibModalInstance.close(this.file);
	  };

	  this.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };

	  /* la fonction déclenché a l'ouverte du modal */
	  $uibModalInstance.opened.then(function() {
	  	me.init();
	  });

 });
