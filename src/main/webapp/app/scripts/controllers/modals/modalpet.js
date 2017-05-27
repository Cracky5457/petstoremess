'use strict';

/**
 * @ngdoc function
 * @name petstoreFrontApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the poFront
 */
 angular.module('petstoreFrontApp')
 .controller('ModalPetCtrl', function ($uibModalInstance,CONSTANTS,PetsApiFactory) {

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
		    {
		      "id": 0,
		      "name": "Tag1"
		    },
		    {
		      "id": 1,
		      "name": "Tag2"
		    },
		  ],
		  "status": ""
	  }

 	  this.tags = ['Tag1','Tag6','Tag3'];
 	  
 	  this.init = function() {

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
