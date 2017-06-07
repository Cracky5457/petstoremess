'use strict';

/**
 * @ngdoc function
 * @name petstoreFrontApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the poFront
 */
 angular.module('petstoreFrontApp')
 .controller('ModalPetCtrl', function ($scope, $uibModalInstance,CONSTANTS,PetsApiFactory, items) {

 	  var me = this;

		this.state;

 	  this.status = CONSTANTS.PET_STATUS;

 	  this.petModel = {
		  "id": null,
		  "category": {
		    "id": null,
		    "name": ""
		  },
		  "name": "",
		  "photoUrls": [],
		  "tags": [],
		  "status": ""
	  }

 	  
 	  this.init = function() {

 	  	if(items.pet != null) {
 	  		 this.petModel = items.pet;
 	  	}

			 this.state = items.state;
 	  }

		this.isViewMode = function() {
			return this.state===CONSTANTS.STATE_VIEW;
		}

		this.getOkButton = function() {
			if(this.state===CONSTANTS.STATE_VIEW) {
				return "Close";
			}

			return "Save";
		}

	  this.displayTags = function(tags) {

       var stags = "";

       var i = 0;

       if(!isUndefinedOrNull(tags)) {
         for(i = 0; i < tags.length; i++) {
           
           if(i==0) {
             stags+=tags[i].name;
           } else {
             stags+=","+tags[i].name;
           }
         }
       }

       return stags;

     }

	  this.ok = function () {

			this.formPet.submitted = true;

			if(this.formPet.$valid) {
				$uibModalInstance.close(this.petModel);
			} else {
				console.log('Errors in form data');
				console.log(this.formPet.submitted);
				console.log(this.formPet.name.$invalid);
			}
	  };

	  this.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };

	  /* la fonction déclenché a l'ouverte du modal */
	  $uibModalInstance.opened.then(function() {
	  	me.init();
	  });

 });
