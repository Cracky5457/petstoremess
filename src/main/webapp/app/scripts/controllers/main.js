'use strict';

/**
 * @ngdoc function
 * @name petstoreFrontApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the petstoreFrontApp
 */
angular.module('petstoreFrontApp')
  .controller('MainCtrl', function (CONSTANTS,$uibModal,PetsApiFactory, Upload) {

  	var me = this;

  	this.displayListPets = [];

  	this.listPets = [];

  	this.lineByPage = 5;

  	this.init = function() {
  		this.loadPets();
   	}

   	this.loadPets = function() {
              PetsApiFactory.list()
                .then(function(pets) {
                        if(!isUndefinedOrNull(pets)) {
                        	me.listPets = pets;
                        	me.displayListPets = pets;
                        }
                  },function(error) {
                    // catch
                  });
   	}

     this.displayTags = function(tags) {

       var stags = "";

       var i = 0;

       if(!isUndefinedOrNull(tags)) {
         for(i = 0; i < tags.length; i++) {
           
           console.log(tags[i]);
           if(i==0) {
             stags+=tags[i].name;
           } else {
             stags+=","+tags[i].name;
           }
         }
       }

       return stags;

     }

   	this.addPet = function() {

     	 var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/modals/pet.html',
            controller: 'ModalPetCtrl',
            controllerAs: 'pet',
            size: 'lg',
            resolve: {
                items: function () {
                  return {
                    state:CONSTANTS.STATE_ADD
                  };
                }
              }
          });

          modalInstance.result.then(function (pet) {
            PetsApiFactory.save(pet)
              .then(function(response){
                me.loadPets();
              },function(error) {

              });
          });
   	}

    this.viewPet = function(petSelected) {

       var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/modals/pet.html',
            controller: 'ModalPetCtrl',
            controllerAs: 'pet',
            size: 'lg',
            resolve: {
                items: function () {
                  return {
                    state:CONSTANTS.STATE_VIEW,
                    pet: petSelected
                  };
                }
              }
          });
    }

    this.editPet = function(petSelected) {

       var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/modals/pet.html',
            controller: 'ModalPetCtrl',
            controllerAs: 'pet',
            size: 'lg',
            resolve: {
                items: function () {
                  return {
                    state:CONSTANTS.STATE_EDIT,
                    pet: petSelected
                  };
                }
              }
          });

          modalInstance.result.then(function (pet) {
            PetsApiFactory.save(pet)
              .then(function(response){
                me.loadPets();
              },function(error) {

              });
          });
    }

    this.addImage = function(petSelected) {

       var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/modals/uploadfile.html',
            controller: 'ModalUploadFileCtrl',
            controllerAs: 'uploadfile',
            size: 'lg'
          });

          modalInstance.result.then(function (file) {
            Upload.upload({
                    url: CONSTANTS.USERS_DOMAIN + "/pet/" + petSelected.id +"/uploadImage" ,
                    file: file
                }).progress(function (evt) {

                }).success(function (data, status, headers, config) {
                  me.loadPets();
                });
          });
    }

    this.removePet = function(petId) {
        var modalInstance = $uibModal.open({
          animation: true,
          templateUrl: 'views/modals/modalConfirm.html',
          controller: 'ModalConfirmCtrl',
          controllerAs: 'modalconfirm',
          resolve: {
            items: function () {
              return {
                title: "Delete pet",
                message: "Are you sure you want to delete this pet ?"
              };
            }
          }
        });

        modalInstance.result.then(function () {
          PetsApiFactory.delete(petId)
            .then(function () {
              me.loadPets();
            });
        });      
    }

  });
