'use strict';

/**
 * @ngdoc function
 * @name petstoreFrontApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the poFront
 */
 angular.module('petstoreFrontApp')
 .controller('ModalImagePetCtrl', function ($uibModalInstance,$scope,CONSTANTS,PetsApiFactory, items) {

 	  var me = this;

      this.petModel = null;

 	  this.image = null;
 	  
 	  this.init = function() {

 	  	if(items.pet != null) {
 	  		this.petModel = items.pet;
 	  	}
 	  }

	  this.ok = function () {
          console.log(this.image);
	    $uibModalInstance.close(this.image);
	  };

	  this.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };

    this.uploadFile = function(files) {
        var fd = new FormData();
        //Take the first selected file
        fd.append("file", files[0]);

        $http.post(uploadUrl, fd, {
            withCredentials: true,
            headers: {'Content-Type': undefined },
            transformRequest: angular.identity
           })
           .success(console.log("success"))
           .error(console.log("error"));

    };
	  /* la fonction déclenché a l'ouverte du modal */
	  $uibModalInstance.opened.then(function() {
	  	me.init();
	  });

 });
