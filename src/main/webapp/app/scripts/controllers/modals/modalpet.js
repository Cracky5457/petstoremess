'use strict';

/**
 * @ngdoc function
 * @name petstoreFrontApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the poFront
 */
 angular.module('petstoreFrontApp')
 .controller('ModalPetCtrl', function ($uibModalInstance,CONSTANTS,PetsApiFactory, items) {

 	  var me = this;

 	  this.title = "Create";

 	  this.status = CONSTANTS.PET_STATUS;

 	  this.petModel = {
		  "id": null,
		  "category": {
		    "id": null,
		    "name": ""
		  },
		  "name": "",
		  "photoUrls": [
		    ""
		  ],
		  "tags": [
		  ],
		  "status": ""
	  }

 	  
 	  this.init = function() {

 	  	if(items.pet != null) {
 	  		this.petModel = items.pet;
 	  	}
 	  }

	  this.ok = function () {
	    $uibModalInstance.close(this.petModel);
	  };

	  this.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };

	  /* la fonction déclenché a l'ouverte du modal */
	  $uibModalInstance.opened.then(function() {
	  	me.init();
	  });

 });
